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

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.PlacedBox;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Antoine CARON on 22/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Implementation of a Packing Algorithm Guillotine Sort Best Fit First with Rectangle Merging.
 */
public class GuillotineSortBFFRM extends GuillotineSortBFF {

    /**
     * Contructor.
     *
     * @param boxComparators comparators to use for the Best Fit First.
     */
    public GuillotineSortBFFRM(ArrayList<Comparator<Box>> boxComparators) {
        super(boxComparators);
    }

    /**
     * Merge two Bins and add it to Bean Arralylist.
     *
     * @param bins       List of currentBins
     * @param createdBin Bin creatd by merging.
     * @param bin1       Bin to disable.
     * @param bin2       Bin to disable.
     */
    private static void merge(@NotNull ArrayList<Bin> bins, @NotNull Bin createdBin, @NotNull Bin bin1, @NotNull Bin bin2, Boolean binMerged) {
        bins.add(createdBin);
        bin1.setActive(false);
        bin2.setActive(false);
        binMerged = true;
    }

    /**
     * Place the box on the pattern.
     *
     * @param bins List of bins that will be generated.
     * @param box  Box to be placed.
     * @return The box that is now placed. Return null if it can't be placed.
     */
    @Override
    public PlacedBox generatePlacedBox(@NotNull ArrayList<Bin> bins, @NotNull Box box) {
        for (Bin bin : bins.parallelStream().filter(b -> b.isActive()).collect(Collectors.toList())) {
            if (bin.isActive() && (isBinCompatible(bin.getSize(), box.getSize()) || isBinCompatible(bin.getSize(), box.getSize().getInvertedVector()))) {
                PlacedBox placedBox = generatePlaceBoxForBin(bin, box);
                if (placedBox != null) {
                    //mark Current Bin
                    bin.setActive(false);
                    //Disable bin brothers from other cutting (Vertical or horizontal
                    bin.disableSubBinFromBin(bins);
                    //Generate subBins and adds it
                    generateSubBins(bin, placedBox, bins);
                    //Merging Bins.
                    rectangleMerge(bins);
                    return placedBox;
                }
            }
        }
        return null;
    }

    /**
     * Rectangle Merging.
     *
     * @param bins List of Bins to Merge.
     */
    public void rectangleMerge(ArrayList<Bin> bins) {
        for (Bin bin : bins) {
            if (bin.isActive()) {
                List<Bin> sameXBins = bins.parallelStream().filter(b -> (b != bin && (b.getSize().getX() == bin.getSize().getX()))).collect(Collectors.toList());
                List<Bin> sameYBins = bins.parallelStream().filter(b -> (b != bin && (b.getSize().getY() == bin.getSize().getY()))).collect(Collectors.toList());
                Vector v = new Vector(0, 0);
                Boolean binMerged = false;
                Bin mBin;
                for (Bin yBin : sameYBins) {
                    //ybin is at right
                    v.set(yBin.getOrigin().getX() - bin.getSize().getX(), yBin.getOrigin().getY());
                    if (v.equals(bin.getOrigin())) {
                        mBin = new Bin(new Vector(yBin.getSize().getX() + bin.getSize().getX(), yBin.getSize().getY()), bin.getPattern(), yBin.getOrigin());
                        merge(bins, mBin, yBin, bin, binMerged);
                    }
                    //ybin is at left
                    v.set(yBin.getOrigin().getX() + yBin.getSize().getX(), bin.getOrigin().getY());
                    if (!binMerged && v.equals(bin.getOrigin())) {
                        mBin = new Bin(new Vector(yBin.getSize().getX() + bin.getSize().getX(), yBin.getSize().getY()), bin.getPattern(), bin.getOrigin());
                        merge(bins, mBin, yBin, bin, binMerged);
                    }
                }
                if (!binMerged) {
                    for (Bin xBin : sameXBins) {
                        //xBin is at top
                        v.set(xBin.getOrigin().getX(), xBin.getOrigin().getY() + xBin.getSize().getY());
                        if (v.equals(bin.getOrigin())) {
                            mBin = new Bin(new Vector(xBin.getSize().getX(), xBin.getSize().getY() + bin.getSize().getY()), bin.getPattern(), xBin.getOrigin());
                            merge(bins, mBin, xBin, bin, binMerged);
                        }
                        //xBin is at bottom
                        v.set(xBin.getOrigin().getX(), xBin.getOrigin().getY() - bin.getSize().getY());
                        if (!binMerged && v.equals(bin.getOrigin())) {
                            mBin = new Bin(new Vector(xBin.getSize().getX(), xBin.getSize().getY() + bin.getSize().getY()), bin.getPattern(), bin.getOrigin());
                            merge(bins, mBin, xBin, bin, binMerged);
                        }
                    }
                }
                binMerged = false;
            }
        }
    }
}
