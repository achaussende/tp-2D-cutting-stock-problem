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

package com.polytech4A.cuttingstock.core.method;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adrien CHAUSSENDE on 21/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Representation of the linear resolution method for 2D cutting stock problem.
 *          It is a linear programming problem resolution.
 */
public class LinearResolutionMethod {

    /**
     * Function to minimize.
     */
    private LinearObjectiveFunction function;

    /**
     * List containing the constraints that the function is subject to.
     */
    private ArrayList<LinearConstraint> constraints;

    /**
     * Current context.
     */
    private Context context;

    /**
     * Create a LinearResolution Method with initial resolution to initialize function and constraints.
     *
     * @param context Current context.
     */
    public LinearResolutionMethod(Context context) {
        this.context = context;
    }

    /**
     * Resolve linear programming problem when minimizing the equation with current constraints. Returns
     *
     * @param solution Solution to minimize the objective the function from.
     * @return Number of printings and cost value.
     */
    public Result minimize(Solution solution) {
        updateFunction(solution);
        updateConstraints(solution);
        try {
            RealPointValuePair result = new SimplexSolver().optimize(function, constraints, GoalType.MINIMIZE, true);
            double[] point = result.getPoint();
            if (result.getValue() < 0) {
                return null;
            }
            for (int i = 0; i < point.length; ++i) {
                if (point[i] < 0) {
                    return null;
                }
            }
            return new Result(point, context.getSheetCost());
        } catch (OptimizationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update current context's objective function using the solution.
     *
     * @param solution Solution used to update the function.
     */
    public void updateFunction(Solution solution) {
        double[] coefficients = new double[solution.getPatterns().size()];
        Arrays.fill(coefficients, context.getSheetCost());
        function = new LinearObjectiveFunction(coefficients, solution.getPatterns().size() * context.getPatternCost());
    }

    /**
     * Update current context's constraints using the solution.
     *
     * @param solution Solution used to update the constraints.
     */
    public void updateConstraints(Solution solution) {
        ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        double[] coefficients;
        int i;
        for (Box ctxBox : context.getBoxes()) {
            coefficients = new double[solution.getPatterns().size()];
            i = 0;
            for (Pattern p : solution.getPatterns()) {
                for (Box box : p.getAmounts()) {
                    if (box.equals(ctxBox)) {
                        coefficients[i] = box.getAmount();
                        ++i;
                    }
                }
            }
            constraints.add(new LinearConstraint(coefficients, Relationship.GEQ, ctxBox.getAmount()));
        }
        this.constraints = constraints;
    }

}
