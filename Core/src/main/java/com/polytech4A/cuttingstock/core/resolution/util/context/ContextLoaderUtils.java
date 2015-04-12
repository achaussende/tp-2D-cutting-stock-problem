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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Antoine CARON on 20/03/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p>
 *          Loader of Context files.
 */
public class ContextLoaderUtils {

    /**
     * Load the content of the context file.
     *
     * @param path path of the context File.
     * @return Context loaded.
     * @throws IOException                   if file not found.
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     * @throws IllogicalContextException     if an image is bigger than pattern max size.
     */
    public static Context loadContext(String path) throws IOException, MalformedContextFileException, IllogicalContextException {
        File file = new File(path);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        ArrayList<Box> boxes = new ArrayList<>();
        try {
            Double x = loadLine(it, "^LX=[0-9]{1,13}(\\.[0-9]*)?$");
            Double y = loadLine(it, "LY=[0-9]{1,13}(\\.[0-9]*)?");
            int cost = loadLine(it, "m=[0-9]{1,13}(\\.[0-9]*)?").intValue();
            while (it.hasNext()) {
                boxes.add(loadBox(it.nextLine()));
            }
            LineIterator.closeQuietly(it);
            double max = Math.max(x, y);
            if (boxes.parallelStream().anyMatch(b -> b.getSize().getX() > max) ||
                    boxes.parallelStream().anyMatch(b -> b.getSize().getY() > max)) {
                throw new IllogicalContextException("There is an image which is bigger than the pattern.");
            }
            return new Context(file.getName(), 20, 1, boxes, new Vector(x, y));
        } catch (MalformedContextFileException mctx) {
            throw mctx;
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * Line Parser of global first infomrations.
     *
     * @param iterator iterator of the openfile.
     * @param regex    regex of the line.
     * @return value loaded.
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     */
    private static Double loadLine(LineIterator iterator, String regex) throws MalformedContextFileException {
        MalformedContextFileException mctx = new MalformedContextFileException();
        if (iterator.hasNext()) {
            String line = iterator.nextLine();
            if (line.matches(regex)) {
                return Double.parseDouble(line.split("=")[1]);
            } else {
                throw mctx;
            }
        } else {
            throw mctx;
        }
    }

    /**
     * Load a box form the file.
     *
     * @param line line in the file.
     * @return Box loaded of the line
     * @throws MalformedContextFileException if the Context file don't have the right structure.
     */
    private static Box loadBox(String line) throws MalformedContextFileException {
        MalformedContextFileException mctx = new MalformedContextFileException();
        if (line.matches("[0-9]{1,13}(\\.[0-9]*)?\\t[0-9]{1,13}(\\.[0-9]*)?\\t\\d{1,5}")) {
            String[] array = line.split("\\t");
            return new Box(new Vector(Double.parseDouble(array[0]), Double.parseDouble(array[1])), Integer.parseInt(array[2]));
        } else throw mctx;
    }

}
