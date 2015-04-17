/*
 * Project to resolve 2D cutting stock problem for Discreet Optimizations course at Polytech Lyon
 * Copyright (C) 2015.  CARON Antoine and CHAUSSENDE Adrien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.polytech4A.cuttingstock.core.method;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Vector;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import com.polytech4A.cuttingstock.core.resolution.util.context.ContextLoaderUtils;
import junit.framework.TestCase;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.util.FastMath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Adrien CHAUSSENDE on 22/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class LinearResolutionMetodTest extends TestCase {

    private Context context;

    private Solution solution;

    @Before
    public void setUp() {
        // Context initialization
        Vector patternSize = new Vector(40,60);
        Box box0 = new Box(new Vector(24, 30), 246);
        Box box1 = new Box(new Vector(13, 56), 562);
        Box box2 = new Box(new Vector(14, 22), 1000);
        Box box3 = new Box(new Vector(9, 23), 3498);
        ArrayList<Box> ctxBoxes = new ArrayList<>();
        ctxBoxes.add(box0);
        ctxBoxes.add(box1);
        ctxBoxes.add(box2);
        ctxBoxes.add(box3);
        context = new Context("testConext", 20, 1, ctxBoxes, patternSize);

        // Solution initialization
        //  List of boxes for each pattern
        //      Pattern 1
        ArrayList<Box> pattern1Boxes = new ArrayList<>();
        pattern1Boxes.add(new Box(new Vector(24, 30), 2));
        pattern1Boxes.add(new Box(new Vector(13, 56), 1));
        pattern1Boxes.add(new Box(new Vector(14, 22), 0));
        pattern1Boxes.add(new Box(new Vector(9, 23), 0));
        //      Pattern 2
        ArrayList<Box> pattern2Boxes = new ArrayList<>();
        pattern2Boxes.add(new Box(new Vector(24, 30), 0));
        pattern2Boxes.add(new Box(new Vector(13, 56), 1));
        pattern2Boxes.add(new Box(new Vector(14, 22), 0));
        pattern2Boxes.add(new Box(new Vector(9, 23), 7));
        //      Pattern 3
        ArrayList<Box> pattern3Boxes = new ArrayList<>();
        pattern3Boxes.add(new Box(new Vector(24, 30), 0));
        pattern3Boxes.add(new Box(new Vector(13, 56), 0));
        pattern3Boxes.add(new Box(new Vector(14, 22), 5));
        pattern3Boxes.add(new Box(new Vector(9, 23), 2));
        //  Add each pattern to list of patterns for the solution
        ArrayList<Pattern> solutionPatterns = new ArrayList<>();
        solutionPatterns.add(new Pattern(patternSize, pattern1Boxes));
        solutionPatterns.add(new Pattern(patternSize, pattern2Boxes));
        solutionPatterns.add(new Pattern(patternSize, pattern3Boxes));
        // Create solution
        solution = new Solution(solutionPatterns);
    }

    @After
    public void tearDown() {
        context = null;
    }

    @Test
    public void testUpdateFunction() throws NoSuchFieldException, IllegalAccessException {
        LinearResolutionMethod method = new LinearResolutionMethod(context);
        method.updateFunction(solution);
        // To avoid set attributes in public, we get the field function.
        Field field = method.getClass().getDeclaredField("function");
        field.setAccessible(true);
        LinearObjectiveFunction f = (LinearObjectiveFunction) field.get(method);
        // Test of the function
        //  Coefficients value = sheet cost
        for (double d : f.getCoefficients().getData()) {
            assertEquals((double) context.getSheetCost(), d);
        }
        // Number of coefficients has to equal the number of patterns of the solution.
        assertEquals(solution.getPatterns().size(), f.getCoefficients().getDimension());
    }

    @Test
    public void testUpdateConstraints() throws NoSuchFieldException, IllegalAccessException {
        LinearResolutionMethod method = new LinearResolutionMethod(context);
        method.updateConstraints(solution);
        // To avoid set attributes in public, we get the field function.
        Field field = method.getClass().getDeclaredField("constraints");
        field.setAccessible(true);
        ArrayList<LinearConstraint> constraints = (ArrayList<LinearConstraint>) field.get(method);
        // Test of value of each constraint's value.
        int i = 0;
        for (LinearConstraint c : constraints) {
            double[] coefficients = c.getCoefficients().getData();
            int j = 0;
            for(Pattern p : solution.getPatterns()) {
                Box b = p.getAmounts().get(i);
                assertEquals((double)p.getAmounts().get(i).getAmount(), coefficients[j]);
                j++;
            }
            i++;
            assertEquals(c.getRelationship(), Relationship.GEQ);
        }
    }

    @Test
    public void testMinimize() {
        LinearResolutionMethod method = new LinearResolutionMethod(context);
        Result result = method.minimize(solution);
        int[] printings = {123, 443, 200};
        for(int i = 0; i < printings.length; ++i) {
            assertEquals(printings[i], result.getPrintings()[i]);
        }
        assertEquals(826, (int) FastMath.ceil(result.getCost()));
    }
}
