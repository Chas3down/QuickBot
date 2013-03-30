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
package org.qkit.core.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author trDna
 * @since 1.7
 */
public class NetUtils {

    /**
     * Downloads a file from a given URL.
     *
     * @param url The URL.
     * @param fileName The wanted file name with an optional path.
     */
    public static void downloadFile(final String url, final String fileName){

        try {
            //Create a URL representation of a web address.
            URL site = new URL(url);

            //Create a byte reader.
            ReadableByteChannel rbc = Channels.newChannel(site.openStream());

            //Setup the output stream of the file
            @SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(fileName);

            //Download the file
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
