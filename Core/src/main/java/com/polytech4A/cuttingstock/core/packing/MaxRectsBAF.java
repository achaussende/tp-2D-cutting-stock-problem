/*
 * Project to resolve 2D cutting stock problem for Discreet Optimizations course at Polytech Lyon
 * Copyright (C) 2015.  CARON Antoine and CHAUSSENDE Adrien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.polytech4A.cuttingstock.core.packing;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.PlacedBox;
import com.polytech4A.cuttingstock.core.model.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Adrien CHAUSSENDE on 09/05/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class MaxRectsBAF extends GuillotineSortBAF {

    public MaxRectsBAF(ArrayList<Comparator<Box>> boxComparators) {
        super(boxComparators);
    }

    @Override
    public void generateSubBins(Bin bin, PlacedBox placedBox, ArrayList<Bin> bins) {
        Vector sizeBox;
        Bin bintoCreate;
        if (placedBox.isRotation()) {
            sizeBox = placedBox.getSize().getInvertedVector();
        } else {
            sizeBox = placedBox.getSize();
        }
        //Horizontal cut
        Vector sizeBin = new Vector(bin.getSize().getX(), bin.getSize().getY() - sizeBox.getY());
        Vector originBin = new Vector(bin.getOrigin().getX(), bin.getOrigin().getY() + sizeBox.getY());
        if (!sizeBin.isDimensionNull()) {
            bintoCreate = new MaxRectsBin(sizeBin, bin.getPattern(), originBin);
            bin.getHorizontalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        }

        //Vertical cut
        sizeBin = new Vector(bin.getSize().getX() - sizeBox.getX(), bin.getSize().getY());
        originBin = new Vector(bin.getOrigin().getX() + sizeBox.getX(), bin.getOrigin().getY());
        if (!sizeBin.isDimensionNull()) {
            bintoCreate = new MaxRectsBin(sizeBin, bin.getPattern(), originBin);
            bin.getVerticalsubBin().add(bintoCreate);
            bins.add(bintoCreate);
        }
    }

    @Override
    public Pattern generatePattern(Pattern p, Comparator<Box> comparator) {
        ArrayList<Box> boxes = p.getBoxes();
        Collections.sort(boxes, Collections.reverseOrder(comparator));
        Bin bin = new MaxRectsBin(p.getSize(), p, new Vector(0, 0));
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
}
