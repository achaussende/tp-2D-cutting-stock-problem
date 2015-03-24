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

package com.polytech4A.cuttingstock.core.resolution.util.context;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Context Objet for Serialization in XML file.
 */
public class Context {

    /**
     * Id of the context
     */
    private String id;

    /**
     * Cost of a Pattern in €.
     */
    private int patternCost;

    /**
     * Cost of a sheet in €.
     */
    private int sheetCost;

    /**
     * Size of a Pattern.
     */
    private Vector patternSize;

    /**
     * Boxes with size and amount to print, not the amount per pattern.
     */
    @NotNull
    private ArrayList<Box> boxes;

    public Context(String id, int patternCost, int sheetCost, @NotNull ArrayList<Box> boxes,Vector patternSize) {
        this.id = id;
        this.patternCost = patternCost;
        this.sheetCost = sheetCost;
        this.boxes = boxes;
        this.patternSize=patternSize;
    }

    public String getId() {
        return id;
    }

    public int getPatternCost() {
        return patternCost;
    }

    public int getSheetCost() {
        return sheetCost;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public Vector getPatternSize() {
        return patternSize;
    }
}
