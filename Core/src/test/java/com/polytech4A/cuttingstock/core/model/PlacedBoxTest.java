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

package com.polytech4A.cuttingstock.core.model;

import junit.framework.TestCase;

/**
 * Created by Antoine CARON on 29/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          TestCase for PlacedBox Class.
 */
public class PlacedBoxTest extends TestCase {

    public void testClone() throws Exception {
        PlacedBox p = new PlacedBox(new Vector(1, 2), 2, new Vector(1, 5), true),
                p1 = p.clone();
        assertFalse(p == p1);
        assertEquals(p.getPosition(), p1.getPosition());
        assertEquals(p.getAmount(), p1.getAmount());
        assertEquals(p.getSize(), p1.getSize());
        assertEquals(p.isRotation(), p1.isRotation());
    }
}
