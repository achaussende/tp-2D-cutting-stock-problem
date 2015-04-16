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

import java.util.Random;

/**
 * Created by Adrien CHAUSSENDE on 09/04/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p>
 *          Generator of neighbours using box addition to a pattern. This one adds an empty pattern to generate more neighbours.
 */
public class DynPatternIncrementNeighbour extends IncrementNeighbour {
    @Override
    public Solution getNeighbourhood(final Solution s) {
        Random random = new Random(System.currentTimeMillis());
        int nbPattern = s.getPatterns().size();
        int choosenPattern = random.nextInt(nbPattern + 1);
        if (choosenPattern == nbPattern) {
            Solution retSolution = s.clone();
            Pattern p = retSolution.addBlankPattern();
            Box rdBox = p.getAmounts().get(random.nextInt(p.getAmounts().size()));
            rdBox.setAmount(rdBox.getAmount() + 1);
            return retSolution;
        } else {
            return super.getNeighbourhood(s);
        }
    }
}
