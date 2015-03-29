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
import org.junit.After;
import org.junit.Before;

/**
 * Created by Adrien CHAUSSENDE on 29/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class BoxTest extends TestCase {

    private Box box;

    @Before
    public void setUp() {
        box = new Box(new Vector(1,5), 2);
    }

    @After
    public void tearDown() {
        box = null;
    }

    public void testEquals() throws Exception {
        assertTrue(box.equals(new Box(new Vector(1, 5), 2)));
        assertFalse(box.equals(new Box(new Vector(0, 5), 2)));
        assertFalse(box.equals(new Box(new Vector(1, 0), 2)));
        assertTrue(box.equals(new Box(new Vector(1, 5), 20)));
    }

    public void testClone() throws Exception {
        Box b = box.clone();
        assertFalse(b == box);
        assertEquals(b.getSize(), box.getSize());
        assertEquals(b.getAmount(), box.getAmount());
    }
}
