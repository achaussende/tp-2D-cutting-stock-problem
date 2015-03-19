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

package com.polytech4A.cuttingstock.core.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class PatternTest {

    private Pattern pattern;

    private Box boxeToTest;

    @Before
    public void setUp() throws Exception {
        ArrayList<Box> boxes = new ArrayList<Box>();
        pattern = new Pattern(new Vector(1, 1), boxes);
        boxeToTest = new Box(new Vector(933.0, 372.0), 6);
        boxes.add(boxeToTest);
        boxes.add(new Box(new Vector(893.0, 307.0), 2));
        boxes.add(new Box(new Vector(727.0, 333.0), 5));
    }

    @After
    public void tearDown() throws Exception {
        pattern.getAmounts().clear();
        pattern = null;
        boxeToTest = null;
    }

    @Test
    public void testGetBoxes() throws Exception {
        ArrayList<Box> result = pattern.getBoxes();
        TestCase.assertEquals(13, result.size());
        TestCase.assertEquals(6, Collections.frequency(result, boxeToTest));
    }
}