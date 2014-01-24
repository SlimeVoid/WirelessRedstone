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
package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.items.ItemRedstoneWirelessRemote;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class RemoteChangeFreqExecutor implements IDevicePacketExecutor {

	@Override
	public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
		if (p instanceof PacketWirelessDevice) {
			PacketWirelessDevice packet = (PacketWirelessDevice) p;
			ItemStack itemstack = entityplayer.getHeldItem();
			int freq = Integer.parseInt(packet.getFreq());
			int oldFreq = Integer.parseInt(String.valueOf(ItemRedstoneWirelessRemote.getFreq(itemstack)));
			ItemRedstoneWirelessRemote.setFreq(	itemstack,
												Integer.toString(oldFreq + freq));
			// device.onInventoryChanged();
			String newFreq = ItemRedstoneWirelessRemote.getFreq(itemstack);
			PacketWirelessDevice remotePacket = new PacketWirelessDevice(newFreq, false);
			remotePacket.setCommand(PacketRemoteCommands.remoteCommands.changeFreq.toString());
			ServerPacketHandler.broadcastPacket(remotePacket.getPacket());
		}
	}
}