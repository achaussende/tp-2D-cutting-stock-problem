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

import com.sun.istack.internal.NotNull;

import java.util.Comparator;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          A Box is aa representation of a image to pack in a Pattern.
 * @see com.polytech4A.cuttingstock.core.model.Pattern
 */
public class Box implements Comparable<Box> {

    @NotNull
    private Vector size;

    private int amount;

    public Box(Vector size, int amount) {
        this.size = size;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    @Override
    public int compareTo(Box o) {
        return Comparators.AREA.compare(this, o);
    }

    public static class Comparators {
        public static Comparator<Box> X = new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (int) (o1.getSize().getX() - o2.getSize().getX());
            }
        };
        public static Comparator<Box> Y = new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (int) (o1.getSize().getY() - o2.getSize().getY());
            }
        };

        public static Comparator<Box> AREA = new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (int) (o1.getSize().getArea() - o2.getSize().getArea());
            }
        };

        public static Comparator<Box> AMOUNT = new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (o1.getAmount() - o2.getAmount());
            }
        };
    }

}
