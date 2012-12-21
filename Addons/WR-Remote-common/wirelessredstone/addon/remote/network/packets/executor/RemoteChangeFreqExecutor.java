package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class RemoteChangeFreqExecutor implements IDevicePacketExecutor {

	@Override
	public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
		if (p instanceof PacketWirelessDevice) {
			PacketWirelessDevice packet = (PacketWirelessDevice) p;
			IWirelessDeviceData data = packet.getDeviceData(WirelessRemoteData.class, world, entityplayer);
			int freq = Integer.parseInt(packet.getDeviceFreq());
			int oldFreq = Integer.parseInt(data.getDeviceFreq());
			data.setDeviceFreq(Integer.toString(oldFreq + freq));
			PacketWirelessDevice remotePacket = new PacketWirelessDevice(data);
			remotePacket.setCommand(PacketRemoteCommands.remoteCommands.changeFreq.toString());
			ServerPacketHandler.broadcastPacket(remotePacket.getPacket());
		}
	}
}