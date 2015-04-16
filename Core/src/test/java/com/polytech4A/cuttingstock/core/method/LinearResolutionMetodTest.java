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
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Vector(4, 2), 200));
        boxes.add(new Box(new Vector(4, 5), 120));
        context = new Context("testContext", 20, 1, boxes, new Vector(4, 9));
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<Box> solutionBoxes = new ArrayList<Box>();
        solutionBoxes.add(new Box(new Vector(4, 2), 2));
        solutionBoxes.add(new Box(new Vector(4,5), 1));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        patterns.add(new Pattern(new Vector(4, 9), solutionBoxes));
        solution = new Solution(patterns);
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
}
