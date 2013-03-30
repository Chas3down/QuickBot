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
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import org.qkit.Service;

import org.qkit.core.BotInternalConstants;

import javax.swing.*;

/**
 * Used to double buffer over the pixels of the game.
 *
 * @author Parnassian/Clisprail
 * @author Dane
 * @author trDna
 *
 * @since 0.1
 */
public abstract class Renderer extends ImageProducer {

    /**
     * The dimensions of the buffered image.
     */
    private static Dimension bDim = BotInternalConstants.BUFFER_DIMENSION;

    /**
     * The dimensions of the game.
     */
    private static Dimension gDim = BotInternalConstants.GAME_DIMENSION;

    /**
     * Represents a snapshot of the game's pixels.
     */
    public static final ImageGraphic game = new ImageGraphic(gDim.width, gDim.height,
            BufferedImage.TYPE_INT_RGB);

    /**
     * Represents a snapshot of the paint (to be drawn onto the screen).
     */
    public static final ImageGraphic paint = new ImageGraphic(bDim.width, bDim.height,
            BufferedImage.TYPE_INT_ARGB);

    /**
     * An actual image of the screen.
     */
    public static final BufferedImage screen = new BufferedImage(bDim.width, bDim.height,
            BufferedImage.TYPE_INT_RGB);

    /**
     * Currently has no use.
     */
    public static boolean lessCpu = false;

    /**
     * Paint toggle.
     */
    public static boolean disableDrawing = false;

    /**
     * Fade to be used for the introductory screen.
     */
    private static float fade = 1.0f;

    /**
     * A Timer used for the introductory screen.
     */
    private static org.qkit.ext.utils.Timer fadeTimer = new org.qkit.ext.utils.Timer();

    /**
     * A list that will store the multiple ImageProducers.
     */
    private static List<ImageCache> cache = new ArrayList<ImageCache>();

    /**
     * Toggle to clear the cache containing representations of the ImageProducers.
     */
    private static boolean cacheCheck = false;

    /**
     * Toggles rendering.
     */
    private static boolean disableRender = false;

    /**
     * Has no real use at the moment.
     */
    public static final Object paintSync = new Object();

    /**
     * Stores the QuickBot logo.
     */
    private static ImageIcon quickLogo;

    /**
     * Retrieves the QuickBot logo.
     *
     * @return The QuickBot logo as an {@link Image}.
     */
    private static Image getQuickBotLogo() {
        if (quickLogo == null) {
            quickLogo = new ImageIcon(Renderer.class.getResource("/org/qkit/core/bot/images/quickbot.png"));
        }
        return quickLogo.getImage();
    }


    public static boolean render(Image image, int y, int x, ImageObserver server) {
        render(image, x, y);
        return true;
    }

    public static boolean render(Image image, int y, int x) {
        if (lessCpu) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }

        if (disableDrawing) {
            Graphics g = Service.getClient().getRealGraphics();
            if (!disableRender) {
                g.clearRect(0, 0, game.getWidth(), game.getHeight());
                g.drawImage(getQuickBotLogo(), 227, 50, null);

                disableRender = true;
            }
            return true;
        } else {
            disableRender = false;
        }

        synchronized (cache) {
            if (Service.getClient().isLoggedIn() && !cacheCheck) {
                cacheCheck = true;
            }

            if (!Service.getClient().isLoggedIn() && cacheCheck) {
                cache.clear();
                game.clear();
                cacheCheck = false;
            }

            ImageCache cached = new ImageCache();
            cached.img = image;
            cached.x = x;
            cached.y = y;
            if (!cache.contains(cached)) {
                cache.add(cached);
            }
            for (ImageCache c : cache) {
                game.getGraphics().drawImage(c.img, c.x, c.y, null);
            }
        }
        synchronized (paintSync) {
            Graphics g = paint.getGraphics();

            if (g == null) {
                g = paint.createGraphics();
            }

            if (g != null) {
                paint.clear();
                try {
                    OnDemandPainter.paint(g);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (fade > 0) {
                int alpha = (int) (255 * fade);
                g.setColor(new Color(0, 0, 0, alpha));
                g.fillRect(0, 0, 765, 503);
                ((Graphics2D) g).setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, fade));
                g.drawImage(getQuickBotLogo(),
                        (765 / 2) - (318 / 2), (503 / 2) - (57 / 2), 318, 57,
                        null);
                g.setColor(Color.BLUE);
                if (fadeTimer.getElapsedTime() > 4000L) {
                    fade -= 0.005;
                }
            }

            screen.getGraphics().drawImage(game.getImage(), 0, 0, null);
            screen.getGraphics().drawImage(paint.getImage(), 0, 0, null);

            if (fade == 0 && fadeTimer != null) {
                fadeTimer.stop();
                fadeTimer = null;
            }

            Service.getClient().getRealGraphics().drawImage(screen, 0, 0, null);
        }
        return true;
    }
}
