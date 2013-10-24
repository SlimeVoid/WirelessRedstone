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
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class ClientEtherPacketTXAddExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		TileEntity tileentity = packet.getTarget(world);
		if (tileentity != null
			&& tileentity instanceof TileEntityRedstoneWirelessT) {
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
			/*
			 * } else { tileentity = new TileEntityRedstoneWirelessT();
			 * ((TileEntityRedstoneWireless)
			 * tileentity).setFreq(packet.getFreq().toString());
			 * world.setBlockTileEntity( packet.xPosition, packet.yPosition,
			 * packet.zPosition, tileentity );
			 */
		}
		RedstoneEther.getInstance().addTransmitter(	world,
													packet.xPosition,
													packet.yPosition,
													packet.zPosition,
													packet.getFreq().toString());
	}

}
