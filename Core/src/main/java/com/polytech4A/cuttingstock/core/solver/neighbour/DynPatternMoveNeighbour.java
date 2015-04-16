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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrien CHAUSSENDE on 08/04/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Generator of neighbours using a box move from a pattern to an other. This one adds an empty pattern to generate more neighbours.
 */
public class DynPatternMoveNeighbour extends MoveNeighbour {
    @Override
    public Solution getRandomNeighbour(final Solution s) {
        Random random = new Random(System.currentTimeMillis());
        int targetPatternIndex = random.nextInt(s.getPatterns().size() + 1);
        if (targetPatternIndex != s.getPatterns().size()) {
            return super.getRandomNeighbour(s);
        } else {
            Solution solution = s.clone();
            ArrayList<Pattern> patterns = solution.getPatterns();
            int originPatternIndex = random.nextInt(patterns.size());
            solution.addBlankPattern();
            Pattern originPattern = patterns.get(originPatternIndex);
            int originBoxIndex;
            Box originBox;
            do {
                originBoxIndex = random.nextInt(originPattern.getAmounts().size());
                originBox = originPattern.getAmounts().get(originBoxIndex);
            } while (originBox.getAmount() == 0);
            originBox.setAmount(originBox.getAmount() - 1);
            patterns.get(targetPatternIndex).getAmounts().get(originBoxIndex)
                    .setAmount(patterns.get(targetPatternIndex).getAmounts().get(originBoxIndex).getAmount() + 1);
            return solution;
        }
    }
}
