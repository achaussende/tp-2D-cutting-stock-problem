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
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import com.polytech4A.cuttingstock.core.solver.neighbour.INeighbourUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Adrien CHAUSSENDE on 13/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Implementation of Simulated Annealing algorithm for the Cutting Stock problem. See more about the algorithm :
 *          http://en.wikipedia.org/wiki/Simulated_annealing
 */
public class SimulatedAnnealing extends NeighbourSolver {

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(SimulatedAnnealing.class);

    /**
     * Coefficient of multiplication for generation of the first solution.
     */
    private static final double temp_coef = 1.1;
    /**
     * Limit number of iterations of the algorithm.
     */
    private long nbIterations;

    /**
     * Common ratio for decreasing the temperature through geometric progression.
     */
    private double mu;

    /**
     * Global time-varying parameter used in the algorithm.
     * See : http://en.wikipedia.org/wiki/Simulated_annealing#Acceptance_probabilities
     */
    private double temperature = -1;

    public SimulatedAnnealing(Packer packer, LinearResolutionMethod simplex, ArrayList<INeighbourUtils> neighbourGenerator,
                              long nbIterations) {
        super(packer, simplex, neighbourGenerator);
        this.nbIterations = nbIterations;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getMu() {
        return mu;
    }

    @Override
    public Solution getSolution(Solution solution, Context context) {
        return null;
    }

    /**
     * Set Temperature of Simulated Annealing Algorithm.
     *
     * @param solution solution onto generate temperature.
     * @return -1 if definition of the first temperature failed.
     * Temperature generated.
     */
    public double setFirstTemperature(Solution solution) {
        Solution clSolution = solution.clone();
        ArrayList<Solution> solutionsN = new ArrayList<>(); // Solutions from the neighbourhood of the solution.
        int nbIteration = 1000 * solution.getPatterns().get(0).getAmounts().size();
        for (int i = 0; i < nbIteration; i++) {
            Solution randomSolution = chooseRandomNeihbourUtils().getRandomNeighbour(clSolution);
            Solution packedSolution = packer.getPlacing(randomSolution);

            if (packedSolution.isPackable() && packedSolution.isValid()) {
                solutionsN.add(packedSolution);
            }
        }
        try {
            double temperature = solutionsN
                    .parallelStream()
                    .mapToDouble(s -> simplex.minimize(s).getCost())
                    .max()
                    .getAsDouble() * temp_coef;
            this.temperature = temperature;
            return temperature;
        } catch (NoSuchElementException ex) {
            logger.error("Generation of first temperature", ex);
        }
        this.temperature = -1;
        return -1;
    }

    /**
     * Generate Mu from temperature and nbIterations.
     *
     * @return mu generated value.
     */
    public double setMu() {
        if (temperature != -1) {
            double mu = 1 - (temperature / (double) nbIterations);
            this.mu = mu;
            return mu;
        } else {
            throw new NullPointerException("Temperature isn't defined");
        }
    }
}
