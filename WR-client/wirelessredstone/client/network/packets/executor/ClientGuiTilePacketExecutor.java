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
package wirelessredstone.client.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ClientGuiTilePacketExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance(
				"ClientGuiTilePacketExecutor"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		TileEntity tileentity = packet.getTarget(world);
		if (tileentity != null && tileentity instanceof TileEntityRedstoneWireless) {
			WRCore.proxy.activateGUI(world, entityplayer, (TileEntityRedstoneWireless)tileentity);
		}
	}
}