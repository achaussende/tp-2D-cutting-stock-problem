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

package com.polytech4A.cuttingstock.core.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Adrien CHAUSSENDE on 29/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class SolutionTest extends TestCase {

    private Solution solution;

    @Before
    public void setUp() {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 2));
        solutionBoxes.add(new Box(new Vector(4, 5), 1));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        solution = new Solution(patterns);
    }

    @After
    public void tearDown() {
        solution = null;
    }

    @Test
    public void testToString() {
        assertEquals("Solution = {(2,1)}", solution.toString());
    }

    public void testRemoveEmpty() throws Exception {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 2));
        solutionBoxes.add(new Box(new Vector(4, 5), 1));
        ArrayList<Box> solutionBoxes2 = new ArrayList<Box>();
        solutionBoxes2.add(new Box(new Vector(4, 2), 0));
        solutionBoxes2.add(new Box(new Vector(4, 5), 28));
        ArrayList<Box> solutionBoxes3 = new ArrayList<Box>();
        solutionBoxes3.add(new Box(new Vector(4, 2), 0));
        solutionBoxes3.add(new Box(new Vector(4, 5), 0));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes2));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes3));
        Solution solution1 = new Solution(patterns);
        solution1.removeEmpty();
        assertEquals(2, solution1.getPatterns().size());
    }
}
