/*
 *
 *  * Project to resolve 2D cutting stock problem for Discreet Optimizations course at Polytech Lyon
 *  * Copyright (C) 2015.  CARON Antoine and CHAUSSENDE Adrien
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Affero General Public License as
 *  * published by the Free Software Foundation, either version 3 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Affero General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Affero General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.polytech4A.cuttingstock.core.packing;

import com.polytech4A.cuttingstock.core.model.*;
import com.polytech4A.cuttingstock.core.model.Vector;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Antoine CARON on 13/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Implementation of GUILLOTINE-rect-split-RM-sort-BNF Algorithm declared in
 *          A Thousand Ways to Pack the Bin - A
 *          Practical Approach to Two-Dimensional
 *          Rectangle Bin Packing
 *          Jukka Jylänki
 *          February 27, 2010
 */
public class GuillotineSortBAF extends Packer {

    private static final Logger logger = Logger.getLogger(GuillotineSortBAF.class);
    
    private Solution solution;

    public GuillotineSortBAF(ArrayList<Comparator<Box>> boxComparators) {
        super(boxComparators);
    }

    @Override
    public Solution getPlacing(Solution solution) {
        Solution clSolution = solution.clone();
        clSolution.getPatterns().parallelStream().forEach(p -> p.getPlacedBoxes().clear());
        ArrayList<Pattern> patterns = clSolution.getPatterns();
        logger.debug("Solution to pack " + clSolution);
        this.solution = clSolution;
        patterns.parallelStream().forEach(p -> {
            Pattern pattern = null;
            for (Comparator<Box> comparator : boxComparators) {
                pattern = generatePattern(p, comparator);
                if (pattern != null && pattern != p) { //take the first Pattern Found.
                    p.setPlacedBoxes(pattern.getPlacedBoxes());
                    break;
                }
            }
        });
        logger.debug("Solution packed " + clSolution);
        return clSolution;
    }

    /**
     * Check if a box can be placed in the bin.
     *
     * @param binVector Vector of the bin.
     * @param boxVector Box to be placed in the bin.
     * @return True if the box can be placed in the bin.
     */
    public boolean isBinCompatible(Vector binVector, Vector boxVector) {
        return boxVector.isSmallerThan(binVector);
    }

    /**
     * Generate pattern with placed boxes on it.
     *
     * @param p          Kind of pattern where the boxes will be placed.
     * @param comparator Comparator to use when sorting boxes.
     * @return A new pattern with the boxes placed on it. Return null if there is no boxes to place.
     */
    public Pattern generatePattern(Pattern p, Comparator<Box> comparator) {
        ArrayList<Box> boxes = p.getBoxes();
        Collections.sort(boxes, Collections.reverseOrder(comparator));
        Bin bin = new Bin(p.getSize(), p, new Vector(0, 0));
        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(bin);
        ArrayList<PlacedBox> placedBoxes = generatePlacedBoxes(boxes, bins);
        if (placedBoxes != null) {
            Pattern retPattern = new Pattern(p.getSize(), p.getAmounts());
            retPattern.setPlacedBoxes(placedBoxes);
            return retPattern;
        }
        return p;
    }

    /**
     * Place the boxes on the pattern.
     *
     * @param boxes Boxes that will be placed.
     * @param bins  List of bins that will be generated.
     * @return List of placed boxes.
     */
    public ArrayList<PlacedBox> generatePlacedBoxes(ArrayList<Box> boxes, ArrayList<Bin> bins) {
        ArrayList<PlacedBox> placedBoxes = new ArrayList<>();
        ListIterator<Box> iterator = boxes.listIterator();
        boolean boxPacked = false;
        while (iterator.hasNext() && !boxPacked) {
            Box b = iterator.next();
            boxPacked = false;
            PlacedBox placedBox = generatePlacedBox(bins, b);
            if (placedBox != null) {
                placedBoxes.add(placedBox);
            } else {
                boxPacked = true;
            }
        }
        if (placedBoxes.size() == boxes.size()) {
            return placedBoxes;
        }
        return null;
    }

