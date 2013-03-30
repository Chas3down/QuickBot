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
package org.qkit.core.event.producers;

import org.qkit.Service;
import org.qkit.core.impl.accessors.Client;

import java.awt.*;

/**
 * Used to paint settings on the applet immediately.
 * @author trDna
 */
public class OnDemandPainter {

    private static int offY = 25;

    public static boolean debugCamera = true, debugPlayer = true, debugMinimap = true, debugMapBase = true;

    private static Graphics localGraphics;

    protected static void paint(Graphics localGraphics){
        OnDemandPainter.localGraphics = localGraphics;
        drawSettings();
    }

    private static void drawSettings(){
        localGraphics.setColor(Color.GREEN);

        OnDemandPainter.offY = 20;

        if(getClient().isLoggedIn()){

            if(debugCamera){
                drawString("CameraX = " + getClient().getCameraX());
                drawString("CameraY = " + getClient().getCameraY());
                drawString("CameraZ = " + getClient().getCameraZ());
                drawString("Camera Curve X = " + getClient().getCameraCurveX());
                drawString("Camera Curve Y = " + getClient().getCameraCurveY());
                drawString("Camera Zoom = " + getClient().getCameraZoom());
            }
            if(debugPlayer){
                drawString("Destination [X,Y] = " + "[" + getClient().getDestX() + ", " + getClient().getDestY() + "]");
                drawString("Plane = " + getClient().getPlane());
            }

            if(debugMapBase){
                drawString("Map Base [X,Y] = " + "[" + getClient().getMapBaseX() + ", " + getClient().getMapBaseY() + "]");
            }

            if(debugMinimap){
                drawString("MinimapInt1 = " + getClient().getMinimapInt1());
                drawString("MinimapInt2 = " + getClient().getMinimapInt2());
                drawString("MinimapInt3 = " + getClient().getMinimapInt3());
            }

        }
    }

    public static void drawString(String s) {
        localGraphics.setColor(Color.GREEN);
        localGraphics.drawString(s, 16, OnDemandPainter.offY);

        OnDemandPainter.offY += 20;
    }

    private static Client getClient(){
        return Service.getClient();
    }

}
