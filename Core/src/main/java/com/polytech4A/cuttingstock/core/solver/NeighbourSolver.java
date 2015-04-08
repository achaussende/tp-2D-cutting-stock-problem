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
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.packing.Packer;
import com.polytech4A.cuttingstock.core.solver.neighbour.INeighbourUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrien CHAUSSENDE on 28/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Representation of a solver that uses mathematical notion of neighbours.
 */
public abstract class NeighbourSolver extends Solver {

    private static final int RANDOM_SOLUTION_NB = 100;
    /**
     * List containing neighbours generator objects.
     */
    private ArrayList<INeighbourUtils> neighbourGenerator;

    public NeighbourSolver(Packer packer, LinearResolutionMethod simplex, ArrayList<INeighbourUtils> neighbourGenerator) {
        super(packer, simplex);
        this.neighbourGenerator = neighbourGenerator;
    }

    public Solution getRandomSolution(final Solution solution) {
        Solution retSolution = solution.clone();
        for (int i = 0; i < RANDOM_SOLUTION_NB; i++) {
            ArrayList<Solution> solutions = new ArrayList<>();
            for (INeighbourUtils generator : neighbourGenerator) {
                solutions.addAll(generator.getNeighbourhood(retSolution));
            }
            Solution packedSolution;
            do {
                packedSolution = null;
                Random random = new Random(System.currentTimeMillis());
                retSolution = solutions.get(random.nextInt(solutions.size()));
                packedSolution = packer.getPlacing(retSolution);
            }
            while (packedSolution != null && packedSolution.getPatterns().size() == retSolution.getPatterns().size());
            solutions = null;
        }
        return retSolution;
    }
}