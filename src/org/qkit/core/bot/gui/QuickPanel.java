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

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.net.URL;

/**
 * @author trDna
 */
public class QuickPanel extends JPanel implements AppletStub{

    private Applet applet;

    private Dimension size = new Dimension(765, 503);

    public QuickPanel(Applet applet){
        this.applet = applet;
        setSize(getSize());
    }

    public void initPanel(){
        add(applet);
        applet.setPreferredSize(getSize());
        applet.init();
        applet.start();
    }

    @Override
    public Dimension getSize(){
        return size;
    }

    @Override
    public boolean isActive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public URL getDocumentBase() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public URL getCodeBase() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getParameter(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AppletContext getAppletContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void appletResize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
