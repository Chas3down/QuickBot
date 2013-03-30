package org.qkit.core.impl.accessors;
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
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;



public abstract class SuperApplet extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, WindowListener, MouseWheelListener
{
    private static final long serialVersionUID = 1L;

    public static SuperApplet instance = null;

    public long lastPress = 0;
    public long lastRelease = 0;
    public int mouseX = 0;
    public int mouseY = 0;

    private boolean runExecuted = false;



    public SuperApplet()
    {
        instance = this;
    }

    /*
    protected final void configLoaderHook(int percent, String message) {
        if (message.equalsIgnoreCase("Preparing game engine") || percent >= 100) {
            Items.loadDefinitions();
            GameObjects.loadDefinitions();
        }
    }


    protected final void messageListenerHook(int type, String name, String msg) {
        MessageEvent evt = new MessageEvent(type, name, msg);
        for (int i = 0; i < Constants.messages.size(); i++)
            Constants.messages.get(i).onMessageReceived(evt);
    }
      */
    @Override
    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public Graphics getRealGraphics() {
        return super.getGraphics();
    }

    /*
    @Override
    public void keyPressed(KeyEvent e) {
        for (int i = 0; i < Constants.keyListeners.size(); i++) {
            Constants.keyListeners.get(i).keyPressed(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_KEYBOARD_ENABLED)
            _keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (int i = 0; i < Constants.keyListeners.size(); i++) {
            Constants.keyListeners.get(i).keyReleased(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_KEYBOARD_ENABLED && !e.isConsumed())
            _keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (int i = 0; i < Constants.keyListeners.size(); i++) {
            Constants.keyListeners.get(i).keyTyped(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_KEYBOARD_ENABLED && !e.isConsumed())
            _keyTyped(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < Constants.mouseListeners.size(); i++) {
            Constants.mouseListeners.get(i).mouseClicked(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mouseClicked(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (int i = 0; i < Constants.mouseMotionListeners.size(); i++) {
            Constants.mouseMotionListeners.get(i).mouseDragged(e);
            if (e.isConsumed())
                return;
        }
        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mouseDragged(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < Constants.mouseListeners.size(); i++) {
            Constants.mouseListeners.get(i).mouseEntered(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mouseEntered(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < Constants.mouseListeners.size(); i++) {
            Constants.mouseListeners.get(i).mouseExited(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mouseExited(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < Constants.mouseMotionListeners.size(); i++) {
            Constants.mouseMotionListeners.get(i).mouseMoved(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mouseMoved(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < Constants.mouseListeners.size(); i++) {
            Constants.mouseListeners.get(i).mousePressed(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            Mouse.mouse_x = e.getX();
            Mouse.mouse_y = e.getY();
            _mousePressed(e);
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < Constants.mouseListeners.size(); i++) {
            Constants.mouseListeners.get(i).mouseReleased(e);
            if (e.isConsumed())
                return;
        }

        if (Constants.INPUT_MOUSE_ENABLED) {
            _mouseReleased(e);
            Mouse.lastRelease = System.currentTimeMillis();
        }
        mouseX = e.getX();
        mouseY = e.getY();
        lastRelease = System.currentTimeMillis();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (Constants.INPUT_MOUSE_ENABLED) {
            _mouseWheelMoved(e);
        }
    }

    public void run() {
        runExecuted = true;
        _run();
    }

    public void sendMouseEvent(MouseEvent evt) {
        Mouse.mouse_x = evt.getX();
        Mouse.mouse_y = evt.getY();
        switch (evt.getID())
        {
            case MouseEvent.MOUSE_CLICKED:
                _mouseClicked(evt);
                break;
            case MouseEvent.MOUSE_PRESSED:
                _mousePressed(evt);
                break;
            case MouseEvent.MOUSE_RELEASED:
                _mouseReleased(evt);
                lastRelease = System.currentTimeMillis();
                break;
            case MouseEvent.MOUSE_MOVED:
                _mouseMoved(evt);
                break;
        }
    }

    public void sendKeyEvent(KeyEvent e) {
        switch (e.getID())
        {
            case KeyEvent.KEY_PRESSED:
                _keyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                _keyReleased(e);
                break;
            case KeyEvent.KEY_TYPED:
                _keyTyped(e);
                break;
        }

    }
    */
}
