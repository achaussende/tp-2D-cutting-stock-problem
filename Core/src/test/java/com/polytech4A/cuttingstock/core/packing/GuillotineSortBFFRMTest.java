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
import com.polytech4A.cuttingstock.core.model.Vector;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 30/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          TestCase
 */
public class GuillotineSortBFFRMTest extends TestCase {

    public void testGeneratePlacedBox() throws Exception {

    }

    public void testRectangleMerge() throws Exception {
        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(new Bin(new Vector(4, 4), null, new Vector(4, 4)));
        bins.add(new Bin(new Vector(4, 4), null, new Vector(8, 4)));
        ArrayList<Comparator<Box>> boxComparators = new ArrayList<>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
        GuillotineSortBFFRM gui = new GuillotineSortBFFRM(boxComparators);
        gui.rectangleMerge(bins);
        assertEquals(3, bins.size());
        assertEquals(1, bins.parallelStream().filter(bin -> bin.isActive()).count());
    }
}
