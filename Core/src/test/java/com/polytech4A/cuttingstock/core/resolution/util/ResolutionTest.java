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

package com.polytech4A.cuttingstock.core.resolution.util;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.polytech4A.cuttingstock.core.resolution.Resolution;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 02/04/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Test Case for resolution.
 */
public class ResolutionTest extends TestCase {

    public void testGenerateFirstSolution() throws Exception {
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Vector(1, 2), 100));
        boxes.add(new Box(new Vector(5, 4), 234));
        boxes.add(new Box(new Vector(3, 7), 3));
        boxes.add(new Box(new Vector(2, 2), 15));
        Context context = new Context("Test", 20, 1, boxes, new Vector(5, 5));
        Solution solution = Resolution.generateFirstSolution(context);
        assertTrue(!solution.getPatterns().isEmpty());
    }
}
