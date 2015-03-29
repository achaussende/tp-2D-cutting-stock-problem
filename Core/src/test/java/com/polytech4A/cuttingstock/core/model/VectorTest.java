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

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 29/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          TestCase for Vector Class.
 */
public class VectorTest extends TestCase {

    public void testGetArea() throws Exception {
        ArrayList<Vector> v = new ArrayList<>();
        v.add(new Vector(0, 1));
        v.add(new Vector(3, 5));
        assertEquals((double) 0, v.get(0).getArea());
        assertEquals((double) 15, v.get(1).getArea());
    }

    public void testIsSmallerThan() throws Exception {
        ArrayList<Vector> v = new ArrayList<>();
        v.add(new Vector(0, 1));
        v.add(new Vector(3, 5));
        v.add(new Vector(2, 4));
        assertTrue(v.get(0).isSmallerThan(v.get(1)));
        assertFalse(v.get(1).isSmallerThan(v.get(2)));
    }

    public void testGetInvertedVector() throws Exception {
        Vector v = new Vector(2, 3);
        assertEquals(new Vector(3, 2), v.getInvertedVector());
    }

    public void testIsDimensionNull() throws Exception {
        Vector v = new Vector(0, 1),
                v1 = new Vector(0, 0),
                v2 = new Vector(1, 0),
                v3 = new Vector(2, 3);
        assertTrue(v.isDimensionNull());
        assertTrue(v1.isDimensionNull());
        assertTrue(v2.isDimensionNull());
        assertFalse(v3.isDimensionNull());
    }

    public void testEquals() throws Exception {
        Vector v = new Vector(0, 1),
                v1 = new Vector(0, 0),
                v2 = new Vector(1, 0),
                v3 = new Vector(0, 0);
        assertTrue(v1.equals(v3));
        assertFalse(v.equals(v2));
        assertFalse(v.equals(v1));
    }

    public void testClone() throws Exception {
        Vector v = new Vector(0, 1);
        Vector v1 = v.clone();
        assertFalse(v1 == v);
        assertEquals(v1.getX(), v.getX());
        assertEquals(v1.getY(), v.getY());
    }
}
