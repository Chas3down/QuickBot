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
package org.qkit;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import org.qkit.core.BotInternalConstants;
import org.qkit.core.bot.BotWindow;
import org.qkit.core.impl.accessors.Client;
import org.qkit.core.impl.accessors.GameApplet;
import org.qkit.core.net.NetUtils;
import org.qkit.core.updater.Injector;
import org.qkit.core.updater.JarConstruct;
import org.qkit.core.bot.Out;

import javax.swing.*;
import java.awt.*;


/**
 * A RSPS bot. For educational purposes only!
 *
 * @author trDna
 * @author Parnassian/Clisprail (double-buffering).
 *
 * @version 0.1
 */

public class Service{

    /**
     * Pointer to the Client accessor.
     */
    private static Client theClient;

    /**
     * Pointer to the GameApplet accessor.
     */
    private static GameApplet theApplet;

    /**
     * The bot window.
     */
    private static BotWindow botWindow;


    public static void main(String[]args){

        //Download the JAR.
        Out.ln("Downloading JAR..");
        NetUtils.downloadFile(BotInternalConstants.RUNESENS_CLIENT_URL, BotInternalConstants.JAR_PATH);

        //Start performing the injection operations.
        Out.ln("Injecting..");

        //Reference the JAR file.
        JarConstruct jc = new JarConstruct(BotInternalConstants.JAR_PATH);

        //Inject the contents.
        new Injector(jc);

        Out.ln("Starting client..");

        //Start the bot on an EDT.
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                //Set up the LAF (look and feel) of the window.
                botWindow.setDefaultLookAndFeelDecorated(true);

                try {
                    UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());

                    //Create the bot window.
                    botWindow = new BotWindow();

                    //Set the window to be visible.
                    botWindow.setVisible(true);

                    //Reference the Client accessor.
                    theClient = botWindow.getClient();

                    //Reference the GameApplet accessor.
                    theApplet = botWindow.getGameApplet();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Points to the Client interface which should have been injected to the client class.
     *
     * @return The Client accessor interface.
     *
     * @see Client
     * @see GameApplet
     * @see Injector
     *
     * @since 0.01
     */
    public static Client getClient(){
        return theClient;
    }

    /**
     * Points to the GameApplet interface which should have been injected to the client class.
     *
     * @return The Client accessor interface.
     *
     * @see GameApplet
     * @see Injector
     *
     * @since 0.01
     */
    public static GameApplet getApplet(){
        return theApplet;
    }

}