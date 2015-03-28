/*
 * Project to resolve 2D cutting stock problem for Discreet Optimizations course at Polytech Lyon
 * Copyright (C) 2015.  CARON Antoine and CHAUSSENDE Adrien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.polytech4A.cuttingstock.core.method;

/**
 * Created by Adrien CHAUSSENDE on 27/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *
 * Bin class to keep results from the methods for the 2D cutting stock problem.
 */
public class Result {
    /**
     * Number of printings for each pattern.
     */
    private double[] printings;

    /**
     * Cost found during the resolution method.
     */
    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double[] getPrintings() {
        return printings;
    }

    public void setPrintings(double[] printings) {
        this.printings = printings;
    }

    public Result(double[] printings, double cost) {
        this.printings = printings;
        this.cost = cost;
    }
}
