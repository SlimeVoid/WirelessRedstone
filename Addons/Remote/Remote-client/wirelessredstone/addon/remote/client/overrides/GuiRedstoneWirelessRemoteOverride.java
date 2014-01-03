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
package wirelessredstone.addon.remote.client.overrides;

import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiRedstoneWirelessRemoteOverride implements
		IGuiRedstoneWirelessDeviceOverride {

	@Override
	public boolean beforeFrequencyChange(IWirelessDeviceData data, Object oldFreq, Object newFreq) {
		if (data instanceof WirelessRemoteData) {
			World world = FMLClientHandler.instance().getClient().theWorld;
			if (world.isRemote) {
				int OLD = Integer.parseInt(oldFreq.toString());
				int NEW = Integer.parseInt(newFreq.toString());
				if (OLD != NEW) {
					PacketWirelessDevice packet = new PacketWirelessDevice(data);
					packet.setFreq(Integer.toString(NEW - OLD));
					packet.setCommand(PacketRemoteCommands.remoteCommands.changeFreq.toString());
					ClientPacketHandler.sendPacket(packet.getPacket());
				}
			}
		}
		return true;
	}
}
