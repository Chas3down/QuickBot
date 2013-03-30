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
package org.qkit.core;


import java.awt.*;

/**
 * A set of internal bot constants.
 *
 * @author trDna
 */
public interface BotInternalConstants {

    /**
     * The RuneSens client JAR URL.
     */
    public String RUNESENS_CLIENT_URL = "http://dl.dropbox.com/u/9329174/client.jar";

    /**
     * The client JAR path.
     */
    public String JAR_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + "client.jar";

    /**
     * The injected client JAR path.
     */
    public String INJ_JAR_PATH = JAR_PATH.replace("client.jar", "client_injected.jar");

    /**
     * The accessor path.
     */
    public String ACCESSOR_PATH = "org/qkit/core/impl/accessors/";

    /**
     * The Game's dimensions.
     */
    public Dimension GAME_DIMENSION = new Dimension(765, 503);

    /**
     * The BufferedImage's dimensions (used when double buffering).
     */
    public Dimension BUFFER_DIMENSION = new Dimension(771, 531);

}
