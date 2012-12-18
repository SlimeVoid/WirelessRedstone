package wirelessredstone.addon.remote.overrides;

import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.addon.remote.network.packets.PacketWirelessRemote;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class GuiRedstoneWirelessRemoteOverride implements
		IGuiRedstoneWirelessDeviceOverride {

	@Override
	public boolean beforeFrequencyChange(IWirelessDeviceData data,
			Object oldFreq, Object newFreq) {
		if (data instanceof WirelessRemoteData) {
			World world = ModLoader.getMinecraftInstance().theWorld;
			if (world.isRemote) {
				int OLD = Integer.parseInt(oldFreq.toString());
				int NEW = Integer.parseInt(newFreq.toString());
				Object PacketWirelessDevice;
				if (OLD != NEW) {
					PacketWirelessDevice packet = new PacketWirelessRemote(data);
					packet.setFreq(Integer.toString(NEW - OLD));
					packet.setCommand(PacketRemoteCommands.remoteCommands.changeFreq.toString());
					ClientPacketHandler.sendPacket(packet.getPacket());
				}
			}
		}
		return true;
	}
}
