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
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static java.lang.Math.ceil;

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

    /**
     * Method to resolve linear programming problem.
     */
    private LinearResolutionMethod method;


    public SimulatedAnnealing(Packer packer, LinearResolutionMethod simplex, ArrayList<INeighbourUtils> neighbourGenerator,
                              long nbIterations, LinearResolutionMethod method) {
        super(packer, simplex, neighbourGenerator);
        this.nbIterations = nbIterations;
        this.method = method;
    }

    @Override
    public Solution getSolution(@NotNull Solution solution, @NotNull Context context) {
        return null;
    }

    /**
     * Set Temperature of SimulateAnnealing Algorithm.
     *
     * @param solution solution onto generate temperature.
     * @return -1 if definition of the first temperature failed.
     * Temperature generated.
     */
    public int setFirstTemperature(Solution solution) {
        ArrayList<Solution> solutions = generateNeighbour(solution);
        try {
            int temperature = (int) Math.ceil(solutions
                    .parallelStream()
                    .filter(s -> s.getPatterns().size() == solution.getPatterns().size())
                    .mapToInt(s -> (int) ceil(method.minimize(s).getCost()))
                    .max()
                    .getAsInt() * temp_coef);
            this.temperature = temperature;
            return temperature;
        } catch (NoSuchElementException ex) {
            logger.error("Generation of first temperature", ex);
        }
        temperature = -1;
        return -1;
    }

    /**
     * Generate Mu from temperature and nbIterations.
     *
     * @return mu generated value.
     */
    public double setMu() {
        if (temperature != -1) {
            double mu = Math.floor(temperature / nbIterations);
            this.mu = mu;
            return mu;
        } else {
            throw new NullPointerException("Temperature isn't defined");
        }
    }
}