    /**
     * Place the box on the pattern.
     *
     * @param bins List of bins that will be generated.
     * @param box  Box to be placed.
     * @return The box that is now placed. Return null if it can't be placed.
     */
    public PlacedBox generatePlacedBox(ArrayList<Bin> bins, Box box) {
        Optional<Bin> test = bins.parallelStream()
                .filter(b -> b.isActive() && (isBinCompatible(b.getSize(), box.getSize()) || isBinCompatible(b.getSize(), box.getSize().getInvertedVector())))
                .reduce((b1, b2) -> b1.getSize().isSmallerThan(b2.getSize()) ? b1 : b2);
        PlacedBox placedBox = null;
        if (test.isPresent()) {
            placedBox = generatePlaceBoxForBin(test.get(), box);
        }
        if (placedBox != null) {
            //mark Current Bin
            test.get().setActive(false);
            //Disable bin borthers from other cutting (Vertical or horizontal
            test.get().disableSubBinFromBin(bins);
            //Generate subBins and adds it
            generateSubBins(test.get(), placedBox, bins);
            return placedBox;
        }
        return null;
    }

    /**
     * Check if the Box in parameters can be placed into the Bin.
     *
     * @param bin        Current bin where to place the box.
     * @param boxToPlace Box to place.
     * @return placebox or null if no place is found.
     */
    public PlacedBox generatePlaceBoxForBin(Bin bin, Box boxToPlace) {
        if (boxToPlace.getSize().isSmallerThan(bin.getSize())) {
            return new PlacedBox(boxToPlace, bin.getOrigin(), false);
        } else if (boxToPlace.getSize().isSmallerThan(bin.getSize().getInvertedVector())) {
            return new PlacedBox(boxToPlace, bin.getOrigin(), true);
        } else {
            return null;
        }
    }

    /**
     * Generate bins from horizontal and vertical cut of current bin with the box placed on it.
     *
     * @param bin       Current bin to be cut.
     * @param placedBox Box placed on the current bin.
     */
    public void generateSubBins(Bin bin, PlacedBox placedBox, ArrayList<Bin> bins) {
        Vector sizeBox;
        boolean perfectFit = false;
        Bin bintoCreate;
        if (placedBox.isRotation()) {
            sizeBox = placedBox.getSize().getInvertedVector();
        } else {
            sizeBox = placedBox.getSize();
        }

        //Vertical cut.
        //First bin.
        Vector sizeBin = new Vector(bin.getSize().getX() - sizeBox.getX(), bin.getSize().getY());
        Vector originBin = new Vector(bin.getOrigin().getX() + sizeBox.getX(), bin.getOrigin().getY());
        if (!sizeBin.isDimensionNull()) {
            bintoCreate = new Bin(sizeBin, bin.getPattern(), originBin);
            bin.getVerticalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        } else {
            perfectFit = true;
        }
        //Second bin.
        sizeBin = new Vector(sizeBox.getX(), bin.getSize().getY() - sizeBox.getY());
        originBin = new Vector(bin.getOrigin().getX(), bin.getOrigin().getY() + sizeBox.getY());
        if (!sizeBin.isDimensionNull()) {
            bintoCreate = new Bin(sizeBin, bin.getPattern(), originBin);
            bin.getVerticalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        } else {
            perfectFit = true;
        }

        //Horizontal cut.
        //First bin
        sizeBin = new Vector(bin.getSize().getX(), bin.getSize().getY() - sizeBox.getY());
        originBin = new Vector(bin.getOrigin().getX(), bin.getOrigin().getY() + sizeBox.getY());
        if (!sizeBin.isDimensionNull() && !perfectFit) {
            bintoCreate = new Bin(sizeBin, bin.getPattern(), originBin);
            bin.getHorizontalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        }
        //Second bin
        sizeBin = new Vector(bin.getSize().getX() - sizeBox.getX(), sizeBox.getY());
        originBin = new Vector(bin.getOrigin().getX() + sizeBox.getX(), bin.getOrigin().getY());
        if (!sizeBin.isDimensionNull() && !perfectFit) {
            bintoCreate = new Bin(sizeBin, bin.getPattern(), originBin);
            bin.getHorizontalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        }
    }

}
