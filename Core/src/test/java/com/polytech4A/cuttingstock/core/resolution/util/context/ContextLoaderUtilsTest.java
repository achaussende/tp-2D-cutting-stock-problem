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

import com.polytech4A.cuttingstock.core.model.Vector;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Antoine CARON on 21/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 */
public class ContextLoaderUtilsTest {

    @Test(expected = IOException.class)
    public void testLoadContextInvalidFile() throws Exception {
        File file = FileUtils.getFile("src", "test", "java", "resources", "test.txt");
        Context context = ContextLoaderUtils.loadContext(file.getPath());
    }

    @Test
    public void testLoadContextValidFile() throws Exception {
        File file = FileUtils.getFile("src", "test", "java", "resources", "ContextTest.txt");
        Context context = ContextLoaderUtils.loadContext(file.getPath());
        TestCase.assertEquals(179, context.getBoxes().get(0).getAmount());
        TestCase.assertEquals(20, context.getBoxes().size());
        TestCase.assertEquals(20, context.getPatternCost());
        TestCase.assertEquals(1, context.getSheetCost());
        TestCase.assertEquals(new Vector(1400, 700), context.getPatternSize());
    }

    @Test(expected = IllogicalContextException.class)
    public void testFail1() throws Exception {
        File file = FileUtils.getFile("src", "test", "java", "resources", "ContextTestFail1.txt");
        Context context = ContextLoaderUtils.loadContext(file.getPath());
    }

    @Test(expected = IllogicalContextException.class)
    public void testFail2() throws Exception {
        File file = FileUtils.getFile("src", "test", "java", "resources", "ContextTestFail2.txt");
        Context context = ContextLoaderUtils.loadContext(file.getPath());
    }
}
