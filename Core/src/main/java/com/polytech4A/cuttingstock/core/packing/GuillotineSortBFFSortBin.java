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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 26/04/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class GuillotineSortBFFSortBin extends GuillotineSortBFF {

    public GuillotineSortBFFSortBin(ArrayList<Comparator<Box>> boxComparators) {
        super(boxComparators);
    }

    /**
     * Place the box on the pattern.
     *
     * @param bins List of bins that will be generated.
     * @param box  Box to be placed.
     * @return The box that is now placed. Return null if it can't be placed.
     */
    @Override
    public PlacedBox generatePlacedBox(ArrayList<Bin> bins, Box box) {
        Collections.sort(bins);
        return super.generatePlacedBox(bins, box);
    }
}
