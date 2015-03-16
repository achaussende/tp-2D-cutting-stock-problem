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

package com.polytech4A.cuttingstock.core.packing;

import com.polytech4A.cuttingstock.core.model.Vector;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 16/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class BinTest extends TestCase {

    private Bin bin;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bin = new Bin(new Vector(10, 20), null, new Vector(1, 0));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        bin = null;
    }

    public void testDisableSubBinFromBin() throws Exception {
        ArrayList<Bin> bins = new ArrayList<Bin>();
        bin.disableSubBinFromBin(bins);
        bins.add(bin);
        bins.add(new Bin(new Vector(5, 4), null, new Vector(3, 7)));
        bins.add(new Bin(new Vector(8, 8), null, new Vector(0, 0)));
        bins.add(new Bin(new Vector(4, 2), null, new Vector(3, 4)));
        bins.add(new Bin(new Vector(2, 5), null, new Vector(1, 7)));
        bin.getHorizontalsubBin().add(bins.get(1));
        bin.getHorizontalsubBin().add(bins.get(2));
        bin.getVerticalsubBin().add(bins.get(3));
        bin.getVerticalsubBin().add(bins.get(4));
        bins.get(1).disableSubBinFromBin(bins);
        assertTrue(bin.isActive());
        assertFalse(bins.get(3).isActive());
        assertFalse(bins.get(4).isActive());
        assertTrue(bins.get(1).isActive());
        assertTrue(bins.get(2).isActive());
    }
}
