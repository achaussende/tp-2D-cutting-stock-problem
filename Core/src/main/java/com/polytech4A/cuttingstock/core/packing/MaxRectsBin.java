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

import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Vector;

import java.util.ArrayList;

/**
 * Created by Adrien CHAUSSENDE on 09/05/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class MaxRectsBin extends Bin {

    public MaxRectsBin(Vector size, Pattern pattern, Vector origin) {
        super(size, pattern, origin);
    }

    @Override
    public void disableSubBinFromBin(ArrayList<Bin> bins) {
        bins.parallelStream().forEach(currentBin -> {
            if (currentBin.getHorizontalsubBin().contains(this)) {
                currentBin.getVerticalsubBin().forEach(b -> {
                    b.getSize().setY(b.getSize().getY() - getSize().getY());
                });
            }

            if (currentBin.getVerticalsubBin().contains(this)) {
                currentBin.getHorizontalsubBin().forEach(b -> {
                    b.getSize().setX(b.getSize().getX() - getSize().getX());
                });
            }
        });

    }
}