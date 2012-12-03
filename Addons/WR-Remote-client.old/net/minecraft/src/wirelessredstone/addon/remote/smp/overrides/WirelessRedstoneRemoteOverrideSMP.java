package net.minecraft.src.wirelessredstone.addon.remote.smp.overrides;

import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.RedstoneWirelessRemoteOverride;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.PacketHandlerWirelessRemote.PacketHandlerOutput;

public class WirelessRedstoneRemoteOverrideSMP implements
		RedstoneWirelessRemoteOverride {

	@Override
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote) {
		if (world.isRemote) {
			PacketHandlerOutput.sendWirelessRemotePacket(command, remote);
			return true;
		} else
			return false;
	}
}
