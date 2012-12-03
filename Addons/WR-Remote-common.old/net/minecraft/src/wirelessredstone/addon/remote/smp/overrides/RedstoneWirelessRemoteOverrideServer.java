package net.minecraft.src.wirelessredstone.addon.remote.smp.overrides;

import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.RedstoneWirelessRemoteOverride;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.PacketHandlerWirelessRemote;

public class RedstoneWirelessRemoteOverrideServer implements
		RedstoneWirelessRemoteOverride {

	@Override
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote) {
		PacketHandlerWirelessRemote.PacketHandlerOutput.sendWirelessRemoteToAll(command, world, remote);
		return false;
	}

}
