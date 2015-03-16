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
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 15/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class GuillotineSortBFFTest extends TestCase {

    private ArrayList<Comparator<Box>> boxComparators;

    private GuillotineSortBFF guillotineSortBFF;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        boxComparators = new ArrayList<Comparator<Box>>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
        guillotineSortBFF = new GuillotineSortBFF(boxComparators);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGeneratePlaceBoxForBin() throws Exception {
        Bin bin = new Bin(new Vector(4, 7), null, new Vector(0, 1));
        Box box = new Box(new Vector(8, 9), 1);
        Box box1 = new Box(new Vector(7, 4), 1);
        Box box2 = new Box(new Vector(3, 7), 1);
        assertEquals(null, guillotineSortBFF.generatePlaceBoxForBin(bin, box));
        PlacedBox placedBox = guillotineSortBFF.generatePlaceBoxForBin(bin, box1);
        assertEquals(true, placedBox.getSize().equals(new Vector(7, 4)));
        assertEquals(true, placedBox.isRotation());
        PlacedBox placedBox1 = guillotineSortBFF.generatePlaceBoxForBin(bin, box2);
        assertEquals(true, placedBox1.getSize().equals(new Vector(3, 7)));
        assertEquals(false, placedBox1.isRotation());
    }
}
