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

package com.polytech4A.cuttingstock.core.solver.neighbour;

import com.polytech4A.cuttingstock.core.model.Box;
import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Adrien CHAUSSENDE on 28/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Generator of neighbours using box addition to a pattern.
 */
public class IncrementNeighbour implements INeighbourUtils {

    private static final Logger logger = Logger.getLogger(IncrementNeighbour.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Solution> getNeighbourhood(final Solution s) {
        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.addAll(this.getAdditiveNeighbourhood(s));
        solutions.addAll(this.getRemoveNeighbourhood(s));
        return solutions;
    }

    @Override
    public Solution getModifications(final Solution solution) {
        Solution cpSolution = null;
        cpSolution = solution.clone();
        cpSolution.getPatterns().parallelStream().forEach(p -> {
            p.getAmounts().parallelStream().forEach(b -> {
                if (b.getAmount() > 0) {
                    b.setAmount(1);
                }
            });
        });
        return cpSolution;
    }

    /**
     * Generation of +1 solutions
     *
     * @param solution solution to analyse
     * @return Arraylist of solution containing combinations of +1 solution.
     */
    private ArrayList<Solution> getAdditiveNeighbourhood(final Solution solution) {
        Solution cpSolution = solution.clone();
        ArrayList<Solution> retSolution = new ArrayList<>();
        for (Pattern p : cpSolution.getPatterns()) {
            for (Box b : p.getAmounts()) {
                b.setAmount(b.getAmount() + 1);
                retSolution.add(cpSolution.clone());
                b.setAmount(b.getAmount() - 1);
            }
        }
        return retSolution;
    }

    /**
     * Get Array of solution with -1 decrementation where it is possible.
     *
     * @param solution solution of reference.
     * @return
     */
    private ArrayList<Solution> getRemoveNeighbourhood(final Solution solution) {
        Solution cpSolution = solution.clone();
        Solution modifSolution = this.getModifications(solution);
        ArrayList<Solution> retSolution = new ArrayList<>();
        int patternIndex = 0;
        int boxIndex = 0;
        for (Pattern p : cpSolution.getPatterns()) {
            for (Box b : p.getAmounts()) {
                if (modifSolution.getPatterns().get(patternIndex).getAmounts().get(boxIndex).getAmount() == 1) {
                    b.setAmount(b.getAmount() - 1);
                    retSolution.add(cpSolution.clone());
                    b.setAmount(b.getAmount() + 1);
                }
                boxIndex++;
            }
            patternIndex++;
            boxIndex = 0;
        }
        return retSolution;
    }
}
