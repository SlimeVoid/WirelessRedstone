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
package wirelessredstone.addon.powerconfig.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import wirelessredstone.addon.powerconfig.client.network.packets.executors.ClientRemoteOpenGui;
import wirelessredstone.addon.powerconfig.network.packets.PacketPowerConfigCommands;
import wirelessredstone.addon.powerconfig.proxy.PowerConfigCommonProxy;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.network.ClientPacketHandler;
import wirelessredstone.client.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.client.proxy.WRClientProxy;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.core.PacketIds;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class PowerConfigClientProxy extends PowerConfigCommonProxy {

	/**
	 * Wireless Remote GUI
	 */
	public static GuiRedstoneWireless guiPowerConfig;
	
	@Override
	public void init() {
		initGUIs();
		super.init();
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		//guiPowerConfig = new GuiRedstoneWireless();
		// TODO :: GUI
		/**IGuiRedstoneWirelessDeviceOverride override = new GuiRedstoneWirelessRemoteOverride();
		guiWirelessRemote.addOverride(override);
		WRClientProxy.addOverride(new ActivateGuiRemoteOverride());**/
		// TODO :: Overrides
	}
	
	@Override
	public void registerRenderInformation() {
		loadBlockTextures();
	}

	/**
	 * Loads all Block textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadBlockTextures() {
		//MinecraftForgeClient.preloadTexture("/WirelessSprites/terrain.png");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		if (!world.isRemote) {
			super.activateGUI(world, entityplayer, devicedata);
		}
	}
	
	/**
	 * Retrieves the world object without parameters
	 * 
	 * @return the world
	 */
	@Override
	public World getWorld() {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	/**
	 * Retrieves the player object
	 * 
	 * @return the player
	 */
	@Override
	public EntityPlayer getPlayer() {
		return ModLoader.getMinecraftInstance().thePlayer;
	}
	
	/**
	 * Retrieves the world object with NetHandler parameters.
	 * 
	 * @return Minecraft world object.
	 */
	@Override
	public World getWorld(NetHandler handler) {
		if (handler instanceof NetClientHandler) {
			return ((NetClientHandler)handler).getPlayer().worldObj;
		}
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
		World world = getWorld(handler);
		if (world != null) {
			ClientPacketHandler.sendPacket(((new PacketRedstoneEther(PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString())).getPacket()));
		}
	}
	
	@Override
	public void initPacketHandlers() {
		super.initPacketHandlers();
		/////////////////////
		// Client Handlers //
		/////////////////////
		ClientPacketHandler.getPacketHandler(PacketIds.GUI).registerPacketHandler(
				PacketPowerConfigCommands.remoteCommands.openGui.toString(),
				new ClientRemoteOpenGui());
	}
}
