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

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A replica of the ImageProducer in the client.
 *
 * @author Parnassian/Clisprail
 * @author Dane
 */
public abstract class ImageProducer implements ImageObserver {

    /**
     * Retrieves the image/snapshot of the game.
     *
     * @return The snapshot of the game.
     */
    public abstract Image getImage();

}