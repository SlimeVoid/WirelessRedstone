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
package wirelessredstone.addon.camouflager.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.client.presentation.BlockWirelessCamouRenderer;
import wirelessredstone.addon.camouflager.client.presentation.gui.GuiRedstoneWirelessCamouflager;
import wirelessredstone.addon.camouflager.inventory.ContainerCamouflagedRedstoneWireless;
import wirelessredstone.addon.camouflager.proxy.CamouCommonProxy;
import wirelessredstone.client.presentation.BlockRedstoneWirelessRenderer;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class CamouClientProxy extends CamouCommonProxy {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void registerRenderInformation() {
		BlockRedstoneWirelessRenderer.addOverride(new BlockWirelessCamouRenderer());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(	x,
															y,
															z);
		if (tileentity instanceof TileEntityRedstoneWireless) {
			return new GuiRedstoneWirelessCamouflager(new ContainerCamouflagedRedstoneWireless(player.inventory, tileentity));
		}
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir.getPath();
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
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
			// ClientPacketHandler.sendPacket(((new
			// PacketRedstoneEther(PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString())).getPacket()));
		}
	}

	@Override
	public void initPacketHandlers() {
		super.initPacketHandlers();
		// ///////////////////
		// Client Handlers //
		// ///////////////////
		// ClientPacketHandler.getPacketHandler(PacketIds.GUI).registerPacketHandler(
		// PacketPowerConfigCommands.powerConfigCommands.openGui.toString(),
		// new ClientRemoteOpenGui());
	}
}
