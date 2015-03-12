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

package com.polytech4A.cuttingstock.core;

import org.apache.log4j.*;

import java.io.IOException;

/**
 * Created by Antoine on 12/03/2015.
 *
 * @author Antoine
 * @version 1.0
 *          <p/>
 *          Main class of Project  to resolve 2D cutting stock problem for Discreet Optimizations course at Polytech Lyon.
 */
public class Main {

    /**
     * Logger.
     */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Main.class);

    /**
     * Main function.
     *
     * @param args arg0 = port, arg1 = nbConnection, arg2 = boolDeleteMsg
     */
    public static void main(String[] args) {
        defineLogger();
    }

    /**
     * Define the Logger Appender for LOG4j
     */
    private static void defineLogger() {
        HTMLLayout layout = new HTMLLayout();
        DailyRollingFileAppender appender = null;
        try {
            appender = new DailyRollingFileAppender(layout, "./Logs/log.html", "'.'yyyy-MM-dd");
            appender.activateOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsoleAppender ca = new ConsoleAppender();
        ca.setLayout(new SimpleLayout());
        ca.activateOptions();
        logger.addAppender(appender);
        logger.addAppender(ca);
        logger.setLevel(Level.DEBUG);
    }
}
