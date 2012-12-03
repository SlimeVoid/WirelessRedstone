package net.minecraft.src.wirelessredstone.addon.remote.overrides;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;

public interface RedstoneWirelessRemoteOverride {
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote);
}