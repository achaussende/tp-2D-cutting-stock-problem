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

    /**
     * Get a random neighbour from a solution in parameter.
     *
     * @param solutions solutions to generate neighbour.
     * @return a packable solution neighbour of the solution in parameter.
     */
    public Solution getRandomSolutionFromNeighbour(final ArrayList<Solution> solutions) {
        Solution packedSolution;
        Solution retSolution;
        do {
            packedSolution = null;
            retSolution = null;
            Random random = new Random(System.currentTimeMillis());
            retSolution = solutions.get(random.nextInt(solutions.size()));
            retSolution.removeEmpty();
            packedSolution = packer.getPlacing(retSolution);
        } while (packedSolution == null || packedSolution.getPatterns().size() != retSolution.getPatterns().size());
        return (retSolution != null) ? retSolution : retSolution;
    }

    /**
     * Get list of generated neighbour form a solution.
     *
     * @param solution solution onto generate neighbour.
     * @return ArrayList of solution
     */
    protected ArrayList<Solution> generateNeighbour(final Solution solution) {
        ArrayList<Solution> solutions = new ArrayList<>();
        for (INeighbourUtils generator : neighbourGenerator) {
            solutions.addAll(generator.getNeighbourhood(solution));
        }
        return solutions;
    }

    /**
     * Generate a random solution from a solution in parameter.
     * (iterate random choice on neighbour of the solution)
     *
     * @param solution First solution of the solver.
     * @return Random solution generated form neighbour.
     */
    public Solution getRandomSolution(final Solution solution) {
        Solution retSolution = solution.clone();
        for (int i = 0; i < RANDOM_SOLUTION_NB; i++) {
            ArrayList<Solution> solutions = this.generateNeighbour(retSolution);
            retSolution = getRandomSolutionFromNeighbour(solutions);
            solutions = null;
        }
        return retSolution;
    }

    /**
     * Get a random packable solution form neighbours of a solutions.
     *
     * @param solution solution to extract packable neighbours.
     * @return a Random Packable solution from neighbour of a solution.
     */
    public Solution getRandomSolutionFromSolution(final Solution solution) {
        return getRandomSolutionFromNeighbour(generateNeighbour(solution));
    }
}