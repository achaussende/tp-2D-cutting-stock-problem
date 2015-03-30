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
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
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
        ArrayList<Comparator<Box>> boxComparators = new ArrayList<>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
        GuillotineSortBFFRM gui = new GuillotineSortBFFRM(boxComparators);
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Vector(1, 2), 1));
        boxes.add(new Box(new Vector(5, 4), 1));
        boxes.add(new Box(new Vector(3, 7), 1));
        boxes.add(new Box(new Vector(2, 2), 1));

        ArrayList<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(new Vector(10, 20), boxes));
        patterns.add(new Pattern(new Vector(20, 10), boxes));
        Solution solution = new Solution(patterns);
        Solution retSolution = gui.getPlacing(solution);
        assertNotNull(retSolution);
        assertNotNull(retSolution.getPatterns().get(0).getPlacedBoxes().get(0).getPosition());
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
        bins.clear();
        bins.add(new Bin(new Vector(4, 4), null, new Vector(4, 4)));
        bins.add(new Bin(new Vector(4, 4), null, new Vector(0, 4)));
        bins.add(new Bin(new Vector(4, 6), null, new Vector(8, 4)));
        bins.add(new Bin(new Vector(4, 4), null, new Vector(4, 0)));
        gui.rectangleMerge(bins);
        assertEquals(5, bins.size());
        assertEquals(3, bins.parallelStream().filter(b -> b.isActive()).count());
        bins.clear();
        bins.add(new Bin(new Vector(8, 4), null, new Vector(1, 1)));
        bins.add(new Bin(new Vector(8, 4), null, new Vector(1, 5)));
        bins.add(new Bin(new Vector(3, 1), null, new Vector(3, 13)));
        bins.add(new Bin(new Vector(3, 1), null, new Vector(3, 14)));
        gui.rectangleMerge(bins);
        assertEquals(6, bins.size());
        assertEquals(2, bins.parallelStream().filter(b -> b.isActive()).count());
    }
}
