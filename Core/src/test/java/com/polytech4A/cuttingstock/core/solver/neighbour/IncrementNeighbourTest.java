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

package com.polytech4A.cuttingstock.core.solver.neighbour;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Vector;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 08/04/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          TestCase for Increment Neighbour.
 */
public class IncrementNeighbourTest {

    private Solution solution;

    private IncrementNeighbour generator;

    public void setGenerator(IncrementNeighbour generator) {
        this.generator = generator;
    }

    @Before
    public void setUp() throws Exception {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 1));
        solutionBoxes.add(new Box(new Vector(4, 5), 2));
        solutionBoxes.add(new Box(new Vector(4, 3), 0));
        ArrayList<Box> solutionBoxes1 = new ArrayList<Box>();
        solutionBoxes1.add(new Box(new Vector(4, 2), 1));
        solutionBoxes1.add(new Box(new Vector(4, 5), 7));
        solutionBoxes1.add(new Box(new Vector(4, 3), 0));
        ArrayList<Box> solutionBoxes2 = new ArrayList<Box>();
        solutionBoxes2.add(new Box(new Vector(4, 2), 0));
        solutionBoxes2.add(new Box(new Vector(4, 5), 4));
        solutionBoxes2.add(new Box(new Vector(4, 3), 1));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes1));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes2));
        solution = new Solution(patterns);
        generator = new IncrementNeighbour();
    }

    @After
    public void tearDown() throws Exception {
        solution = null;
        generator = null;
    }

    @Test
    public void testGetModifications() throws Exception {
        Solution solutions = generator.getModifications(solution);
        TestCase.assertNotNull(solutions);
        TestCase.assertEquals(solution.getPatterns().size(), solutions.getPatterns().size());
    }

    @Test
    public void testGetNeighbourhood() throws Exception {
        ArrayList<Solution> solutions = generator.getNeighbourhood(solution);
        int count = solutions.get(0).getPatterns().parallelStream().mapToInt(p -> p.getAmounts().size()).sum();
        count += solution.getPatterns().parallelStream().mapToInt(p -> (int) p.getAmounts().parallelStream().filter(b -> b.getAmount() > 0).count()).sum();
        TestCase.assertEquals(count, solutions.size());
    }
}
