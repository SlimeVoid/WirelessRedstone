package wirelessredstone.addon.remote.overrides;

import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;

public interface RedstoneWirelessRemoteOverride {
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote);
}