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


import java.awt.*;

/**
 * Client related operations.
 * For internal use only.
 *
 * @author trDna
 * @since 1.7
 */
public interface Client extends GameApplet{

    /**
     * Retrieves the camera's x component of the camera.
     *
     * @return The magnitude of the x component of the camera.
     */
    public int getCameraX();

    /**
     * Retrieves the camera's y component of the camera.
     *
     * @return The coordinate of the y component of the camera.
     */
    public int getCameraY();

    /**
     * Retrieves the camera's z component of the camera.
     *
     * @return The coordinate of the z component of the camera.
     */
    public int getCameraZ();

    /**
     * Retrieves the player's plane.
     *
     * @return The coordinate of the z component of the camera.
     */
    public int getPlane();

    /**
     * Retrieves the player's skill statistics.
     *
     * @return The array of the player's skill levels.
     */
    public int[] getCurrentStats();

    /**
     * Retrieves the player's experience points in each skill.
     *
     * @return An int array of current experience points in each skill.
     */
    public int[] getCurrentExp();

    /**
     * Retrieves if the player is logged in.
     *
     * @return True if the player is logged in, false if otherwise.
     */
    public boolean isLoggedIn();

    /**
     * Retrieves the camera's curve (x-component).
     *
     * @return The camera's x-curve.
     */
    public int getCameraCurveX();

    /**
     * Retrieves the camera's curve (y-component).
     *
     * @return The camera's y-curve.
     */
    public int getCameraCurveY();

    /**
     * Retrieves the camera's zoom.
     *
     * @return The camera's zoom.
     */
    public int getCameraZoom();

    /**
     * Retrieves the menu's area.
     *
     * @return The menu's screen area.
     */
    public int getMenuScreenArea();

    /**
     * Retrieves the menu's offset.
     *
     * @return The menu's offset (X).
     */
    public int getMenuOffsetX();

    /**
     * Retrieves the menu's offset.
     *
     * @return The menu's offset (Y).
     */
    public int getMenuOffsetY();

    /**
     * Retrieves the menu's width.
     *
     * @return The menu's width.
     */
    public int getMenuWidth();

    /**
     * Retrieves the menu's height.
     *
     * @return The menu's height.
     */
    public int getMenuHeight();

    /**
     * Retrieves the player's x coordinate on the map.
     *
     * @return The x-coordinate of the player.
     */
    public int getMapBaseX();

    /**
     * Retrieves the player's y coordinate on the map.
     *
     * @return The y-coordinate of the player.
     */
    public int getMapBaseY();

    /**
     * Retrieves the Npcs indices.
     *
     * @return Npc indices.
     */
    public int[] getNPCIndices();

    /**
     * Retrieves the surrounding player indices.
     * @return Player indices.
     */
    public int[] getPlayerIndices();

    /**
     * Retrives the player's wanted destination.
     *
     * @return The x-coordinate of the tile.
     */
    public int getDestX();

    /**
     * Retrives the player's wanted destination.
     *
     * @return The y-coordinate of the tile.
     */
    public int getDestY();


    public int getMinimapInt1();

    public int getMinimapInt2();

    public int getMinimapInt3();

    public Graphics getRealGraphics();

	public NodeList getProjectiles();
	
	//public RSFloor[] getFloorCache();


	public void openTab(int tab);


	
	public Player[] getPlayers();
	
	public Npc[] getNPCs();




	
	public String[] getChatNames();
	
	//public String[] getChatMessages();
	
	public WorldController getWorldController();

	
	public int[] getSettings();
	
	public Player getMyPlayer();
	
	public int getMenuActionRow();
	
	public byte[][][] getGroundByteArray();

	
	public int[][][] getIntGroundArray();

	
	public int getOpenTab();

	public String[] getMenuActionNames();
	
	public boolean isMenuOpen();
	
	public NodeList[][][] getGroundItemsArray();
	
	public WorldGraph[] getTileNodeSettings();
	
	public RSInterface[] getInterfaceCache();

	public int getOpenInterfaceId();

	
	public int getLoopCycle();

	public WorldFloor[] getFloorCache();


}
