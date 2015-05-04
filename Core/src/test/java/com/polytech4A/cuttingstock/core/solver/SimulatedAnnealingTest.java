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

package com.polytech4A.cuttingstock.core.solver;

import com.polytech4A.cuttingstock.core.method.LinearResolutionMethod;
import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.polytech4A.cuttingstock.core.packing.GuillotineSortBFF;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import com.polytech4A.cuttingstock.core.solver.neighbour.DynPatternIncrementNeighbour;
import com.polytech4A.cuttingstock.core.solver.neighbour.DynPatternMoveNeighbour;
import com.polytech4A.cuttingstock.core.solver.neighbour.INeighbourUtils;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 10/04/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          TestCase for Simulated Annealing Algorithm.
 */
public class SimulatedAnnealingTest extends TestCase {

    private SimulatedAnnealing sAnnealing;

    private Solution solution;

    public void setUp() throws Exception {
        super.setUp();
        ArrayList<Comparator<Box>> boxComparators;
        boxComparators = new ArrayList<>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
        ArrayList<INeighbourUtils> generators = new ArrayList<>();
        generators.add(new DynPatternIncrementNeighbour());
        generators.add(new DynPatternMoveNeighbour());
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 1));
        solutionBoxes.add(new Box(new Vector(4, 5), 0));
        solutionBoxes.add(new Box(new Vector(4, 3), 0));
        ArrayList<Box> solutionBoxes1 = new ArrayList<Box>();
        solutionBoxes1.add(new Box(new Vector(4, 2), 0));
        solutionBoxes1.add(new Box(new Vector(4, 5), 1));
        solutionBoxes1.add(new Box(new Vector(4, 3), 0));
        ArrayList<Box> solutionBoxes2 = new ArrayList<Box>();
        solutionBoxes2.add(new Box(new Vector(4, 2), 0));
        solutionBoxes2.add(new Box(new Vector(4, 5), 0));
        solutionBoxes2.add(new Box(new Vector(4, 3), 1));
        patterns.add(new Pattern(new Vector(20, 30), solutionBoxes));
        patterns.add(new Pattern(new Vector(20, 30), solutionBoxes1));
        patterns.add(new Pattern(new Vector(20, 30), solutionBoxes2));
        ArrayList<Box> contextBoxes = new ArrayList<>();
        contextBoxes.add(new Box(new Vector(4, 2), 300));
        contextBoxes.add(new Box(new Vector(4, 5), 57));
        contextBoxes.add(new Box(new Vector(4, 3), 220));
        Context ctx = new Context("test", 20, 1, contextBoxes, new Vector(2, 30));
        sAnnealing = new SimulatedAnnealing(new GuillotineSortBFF(boxComparators), new LinearResolutionMethod(ctx), generators, 10000);
        solution = new Solution(patterns);
    }

    public void tearDown() throws Exception {
        sAnnealing = null;
        solution = null;
    }


    public void testGetRandomSolution() {
        Solution retSolution = sAnnealing.getRandomSolution(solution, true);
        assertTrue(retSolution.isPackable());
    }
    
}