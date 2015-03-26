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

package com.polytech4A.cuttingstock.core.save;

import com.polytech4A.cuttingstock.core.model.Solution;

/**
 * Created by Antoine CARON on 24/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Abstract Saving method for CuttingStock Final result.
 *          Files saved will be in the Output directory define in OUTPUT_PATH
 *          Name of File will be, ${Context.id}-timestamp
 */
public abstract class Save {

    /**
     * Output directory.
     */
    protected final static String OUTPUT_PATH = "../../OUTPUT/";

    /**
     * Save Method.
     *
     * @param contextId Name of the file.
     * @param solution  Final Solution.
     */
    public abstract void save(String contextId, Solution solution);
}
