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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * 
 * @author Dane
 * 
 */
public class ImageGraphic
{

	BufferedImage image;
    int[] pixels;
	int width, height;

	public ImageGraphic(int width, int height, int type)
	{
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, type);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Graphics createGraphics() {
		return this.image.createGraphics();
	}

	public Graphics getGraphics() {
		return this.image.getGraphics();
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

}