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
package wirelessredstone.client.network.handlers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.client.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.handlers.SubPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ClientTilePacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketWirelessTile();
	}

	@Override
	protected void handlePacket(PacketWireless packet, World world, EntityPlayer player) {
		LoggerRedstoneWireless.getInstance("ClientTilePacketHandler").write(world.isRemote,
																			"handlePacket("
																					+ packet.toString()
																					+ ")",
																			LoggerRedstoneWireless.LogLevel.DEBUG);

		TileEntity tileentity = packet.getTarget(world);
		if (packet.getCommand().equals(PacketRedstoneWirelessCommands.wirelessCommands.fetchTile.toString())) {
			handleFetchTile(packet,
							tileentity);
		}
	}

	private void handleFetchTile(PacketWireless packet, TileEntity tileentity) {
		if (tileentity != null
			&& tileentity instanceof TileEntityRedstoneWireless) {
			TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless) tileentity;
			tileentityredstonewireless.handleData((PacketWirelessTile) packet);

			GuiScreen screen = ModLoader.getMinecraftInstance().currentScreen;
			if (screen != null
				&& screen instanceof GuiRedstoneWireless
				&& screen instanceof GuiRedstoneWirelessInventory
				&& ((GuiRedstoneWirelessInventory) screen).compareInventory(tileentityredstonewireless)) ((GuiRedstoneWireless) screen).refreshGui();
		}
	}
}
