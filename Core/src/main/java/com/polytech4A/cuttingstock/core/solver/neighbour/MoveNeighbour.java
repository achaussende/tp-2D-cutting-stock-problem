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

import com.polytech4A.cuttingstock.core.model.Solution;

/**
 * Created by Adrien CHAUSSENDE on 28/03/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Generator of neighbours using a box move from a pattern to an other.
 */
public class MoveNeighbour implements INeighbourUtils {
    /**
     * {@inheritDoc}
     */
    @Override
    public Solution getNeighbourhood(final Solution s) {
        Solution solution = s.clone();
        /**ArrayList<Solution> solutions = new ArrayList<Solution>();
         ArrayList<Pattern> modifications = getModifications(solution).getPatterns();
         final int[] count = {0, 0, 0};
         for (count[0] = 0; count[0] < modifications.size(); ++count[0]) {
         ArrayList<Box> modificationAmounts = modifications.get(count[0]).getAmounts();
         ArrayList<Box> patternAmounts = solution.getPatterns().get(count[0]).getAmounts();
         for (count[1] = 0; count[1] < modificationAmounts.size(); ++count[1]) {
         Box mBox = modificationAmounts.get(count[1]);
         Box pBox = patternAmounts.get(count[1]);
         if (mBox.getAmount() == 1 && mBox.equals(pBox)) {
         pBox.setAmount(pBox.getAmount() - 1);
         //Do the thing on other patterns.
         solution.getPatterns().stream().filter(p -> count[2]++ != count[0]).forEach(p -> {
         p.getAmounts().stream().forEach(b -> {
         if (b.equals(pBox)) {
         b.setAmount(b.getAmount() + 1);
         solutions.add(solution.clone());
         b.setAmount(b.getAmount() - 1);
         }
         });
         });
         pBox.setAmount(pBox.getAmount() + 1);
         count[2] = 0;
         }
         }

         }**/
        return s;
    }

}
