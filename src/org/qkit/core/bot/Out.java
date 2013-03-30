/*
 This file is a part of QuickBot.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.qkit.core.bot;

import org.qkit.ext.utils.logging.LogOutputStream;

/**
 * @author trDna
 */
public class Out {

    public static void err(Object o){
        if (BotWindow.values != null && BotWindow.logTextArea != null){
            System.setErr(new LogOutputStream(System.err, BotWindow.values, BotWindow.logTextArea));
            System.out.println(o);
        } else
            System.out.println(o);
    }

    public static void ln(Object o){
        if (BotWindow.values != null && BotWindow.logTextArea != null){
            System.setOut(new LogOutputStream(System.out, BotWindow.values, BotWindow.logTextArea));
            System.out.println(o);
        } else
            System.out.println(o);
    }

}
