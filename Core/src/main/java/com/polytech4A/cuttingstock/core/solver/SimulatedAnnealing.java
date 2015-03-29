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
import com.polytech4A.cuttingstock.core.resolution.Resolution;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import com.sun.istack.internal.NotNull;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adrien CHAUSSENDE on 13/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p/>
 *          Implementation of Simulated Annealing algorithm for the Cutting Stock problem. See more about the algorithm :
 *          http://en.wikipedia.org/wiki/Simulated_annealing
 */
public class SimulatedAnnealing extends NeighbourSolver {

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
    private double temperature;

    /**
     * Method to resolve linear programming problem.
     */
    private LinearResolutionMethod method;

    @Override
    public Solution getSolution(@NotNull Solution solution, @NotNull Context context) {
        return null;
    }


}
