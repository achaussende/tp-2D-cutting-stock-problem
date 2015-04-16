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
 *          <p>
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
        for (Pattern p : patterns) {
            clonedPatterns.add(p.clone());
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
        for (Pattern p : patterns) {
            if (p.getPlacedBoxes() != null && p.getPlacedBoxes().size() > 0) {
                buffer.append("\n");
                buffer.append("Placebox for Pattern nb:" + patterns.indexOf(p) + "\n");
                for (PlacedBox placedBox : p.getPlacedBoxes()) {
                    buffer.append(placedBox.toString());
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Remove Empty Patterns of the solution.
     */
    public void removeEmpty() {
        if (patterns.size() > 1) {
            patterns.removeIf(p -> p.isPatternEmpty());
        }
    }

    /**
     * Boolean Function to dertermine if this solution is Packable.
     *
     * @return true/false
     */
    public boolean isPackable() {
        int nbBoxes = patterns.parallelStream()
                .mapToInt(p -> (int) p.getBoxes().parallelStream().count()).sum();
        int nbPlacedBox = patterns.parallelStream().mapToInt(p -> p.getPlacedBoxes().size()).sum();
        return (nbBoxes == nbPlacedBox);
    }

    /**
     * Boolean Function to dertermine if this solution is valid.
     * If evry image as an amount à 1 minimum.
     *
     * @return true/false
     */
    public boolean isValid() {
        int nbImages = patterns.get(0).getAmounts().size();
        for (int i = 0; i < nbImages; i++) {
            boolean isexisting = false;
            for (Pattern p : patterns) {
                if (p.getAmounts().get(i).getAmount() > 0) {
                    isexisting = true;
                }
            }
            if (!isexisting) {
                return false;
            }
        }
        return true;
    }

    /**
     * add a blank pattern (pattern with the same boxes than pattern index 0 but with amounts at 0).
     */
    public void addBlankPattern() {
        if (!patterns.isEmpty()) {
            Pattern p = patterns.get(0).clone();
            p.getAmounts().parallelStream().forEach(b -> b.setAmount(0));
            patterns.add(p);
        }
    }
}
