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
import com.polytech4A.cuttingstock.core.model.Solution;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Abstract packer algorithm for 2D cutting stock problem.
 */
public abstract class Packer {

    /**
     * Comparators of
     */
    protected ArrayList<Comparator<Box>> boxComparators;

    public Packer(ArrayList<Comparator<Box>> boxComparators) {
        this.boxComparators = boxComparators;
    }

    /**
     * Generating a solution with Patterns composed by
     *
     * @param solution a Solution
     * @return Solution with Patterns composed by PlaceBox.
     * @see com.polytech4A.cuttingstock.core.model.Solution
     * @see com.polytech4A.cuttingstock.core.model.PlacedBox
     * @see com.polytech4A.cuttingstock.core.model.Pattern
     */
    public abstract Solution getPlacing(@NotNull Solution solution);
}
