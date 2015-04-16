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
import org.apache.log4j.Logger;

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

    private static final int RANDOM_SOLUTION_NB = 1000;

    private static final Logger logger = Logger.getLogger(NeighbourSolver.class);

    /**
     * List containing neighbours generator objects.
     */
    private ArrayList<INeighbourUtils> neighbourGenerator;

    public NeighbourSolver(Packer packer, LinearResolutionMethod simplex, ArrayList<INeighbourUtils> neighbourGenerator) {
        super(packer, simplex);
        this.neighbourGenerator = neighbourGenerator;
    }

    /**
     * Choose a random Neighbour method.
     *
     * @return INeighbourUtils.
     */
    public INeighbourUtils chooseRandomNeihbourUtils() {
        Random rd = new Random(System.currentTimeMillis());
        return neighbourGenerator.get(rd.nextInt(neighbourGenerator.size()));
    }

    /**
     * Generate a random solution from a solution in parameter.
     * (iterate random choice on neighbour of the solution)
     *
     * @param solution    First solution of the solver (has to be packable and valid)
     * @param removeEmpty if true, removes empty pattern from solution.
     * @return Random solution generated form neighbour.
     */
    public Solution getRandomSolution(final Solution solution, final boolean removeEmpty) {
        Solution retSolution;
        Solution packedSolution;
        Solution bestFoundSolution = packer.getPlacing(solution);
        int i = 0;
        do {
            retSolution = chooseRandomNeihbourUtils().getRandomNeighbour(bestFoundSolution);
            packedSolution = packer.getPlacing(retSolution);
            i++;
            if (packedSolution.isPackable() && packedSolution.isValid()) {
                packedSolution.removeEmpty();
                if (packedSolution.getPatterns().size() <= bestFoundSolution.getPatterns().size()) {
                    bestFoundSolution = packedSolution;
                }
            }
        } while (i < RANDOM_SOLUTION_NB);
        return bestFoundSolution;
    }
}