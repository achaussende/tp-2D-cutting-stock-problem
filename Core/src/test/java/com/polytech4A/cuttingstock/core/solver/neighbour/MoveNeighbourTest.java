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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrien CHAUSSENDE on 30/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class MoveNeighbourTest extends TestCase {

    private Solution solution;

    private MoveNeighbour generator;

    public void setGenerator(MoveNeighbour generator) {
        this.generator = generator;
    }

    public void setUp() {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 2));
        solutionBoxes.add(new Box(new Vector(4,5), 1));
        solutionBoxes.add(new Box(new Vector(4,3), 0));
        solutionBoxes.add(new Box(new Vector(4,6), 5));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        solution = new Solution(patterns);
        generator = new MoveNeighbour();
    }

    public void tearDown() {
        solution = null;
        generator = null;
    }

    public void testGetNeighbourhood() {
        Solution neighbour = generator.getRandomNeighbour(solution);
        assertEquals(solution.getPatterns().parallelStream()
                        .mapToInt(p -> p.getAmounts().parallelStream()
                                .mapToInt(b -> b.getAmount())
                                .sum())
                        .sum(),
                neighbour.getPatterns().parallelStream()
                        .mapToInt(p -> p.getAmounts().parallelStream()
                                .mapToInt(b -> b.getAmount())
                                .sum())
                        .sum());
        System.out.println(neighbour);
    }
}
