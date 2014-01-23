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
package wirelessredstone.client.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.api.IActivateGuiOverride;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.client.network.ClientPacketHandler;
import wirelessredstone.client.network.handlers.ClientAddonPacketHandler;
import wirelessredstone.client.network.handlers.ClientRedstoneEtherPacketHandler;
import wirelessredstone.client.network.handlers.ClientTilePacketHandler;
import wirelessredstone.client.network.packets.executor.ClientEtherPacketRXAddExecutor;
import wirelessredstone.client.network.packets.executor.ClientEtherPacketTXAddExecutor;
import wirelessredstone.client.overrides.ActivateGuiTileEntityOverride;
import wirelessredstone.client.overrides.RedstoneEtherOverrideSMP;
import wirelessredstone.client.overrides.TileEntityRedstoneWirelessOverrideSMP;
import wirelessredstone.client.presentation.BlockRedstoneWirelessRenderer;
import wirelessredstone.client.presentation.TileEntityRedstoneWirelessRenderer;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessR;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessT;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.proxy.WRCommonProxy;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class WRClientProxy extends WRCommonProxy {

	/**
	 * Wireless Receiver GUI
	 */
	public static GuiRedstoneWirelessInventory	guiWirelessR;
	/**
	 * Wireless Transmitter GUI
	 */
	public static GuiRedstoneWirelessInventory	guiWirelessT;

	private static List<IActivateGuiOverride>	overrides;

	@Override
	public void init() {
		initGUIs();
		super.init();
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessR = new GuiRedstoneWirelessR();
		guiWirelessT = new GuiRedstoneWirelessT();
	}

	/**
	 * Adds a GUI override to the Receiver.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToReceiver(IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("WRClientProxy").write(	true,
																	"Override added to "
																			+ LoggerRedstoneWireless.filterClassName(guiWirelessR.getClass().toString())
																			+ " - "
																			+ LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
																	LoggerRedstoneWireless.LogLevel.DEBUG);
		guiWirelessR.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Transmitter.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToTransmitter(IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("WRClientProxy").write(	true,
																	"Override added to "
																			+ LoggerRedstoneWireless.filterClassName(guiWirelessT.getClass().toString())
																			+ " - "
																			+ LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
																	LoggerRedstoneWireless.LogLevel.DEBUG);
		guiWirelessT.addOverride(override);
	}

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerBlockHandler(	BlockRedstoneWirelessRenderer.renderID,
												new BlockRedstoneWirelessRenderer());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiLib.GUIID_INVENTORY) {
			TileEntity tileentity = world.getBlockTileEntity(	x,
																y,
																z);
			if (tileentity != null) {
				if (tileentity instanceof TileEntityRedstoneWirelessT) {
					guiWirelessT.assTileEntity((TileEntityRedstoneWirelessT) tileentity);
					return guiWirelessT;
				}
				if (tileentity instanceof TileEntityRedstoneWirelessR) {
					guiWirelessR.assTileEntity((TileEntityRedstoneWirelessR) tileentity);
					return guiWirelessR;
				}
			}
		}
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir.getPath();
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
		ClientRegistry.bindTileEntitySpecialRenderer(	clazz,
														new TileEntityRedstoneWirelessRenderer());
	}

	@Override
	public void addOverrides() {
		overrides = new ArrayList();

		RedstoneEtherOverrideSMP etherOverride = new RedstoneEtherOverrideSMP();
		RedstoneEther.getInstance().addOverride(etherOverride);

		// GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new
		// GuiRedstoneWirelessInventoryOverrideSMP();
		// addGuiOverrideToReceiver(GUIOverride);
		// addGuiOverrideToTransmitter(GUIOverride);

		// BlockRedstoneWirelessOverrideSMP blockOverride = new
		// BlockRedstoneWirelessOverrideSMP();
		// WRCore.addOverrideToReceiver(blockOverride);
		// WRCore.addOverrideToTransmitter(blockOverride);

		TileEntityRedstoneWirelessOverrideSMP tileOverride = new TileEntityRedstoneWirelessOverrideSMP();
		TileEntityRedstoneWireless.addOverride(tileOverride);

		// RedstoneEtherOverrideSMP etherOverrideSMP = new
		// RedstoneEtherOverrideSMP();
		// RedstoneEther.getInstance().addOverride(etherOverrideSMP);

		IActivateGuiOverride openGuiOverride = new ActivateGuiTileEntityOverride();
		addOverride(openGuiOverride);

		// BaseModOverrideSMP baseModOverride = new BaseModOverrideSMP();
		// this.addOverride(baseModOverride);
	}

	/**
	 * Adds a Base override to the The Mod.
	 * 
	 * @param override
	 *            Mod override
	 */
	public static void addOverride(IActivateGuiOverride override) {
		overrides.add(override);
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
		ClientPacketHandler.init();
		// Ether Packets
		ClientRedstoneEtherPacketHandler etherPacketHandler = new ClientRedstoneEtherPacketHandler();
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
													new ClientEtherPacketTXAddExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
													new ClientEtherPacketRXAddExecutor());
		ClientPacketHandler.registerPacketHandler(	PacketIds.ETHER,
													etherPacketHandler);
		// Tile Packets
		ClientPacketHandler.registerPacketHandler(	PacketIds.TILE,
													new ClientTilePacketHandler());
		// Addon
		ClientAddonPacketHandler addonPacketHandler = new ClientAddonPacketHandler();
		ClientPacketHandler.registerPacketHandler(	PacketIds.ADDON,
													addonPacketHandler);
	}
}
