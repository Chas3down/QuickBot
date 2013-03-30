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
package org.qkit.core.impl.accessors;

/**
 * @author trDna
 * @since 1.7
 */
public interface RSInterface {
	
	public String getMessage();
	
	public int getID();
	
	public String getSpellName();
	
	public int getWidth();
	
	public int getHeight();
	
	public int getParent();
	
	public String[] getActions();
	
	public String getTooltip();
	
	public String getPopup();
	
	public String getSelectedActionName();
	
	public boolean isInventory();
	
	public int[] getStackSize();
	
	public int[] getItems();
	
	public int[] getChildren();
	
	public int[] getChildX();
	
	public int[] getChildY();
	
	public int[] getSpriteX();
	
	public int[] getSpriteY();

}
