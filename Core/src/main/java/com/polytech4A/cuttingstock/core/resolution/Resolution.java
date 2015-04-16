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

package com.polytech4A.cuttingstock.core.resolution;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.resolution.util.context.Context;
import com.polytech4A.cuttingstock.core.resolution.util.context.ContextLoaderUtils;
import com.polytech4A.cuttingstock.core.resolution.util.context.IllogicalContextException;
import com.polytech4A.cuttingstock.core.resolution.util.context.MalformedContextFileException;
import com.polytech4A.cuttingstock.core.save.Save;
import com.polytech4A.cuttingstock.core.save.ToImg;
import com.polytech4A.cuttingstock.core.solver.Solver;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Antoine on 12/03/2015.
 *
 * @author Antoine
 * @version 1.0
 *          <p>
 *          Representation of a resolution for the 2d cutting stock problem.
 */
public class Resolution {

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(Resolution.class);

    /**
     * Context of the resolution.
     */
    private Context context;

    /**
     * Path of the contextFile.
     */
    private String contextFilePath;

    /**
     * Solver for the problem.
     */
    private Solver solver;

    /**
     * Saving methods.
     */
    private List<Save> saveMethods;

    /**
     *
     */
    private ArrayList<Comparator<Box>> boxComparators;

    public Resolution(String contextFilePath) {
        this.contextFilePath = contextFilePath;
        saveMethods = new ArrayList<>();
        saveMethods.add(new ToImg());
        boxComparators = new ArrayList<>();
        boxComparators.add(Box.Comparators.AREA);
        boxComparators.add(Box.Comparators.X);
        boxComparators.add(Box.Comparators.Y);
    }

    /**
     * Load context function.
     *
     * @param path Path to the Context file.
     * @return Context instance.
     * @throws java.io.IOException                                                                    If the file opening throw exception.
     * @throws com.polytech4A.cuttingstock.core.resolution.util.context.MalformedContextFileException If the file don't have the good structure.
     * @throws IllogicalContextException                                                              if an image is bigger than pattern size defined in the file.
     */
    public static Context loadContext(String path) throws IOException, MalformedContextFileException, IllogicalContextException {
        return ContextLoaderUtils.loadContext(path);
    }

    /**
     * Generate the first Solution from a NotNull Context in attribut.
     *
     * @return Solution with pattern and Boxes.
     * @see com.polytech4A.cuttingstock.core.resolution.util.context.Context
     * @see com.polytech4A.cuttingstock.core.model.Pattern
     * @see com.polytech4A.cuttingstock.core.model.Box
     */
    public static Solution generateFirstSolution(Context context) {
        double pattern_area = context.getPatternSize().getArea();
        double boxes_totalArea = context.getBoxes().parallelStream().mapToDouble(b -> b.getSize().getArea()).sum();
        double nbPatterns = Math.ceil(boxes_totalArea / pattern_area);
        ArrayList<Pattern> patterns = new ArrayList<>();
        for (int i = 0; i < nbPatterns; i++) {
            ArrayList<Box> boxes = (ArrayList<Box>) context.getBoxes().clone();
            boxes.parallelStream().forEach(b -> b.setAmount(0));
            patterns.add(new Pattern(context.getPatternSize(), boxes));
        }
        return new Solution(patterns);
    }

    public void solve() {
        Solution startingSolution = getStartingSolution();
    }

    /**
     * Generate the startingSolution for solving.
     * @return real random packable solution.
     */
    private Solution getStartingSolution() {
        /** try {
            context = loadContext(contextFilePath);
            ArrayList<INeighbourUtils> generators = new ArrayList<>();
            generators.add(new IncrementNeighbour());
            generators.add(new MoveNeighbour());
            SimulatedAnnealing realSolutionGenerator = new SimulatedAnnealing(new GuillotineSortBFF(boxComparators), new LinearResolutionMethod(context), generators, 10000);
            Solution firstSolution = generateFirstSolution(context);
            return realSolutionGenerator.getRandomSolution(firstSolution, false);
        } catch (IOException e) {
            logger.error(e);
        } catch (MalformedContextFileException e) {
            logger.error(e);
        } catch (IllogicalContextException e) {
            logger.error(e);
         }**/
        return null;
    }
}
