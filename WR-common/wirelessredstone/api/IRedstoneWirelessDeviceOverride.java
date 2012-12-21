package wirelessredstone.api;

import net.minecraft.world.World;

public interface IRedstoneWirelessDeviceOverride {

	boolean beforeTransmit(String command, World world, IWirelessDevice wirelessDevice);

}
