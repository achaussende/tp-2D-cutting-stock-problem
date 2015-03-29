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

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Representation of a classic mathematical 2D vector for Position of Size.
 */
public class Vector {

    /**
     * Horizontal value.
     */
    private double x;

    /**
     * Vertical value.
     */
    private double y;

    /**
     * Create a instance of a 2D vector.
     *
     * @param x horizontal value.
     * @param y vertical value.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compute rectangular area given by the horizontal and vertical values.
     *
     * @return Rectangular area
     */
    public double getArea() {
        return x * y;
    }

    /**
     * Compare horizontal and vertical values between current object and an other vector.
     *
     * @param v Vector to compare size.
     * @return True if horizontal and vertical values are smaller or equals to horizontal and vertical values of Vector v.
     */
    public boolean isSmallerThan(Vector v) {
        return getX() <= v.getX() && getY() <= v.getY();
    }

    /**
     * Return the Vector which has x as y and vice versa.
     *
     * @return Vector with x equals current Vector's y and y equals current Vector's x.
     */
    public Vector getInvertedVector() {
        return new Vector(getY(), getX());
    }

    /**
     * Check if one of dimensions equals 0.
     * @return True if one of x or y equals 0.
     */
    public boolean isDimensionNull() {
        return (getX() == 0 || getY() == 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            return obj != null && getX() == ((Vector) obj).getX() && getY() == ((Vector) obj).getY();
        }
        return false;
    }

    @Override
    protected Vector clone() throws CloneNotSupportedException {
        return new Vector(getX(), getY());
    }
}
