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

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 12/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Representation of a solution for 2D cutting stock problem.
 *          It's a representation of a vector sized by the number of pattern.
 * @see Pattern  for information about structure of a Pattern.
 */
public class Solution {

    @NotNull
    private ArrayList<Pattern> patterns;

    public Solution(@NotNull ArrayList<Pattern> patterns) {
        this.patterns = patterns;
    }

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    @Override
    public Solution clone() {
        ArrayList<Pattern> clonedPatterns = new ArrayList<Pattern>();
        for(Pattern p : patterns) {
            clonedPatterns.add((Pattern) p.clone());
        }
        return new Solution(clonedPatterns);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Solution = {");
        for (Pattern p : patterns) {
            buffer.append(p.toString());
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.toString().length() - 1);
        buffer.append("}");
        return buffer.toString();
    }

}
