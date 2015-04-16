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

import com.polytech4A.cuttingstock.core.model.Pattern;
import com.polytech4A.cuttingstock.core.model.Solution;
import com.polytech4A.cuttingstock.core.model.Box;

import java.util.ArrayList;
import java.util.Random;

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
    public Solution getRandomNeighbour(final Solution s) {
        Solution solution = s.clone();
        ArrayList<Pattern> patterns = solution.getPatterns();
        Random random = new Random(System.currentTimeMillis());
        int rdPatternIndex = random.nextInt(patterns.size());
        int rdBoxIndex;
        Pattern rdPattern = patterns.get(rdPatternIndex);
        Box rdBox;
        do {
            rdBoxIndex = random.nextInt(rdPattern.getAmounts().size());
            rdBox = rdPattern.getAmounts().get(rdBoxIndex);
        } while (rdBox.getAmount() == 0);
        int rdPatternNextIndex;
        do {
            rdPatternNextIndex = random.nextInt(patterns.size());
        } while (rdPatternIndex == rdPatternNextIndex);
        rdBox.setAmount(rdBox.getAmount() - 1);
        patterns.get(rdPatternNextIndex).getAmounts().get(rdBoxIndex).setAmount(patterns.get(rdPatternNextIndex).getAmounts().get(rdBoxIndex).getAmount() + 1);
        return solution;
    }

}
