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
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

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
public class GuillotineSortBFF extends Packer {

    private static final Logger logger = Logger.getLogger(GuillotineSortBFF.class);

    public GuillotineSortBFF(ArrayList<Comparator<Box>> boxComparators) {
        super(boxComparators);
    }

    @Override
    public Solution getPlacing(@NotNull Solution solution) {
        ArrayList<Pattern> patterns = solution.getPatterns();
        patterns.parallelStream().forEach(p -> {
            Pattern pattern = null;
            for (Comparator<Box> comparator : boxComparators) {
                ArrayList<Box> boxes = p.getBoxes();
                pattern = generatePattern(p, boxes, comparator);
                if (pattern != null) { //take the first Pattern Found.
                    p.setPlacedBoxes(pattern.getPlacedBoxes());
                    break;
                }
            }
        });
        logger.debug("Solution packed " + solution);
        return solution;
    }

    /**
     * Check if a box can be placed in the bin.
     *
     * @param binVector Vector of the bin.
     * @param boxVector Box to be placed in the bin.
     * @return True if the box can be placed in the bin.
     */
    public boolean isBinCompatible(@NotNull Vector binVector, @NotNull Vector boxVector) {
        return boxVector.isSmallerThan(binVector);
    }

    /**
     * Generate pattern with placed boxes on it.
     *
     * @param p          Kind of pattern where the boxes will be placed.
     * @param boxes      Boxes that will be placed.
     * @param comparator Comparator to use when sorting boxes.
     * @return A new pattern with the boxes placed on it. Return null if there is no boxes to place.
     */
    @Nullable
    public Pattern generatePattern(@NotNull Pattern p, @NotNull ArrayList<Box> boxes, @NotNull Comparator<Box> comparator) {
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
    @Nullable
    public ArrayList<PlacedBox> generatePlacedBoxes(@NotNull ArrayList<Box> boxes, @NotNull ArrayList<Bin> bins) {
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
    @Nullable
    public PlacedBox generatePlacedBox(@NotNull ArrayList<Bin> bins, @NotNull Box box) {
        for (Bin bin : bins) {
            if (bin.isActive() && (isBinCompatible(bin.getSize(), box.getSize()) || isBinCompatible(bin.getSize(), box.getSize().getInvertedVector()))) {
                PlacedBox placedBox = generatePlaceBoxForBin(bin, box);
                if (placedBox != null) {
                    //mark Current Bin
                    bin.setActive(false);
                    //Disable bin borthers from other cutting (Vertical or horizontal
                    bin.disableSubBinFromBin(bins);
                    //Generate subBins and adds it
                    generateSubBins(bin, placedBox, bins);
                    return placedBox;
                }
            }
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
    @Nullable
    public PlacedBox generatePlaceBoxForBin(@NotNull Bin bin, @NotNull Box boxToPlace) {
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
        originBin = new Vector(bin.getOrigin().getX() + placedBox.getPosition().getX(), bin.getOrigin().getY());
        if (!sizeBin.isDimensionNull() && !perfectFit) {
            bintoCreate = new Bin(sizeBin, bin.getPattern(), originBin);
            bin.getHorizontalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        }
    }

}
