package wirelessredstone.addon.remote.overrides;

import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.World;

public interface RedstoneWirelessRemoteOverride {
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote);
}