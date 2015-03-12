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

package com.polytech4A.cuttingstock.core.resolution.util;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Context Objet for Serialization in XML file.
 */
public class Context {

    private int id;

    private int patternCost;

    private int sheetCost;

    @NotNull
    private ArrayList<Box> boxes;

    @NotNull
    private Pattern pattern;


    public Context() {
    }

    public Context(int id, int patternCost, int sheetCost, @NotNull ArrayList<Box> boxes, @NotNull Pattern pattern) {
        this.id = id;
        this.patternCost = patternCost;
        this.sheetCost = sheetCost;
        this.boxes = boxes;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(@NotNull Pattern pattern) {
        this.pattern = pattern;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatternCost() {
        return patternCost;
    }

    public void setPatternCost(int patternCost) {
        this.patternCost = patternCost;
    }

    public int getSheetCost() {
        return sheetCost;
    }

    public void setSheetCost(int sheetCost) {
        this.sheetCost = sheetCost;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(@NotNull ArrayList<Box> boxes) {
        this.boxes = boxes;
    }
}
