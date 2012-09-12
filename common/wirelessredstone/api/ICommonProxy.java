/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * 
 * Abstraction of the common proxy, which is used for general mod functionality.
 * 
 * @author Eurymachus
 * 
 */
public interface ICommonProxy extends IGuiHandler {

	/**
	 * Registers rendering information. Called in the core before registering
	 * tileentity special renderers.
	 */
	public void registerRenderInformation();

	/**
	 * Used for opening a GUI on the client.
	 * 
	 * @param world Minecraft World object
	 * @param entityplayer The player that is opening the GUI
	 * @param tileentity Tile entity related to the GUI
	 */
	public void openGUI(World world, EntityPlayer entityplayer, TileEntity tileentity);

	/**
	 * Returns the minecraft directory
	 * 
	 * @return Directory path of the minecraft installation.
	 */
	public String getMinecraftDir();

	/**
	 * Registers special renderers for TileEntities.
	 * 
	 * @param clazz A TileEntity child class.
	 */
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);

	/**
	 * Adds all overrides.
	 */
	public void addOverrides();

	/**
	 * Called on activity in the GUI.
	 * 
	 * @param world Minecraft world object.
	 * @param entityplayer The player that is opening the GUI
	 * @param tileentity Tile entity related to the GUI
	 */
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntity tileentity);

	/**
	 * Fetches the current minecraft world object.
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld();

	/**
	 * Fetches the current player.
	 * 
	 * @return Player
	 */
	public EntityPlayer getPlayer();

	/**
	 * Called on initialization.
	 */
	public void init();
}
