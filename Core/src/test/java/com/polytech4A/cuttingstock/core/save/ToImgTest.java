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

package com.polytech4A.cuttingstock.core.save;

import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.PlacedBox;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Vector;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Antoine CARON on 26/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Test case for ToImg Save method.
 */
public class ToImgTest extends TestCase {

    public void testSave() throws Exception {
        ArrayList<PlacedBox> placedBoxes1 = new ArrayList<>();
        ArrayList<PlacedBox> placedBoxes2 = new ArrayList<>();
        ArrayList<PlacedBox> placedBoxes3 = new ArrayList<>();
        placedBoxes1.add(new PlacedBox(new Vector(6, 5), 1, new Vector(0, 0), false));
        placedBoxes1.add(new PlacedBox(new Vector(8, 4), 1, new Vector(6, 0), true));


        ArrayList<Pattern> patterns = new ArrayList<>();
        Pattern p1 = new Pattern(new Vector(10, 20), new ArrayList<>());
        p1.setPlacedBoxes(placedBoxes1);
        patterns.add(p1);
        Solution solution = new Solution(patterns);
        Save save = new ToImg();
        save.save("Test1", solution);
    }
}
