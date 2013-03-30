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
package org.qkit.core.bot.gui;

import org.qkit.core.BotInternalConstants;
import org.qkit.core.impl.accessors.Client;
import org.qkit.core.impl.accessors.GameApplet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author trDna
 */
public class QuickBot extends JFrame{

    private static Client theClient;
    private static GameApplet theApplet;

    private static Object clazz;

    public static void main(String[]args){
        new QuickBot().setVisible(true);
    }
    public QuickBot(){
        setSize(new Dimension(765, 503));
        setTitle("qBot - 0.01");
        QuickPanel qp = new QuickPanel(getGame());
        add(qp);
    }

    private Applet getGame(){
        try{
        JFrame.setDefaultLookAndFeelDecorated(true);

        setAlwaysOnTop(true);
        setVisible(true);

        ClassLoader cl = new URLClassLoader(new URL[]{new URL("file:" + BotInternalConstants.INJ_JAR_PATH)});

        clazz = cl.loadClass("client").newInstance();
        Applet gameApplet = (Applet) clazz;

//        GameApplet ga = (GameApplet) cl.loadClass("RSApplet").newInstance();
        //      realGraphics = ga.getRealGraphics();

        gameApplet.setPreferredSize(new Dimension(765, 503));
        gameApplet.setVisible(true);
        gameApplet.init();
        gameApplet.start();

        theClient = (Client) clazz;
        theApplet = (GameApplet) clazz;

            return gameApplet;
        }catch (Exception ex){
            throw new NullPointerException("Applet load failure.");
        }
    }



}
