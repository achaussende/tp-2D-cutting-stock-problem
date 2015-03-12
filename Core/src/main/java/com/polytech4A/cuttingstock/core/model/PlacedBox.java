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

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Representation of a Box placed on a Pattern.
 */
public class PlacedBox extends Box {

    /**
     * Position in a Pattern.
     */
    @NotNull
    private Vector position;

    /**
     * Rotation, if true the place is turned.
     */
    private boolean rotation;

    public PlacedBox(@NotNull Vector size, @NotNull Vector position, @NotNull boolean rotation) {
        super(size);
        this.position = position;
        this.rotation = rotation;
    }

    public PlacedBox(@NotNull Box box, @NotNull Vector position, @NotNull boolean rotation) {
        super(box.getSize());
        this.position = position;
        this.rotation = rotation;
    }

    @NotNull
    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(@NotNull boolean rotation) {
        this.rotation = rotation;
    }
}
