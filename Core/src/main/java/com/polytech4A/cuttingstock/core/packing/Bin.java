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

package com.polytech4A.cuttingstock.core.packing;

import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 13/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class Bin {
    /**
     * Size of the Bin.
     */
    private Vector size;

    /**
     * Pattern reference for the Bin.
     *
     * @see com.polytech4A.cuttingstock.core.model.Pattern
     */
    private Pattern pattern;

    /**
     * Position of the left top corner of the bin.
     */
    private Vector origin;

    /**
     * Boolean to express if the Bin is still existing.
     */
    private boolean active;

    /**
     * SubBins generating after the adding a box on the bin. with vertical cut.
     *
     * @see com.polytech4A.cuttingstock.core.model.Box
     */
    private ArrayList<Bin> verticalsubBin;

    /**
     * SubBins generating after the adding a box on the bin. with horizontal cut.
     *
     * @see com.polytech4A.cuttingstock.core.model.Box
     */
    private ArrayList<Bin> horizontalsubBin;


    public Bin(Vector size, Pattern pattern, Vector origin) {
        this.size = size;
        this.pattern = pattern;
        this.origin = origin;
        this.active = true;
        this.verticalsubBin = new ArrayList<Bin>();
        this.horizontalsubBin = new ArrayList<Bin>();
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Bin> getVerticalsubBin() {
        return verticalsubBin;
    }

    public void setVerticalsubBin(ArrayList<Bin> verticalsubBin) {
        this.verticalsubBin = verticalsubBin;
    }

    public ArrayList<Bin> getHorizontalsubBin() {
        return horizontalsubBin;
    }

    public void setHorizontalsubBin(ArrayList<Bin> horizontalsubBin) {
        this.horizontalsubBin = horizontalsubBin;
    }

    public void disableSubBinFromBin(@NotNull ArrayList<Bin> bins) {
        bins.parallelStream().forEach(currentBin -> {
            if (currentBin.horizontalsubBin.contains(this)) {
                currentBin.verticalsubBin.forEach(vertBin -> {
                    vertBin.setActive(false);
                });
            }
            if (currentBin.verticalsubBin.contains(this)) {
                currentBin.horizontalsubBin.forEach(vertBin -> {
                    vertBin.setActive(false);
                });
            }
        });
    }
}
