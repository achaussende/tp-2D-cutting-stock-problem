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

import com.polytech4A.cuttingstock.core.model.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Antoine CARON on 15/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class GuillotineSortBFFTest extends TestCase {

    private ArrayList<Comparator<Box>> boxComparators;

    private GuillotineSortBFF guillotineSortBFF;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        boxComparators = new ArrayList<>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
        guillotineSortBFF = new GuillotineSortBFF(boxComparators);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGeneratePlaceBoxForBin() throws Exception {
        Bin bin = new Bin(new Vector(4, 7), null, new Vector(0, 1));
        Box box = new Box(new Vector(8, 9), 1);
        Box box1 = new Box(new Vector(7, 4), 1);
        Box box2 = new Box(new Vector(3, 7), 1);
        assertEquals("generatePlaceBoxForBin returned null.", null, guillotineSortBFF.generatePlaceBoxForBin(bin, box));
        PlacedBox placedBox = guillotineSortBFF.generatePlaceBoxForBin(bin, box1);
        assertEquals("The placed box does not have the same size as the box from it has been created.", true, placedBox.getSize().equals(new Vector(7, 4)));
        assertEquals("The placed box was not rotated.", true, placedBox.isRotation());
        PlacedBox placedBox1 = guillotineSortBFF.generatePlaceBoxForBin(bin, box2);
        assertEquals("The second placed box does not have the same size as the box from it has been created.", true, placedBox1.getSize().equals(new Vector(3, 7)));
        assertEquals("The placed box was rotated.", false, placedBox1.isRotation());
    }

    public void testGenerateSubBins() {
        Bin bin = new Bin(new Vector(4, 7), null, new Vector(0, 1));
        Box box1 = new Box(new Vector(1, 2), 1);
        Box box2 = new Box(new Vector(3, 7), 1);
        Box box3 = new Box(new Vector(4, 7), 1);
        PlacedBox placedBox = guillotineSortBFF.generatePlaceBoxForBin(bin, box3);
        ArrayList<Bin> bins = new ArrayList<>();
        guillotineSortBFF.generateSubBins(bin, placedBox, bins);
        assertEquals("1st case: No horizontal sub bin test failed.", 0, bin.getHorizontalsubBin().size());
        assertEquals("1st case: No vertical sub bin test failed", 0, bin.getVerticalsubBin().size());
        placedBox = guillotineSortBFF.generatePlaceBoxForBin(bin, box2);
        guillotineSortBFF.generateSubBins(bin, placedBox, bins);
        assertEquals("2nd case: No horizontal sub bin test failed.", 0, bin.getHorizontalsubBin().size());
        assertEquals("2nd case: 1 vertical sub bin test failed", 1, bin.getVerticalsubBin().size());
        bin = new Bin(new Vector(4, 7), null, new Vector(0, 1));
        placedBox = guillotineSortBFF.generatePlaceBoxForBin(bin, box1);
        guillotineSortBFF.generateSubBins(bin, placedBox, bins);
        assertEquals("3rd case: 2 horizontal sub bins test failed", 2, bin.getHorizontalsubBin().size());
        assertEquals("3rd case: 2 vertical sub bins test failed", 2, bin.getVerticalsubBin().size());
    }

    public void testGeneratePlacedBox() throws Exception {

        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Vector(16, 4), 1));
        boxes.add(new Box(new Vector(3, 7), 1));


        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(new Bin(new Vector(1, 2), null, new Vector(1, 1)));
        bins.add(new Bin(new Vector(2, 3), null, new Vector(3, 3)));
        bins.add(new Bin(new Vector(4, 2), null, new Vector(0, 0)));
        bins.add(new Bin(new Vector(5, 9), null, new Vector(0, 7)));

        PlacedBox placedBox = guillotineSortBFF.generatePlacedBox(bins, boxes.get(0));
        assertNull("A placed box has been created. It must not be.", placedBox);

        placedBox = guillotineSortBFF.generatePlacedBox(bins, boxes.get(1));
        assertNotNull("Null returned for placedBox. Test fails.", placedBox);
        assertEquals("Placed box is bad positioned", new Vector(0, 7), placedBox.getPosition());
        assertFalse("Placed box has been rotated. It must not be in this case.", placedBox.isRotation());
    }

    public void testGeneratePlacedBoxes() throws Exception {
        ArrayList<Box> boxes = new ArrayList<>();
        //All fitted boxes
        boxes.add(new Box(new Vector(1, 2), 1));
        boxes.add(new Box(new Vector(5, 4), 1));
        boxes.add(new Box(new Vector(3, 7), 1));
        boxes.add(new Box(new Vector(2, 2), 1));
        Pattern pattern = new Pattern(new Vector(20, 10), boxes);

        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(new Bin(new Vector(20, 10), pattern, new Vector(0, 0)));

        ArrayList<PlacedBox> placedBoxes = guillotineSortBFF.generatePlacedBoxes(boxes, bins);
        assertNotNull("There should be boxes in returned list of boxes. 1st case: Bigger pattern.", placedBoxes);

        //Not all fitted boxes
        pattern = new Pattern(new Vector(3, 3), boxes);
        bins.removeAll(bins);
        bins.add(new Bin(new Vector(3, 3), pattern, new Vector(0, 0)));
        placedBoxes = guillotineSortBFF.generatePlacedBoxes(boxes, bins);
        assertNull("There should be no boxes in returned list of boxes. 2nd case : Not all fitted boxes", placedBoxes);

        //Just fitted pattern.
        boxes.removeAll(boxes);
        boxes.add(new Box(new Vector(3, 7), 1));
        boxes.add(new Box(new Vector(5, 4), 1));
        boxes.add(new Box(new Vector(2, 2), 1));
        boxes.add(new Box(new Vector(1, 2), 1));
        pattern = new Pattern(new Vector(7, 7), boxes);
        bins.removeAll(bins);
        bins.add(new Bin(new Vector(7, 7), pattern, new Vector(0, 0)));
        placedBoxes = guillotineSortBFF.generatePlacedBoxes(boxes, bins);
        assertNotNull("There should be boxes in returned list of boxes. 3rd case: adjusted pattern, boxes all fit", placedBoxes);

    }

    public void testGetPlacing() throws Exception {
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Vector(1, 2), 1));
        boxes.add(new Box(new Vector(5, 4), 1));
        boxes.add(new Box(new Vector(3, 7), 1));
        boxes.add(new Box(new Vector(2, 2), 1));

        ArrayList<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(new Vector(10, 20), boxes));
        patterns.add(new Pattern(new Vector(20, 10), boxes));
        Solution solution = new Solution(patterns);
        Solution retSolution = guillotineSortBFF.getPlacing(solution);
        assertNotNull(retSolution);
        assertNotNull(retSolution.getPatterns().get(0).getPlacedBoxes().get(0).getPosition());
    }

    public void testGeneratePattern() throws Exception {
        ArrayList<Box> boxes = new ArrayList<>();
        //All fitted boxes
        boxes.add(new Box(new Vector(1, 2), 1));
        boxes.add(new Box(new Vector(5, 4), 1));
        boxes.add(new Box(new Vector(3, 7), 1));
        boxes.add(new Box(new Vector(2, 2), 1));
        Pattern pattern = new Pattern(new Vector(20, 10), boxes);
        for (Comparator<Box> comparator : boxComparators) {
            Pattern retPattern = guillotineSortBFF.generatePattern(pattern, boxes, comparator);
            assertNotNull(retPattern);
        }
    }
}
