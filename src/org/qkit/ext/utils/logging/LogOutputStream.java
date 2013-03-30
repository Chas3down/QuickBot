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
package org.qkit.ext.utils.logging;

import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author trDna
 */
public class LogOutputStream extends PrintStream {

    private ArrayList<String> list;
    private JList jList;

    private ScheduledExecutorService s = Executors.newScheduledThreadPool(1);

    public LogOutputStream(OutputStream out, ArrayList<String> list, JList jList) {
        super(out);
        this.list = list;
        this.jList = jList;
    }

    public void onCall(String s){
        list.add(String.valueOf(s));
    }

    public void println(Object o){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat day = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String s = ("[" + "Internal" + "]" + "["+ day.format(date) + "]" + "[" + dateFormat.format(date) + "] " + String.valueOf(o));
        onCall(s);
        super.println(s);
    }


}
