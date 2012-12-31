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
package wirelessredstone.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

/**
 * Execute a remove transmitter command.<br>
 * <br>
 * Removes the transmitter from the ether.
 * 
 * @author ali4z
 */
public class EtherPacketTXRemExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		RedstoneEther.getInstance().remTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

}
