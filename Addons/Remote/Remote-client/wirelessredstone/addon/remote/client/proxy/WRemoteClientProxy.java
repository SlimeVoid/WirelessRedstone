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
package wirelessredstone.addon.remote.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.client.network.packets.executors.ClientRemoteChangeFreqExecutor;
import wirelessredstone.addon.remote.client.network.packets.executors.ClientRemoteOpenGui;
import wirelessredstone.addon.remote.client.overrides.ActivateGuiRemoteOverride;
import wirelessredstone.addon.remote.client.overrides.GuiRedstoneWirelessRemoteOverride;
import wirelessredstone.addon.remote.client.presentation.gui.GuiRedstoneWirelessRemote;
import wirelessredstone.addon.remote.client.tickhandler.ClientTickHandler;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.addon.remote.network.packets.executor.ActivateRemoteExecutor;
import wirelessredstone.addon.remote.network.packets.executor.DeactivateRemoteExecutor;
import wirelessredstone.addon.remote.proxy.WRemoteCommonProxy;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.network.ClientPacketHandler;
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
public class WRemoteClientProxy extends WRemoteCommonProxy {

	/**
	 * Wireless Remote GUI
	 */
	public static GuiRedstoneWirelessRemote	guiWirelessRemote;

	@Override
	public void init() {
		TickRegistry.registerTickHandler(	new ClientTickHandler(),
											Side.CLIENT);
		initGUIs();
		super.init();
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessRemote = new GuiRedstoneWirelessRemote();
		IGuiRedstoneWirelessDeviceOverride override = new GuiRedstoneWirelessRemoteOverride();
		guiWirelessRemote.addOverride(override);
		WRClientProxy.addOverride(new ActivateGuiRemoteOverride());
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
		// MinecraftForgeClient.preloadTexture("/WirelessSprites/terrain.png");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir.getPath();
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		if (!world.isRemote) {
			super.activateGUI(	world,
								entityplayer,
								devicedata);
		}
	}

	/**
	 * Retrieves the world object with NetHandler parameters.
	 * 
	 * @return Minecraft world object.
	 */
	@Override
	public World getWorld(NetHandler handler) {
		if (handler instanceof NetClientHandler) {
			return ((NetClientHandler) handler).getPlayer().worldObj;
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
		// ///////////////////
		// Client Handlers //
		// ///////////////////
		ClientPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(	PacketRemoteCommands.remoteCommands.activate.toString(),
																						new ActivateRemoteExecutor());
		ClientPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(	PacketRemoteCommands.remoteCommands.deactivate.toString(),
																						new DeactivateRemoteExecutor());
		ClientPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(	PacketRemoteCommands.remoteCommands.changeFreq.toString(),
																						new ClientRemoteChangeFreqExecutor());
		ClientPacketHandler.getPacketHandler(PacketIds.DEVICEGUI).registerPacketHandler(PacketRemoteCommands.remoteCommands.openGui.toString(),
																						new ClientRemoteOpenGui());
	}

	@Override
	public void activateRemote(World world, EntityLivingBase entityliving) {
		if (!world.isRemote) {
			super.activateRemote(	world,
									entityliving);
		}
	}

	@Override
	public boolean deactivateRemote(World world, EntityLivingBase entityliving) {
		if (!world.isRemote) {
			return super.deactivateRemote(	world,
											entityliving);
		}
		return WirelessRemoteDevice.deactivatePlayerWirelessRemote(	world,
																	entityliving);
	}

	@Override
	public boolean isRemoteOn(World world, EntityPlayer entityplayer, String freq) {
		if (!world.isRemote) {
			return super.isRemoteOn(world,
									entityplayer,
									freq);
		}
		boolean flag = WirelessRemoteDevice.remoteTransmitter == null ? false : WirelessRemoteDevice.remoteTransmitter.getFreq() == freq;
		return flag;
	}
}
