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

package com.polytech4A.cuttingstock.core.solver.neighbour;

/**
 * Created by Adrien CHAUSSENDE on 10/04/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class DynPatternIncrementNeighbourTest extends IncrementNeighbourTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setGenerator(new DynPatternIncrementNeighbour());
    }
}
