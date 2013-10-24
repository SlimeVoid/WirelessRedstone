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

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.IGuiHandler;
// import net.minecraft.network.INetworkManager;
// import net.minecraft.network.packet.NetHandler;
// import net.minecraft.network.packet.Packet1Login;
// import net.minecraft.tileentity.TileEntity;

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
	 * Returns the minecraft directory
	 * 
	 * @return Directory path of the minecraft installation.
	 */
	public String getMinecraftDir();

	/**
	 * Registers special renderers for TileEntities.
	 * 
	 * @param clazz
	 *            A TileEntity child class.
	 */
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);

	/**
	 * Adds all overrides.
	 */
	public void addOverrides();

	/**
	 * Called on activity in the GUI.
	 * 
	 * @param world
	 *            Minecraft world object.
	 * @param entityplayer
	 *            The player that is opening the GUI
	 * @param tileentityredstonewireless
	 *            Tile entity related to the GUI
	 */
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless);

	/**
	 * Called on activity in the GUI.
	 * 
	 * @param world
	 *            Minecraft world object.
	 * @param entityplayer
	 *            The player that is opening the GUI
	 * @param device
	 *            Wireless Device related to the GUI
	 */
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata);

	/**
	 * Fetches the current minecraft world object relating to the NetHandler.
	 * 
	 * handler the NetHandler (Server or Client)
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld(NetHandler handler);

	/**
	 * Called on initialization.
	 */
	public void init();

	/**
	 * Initializes packet handlers.<br>
	 * - Ether<br>
	 * - Gui<br>
	 * - Tile<br>
	 * For Server and Client.
	 */
	public void initPacketHandlers();

	/**
	 * Called on player/client login
	 * 
	 * @param handler
	 * @param manager
	 * @param login
	 */
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login);

	/**
	 * Called when a connection to a server is closed
	 * 
	 * @param manager
	 */
	public void connectionClosed(INetworkManager manager);

	void doSomething(String command, World world, int x, int y, int z);

	/**
	 * Registers a configuration file allows sided properties
	 * 
	 * @param configFile
	 *            usually the suggested Configuration File
	 */
	public void registerConfiguration(File configFile);
}
