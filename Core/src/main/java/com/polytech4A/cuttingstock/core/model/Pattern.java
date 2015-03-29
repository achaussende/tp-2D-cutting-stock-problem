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
 *          Representation of a Pattern  for 2D cutting Stock problem.
 *          A pattern is a vector sized by the number of different Image to pack in the Pattern.
 */
public class Pattern {

    /**
     * Size of the Pattern.
     */
    private Vector size;

    /**
     * List of amount of each Box in the Pattern. This table is size by the total number of different boxes.
     */
    @NotNull
    private ArrayList<Box> boxes;

    private ArrayList<PlacedBox> placedBoxes;

    public Pattern(@NotNull Vector size, @NotNull ArrayList<Box> boxes) {
        this.size = size;
        this.boxes = boxes;
    }

    public Pattern(@NotNull Vector size, @NotNull ArrayList<Box> boxes, @NotNull ArrayList<PlacedBox> placedBoxes) {
        this.size = size;
        this.boxes = boxes;
        this.placedBoxes = placedBoxes;
    }

    /**
     * Clone the pattern in parameters.
     *
     * @param p pattern to be cloned.
     */
    public Pattern(Pattern p) {
        try {
            Pattern pattern = (Pattern) p.clone();
            this.size = p.getSize();
            this.boxes = p.getAmounts();
            this.placedBoxes = p.getPlacedBoxes();
        } catch (CloneNotSupportedException e) {
            //TODO : handle in a logger ?
            e.printStackTrace();
        }
    }

    public ArrayList<PlacedBox> getPlacedBoxes() {
        return placedBoxes;
    }

    public void setPlacedBoxes(ArrayList<PlacedBox> placedBoxes) {
        this.placedBoxes = placedBoxes;
    }

    public Vector getSize() {
        return size;
    }

    public ArrayList<Box> getAmounts() {
        return boxes;
    }

    public ArrayList<Box> getBoxes() {
        ArrayList<Box> result = new ArrayList<>();
        boxes.parallelStream().forEach(b -> {
            for (int i = 0; i < b.getAmount(); i++) {
                result.add(new Box(b.getSize(), 1));
            }
        });
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ArrayList<Box> clonedBoxes = new ArrayList<Box>();
        for (Box b : boxes) {
            clonedBoxes.add((Box) b.clone());
        }
        ArrayList<PlacedBox> clonedPlacedBoxes = new ArrayList<PlacedBox>();
        for (PlacedBox placedBox : placedBoxes) {
            clonedBoxes.add((PlacedBox) placedBox.clone());
        }
        return new Pattern((Vector) size.clone(), clonedBoxes, clonedPlacedBoxes);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        for(Box b : getAmounts()) {
            buffer.append(b.getAmount());
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.toString().length() - 1);
        buffer.append(")");
        return buffer.toString();
    }
}
