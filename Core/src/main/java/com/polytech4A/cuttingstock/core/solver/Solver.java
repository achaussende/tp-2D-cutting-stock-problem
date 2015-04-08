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
import com.sun.istack.internal.NotNull;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Generic solver for 2D cutting stock problem.
 */
public abstract class Solver {

    /**
     * Packer Algorithm for packing boxes in Patterns.
     */
    protected Packer packer;

    /**
     * Simplex resolution for calculating objective function (See Simplex Algorithm).
     */
    protected LinearResolutionMethod simplex;


    public Solver(Packer packer, LinearResolutionMethod simplex) {
        this.packer = packer;
        this.simplex = simplex;
    }

    /**
     * @param solution Initial solution for Solver starting.
     * @param context  Initial context for Solver starting.
     *                 Contains all information about Patterns, Boxes and Cost.
     * @return
     */
    @NotNull
    public abstract Solution getSolution(@NotNull Solution solution, @NotNull Context context);
}
