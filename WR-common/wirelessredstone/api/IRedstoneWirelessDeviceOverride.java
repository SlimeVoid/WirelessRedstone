package wirelessredstone.api;

import net.minecraft.src.World;

public interface IRedstoneWirelessDeviceOverride {

	boolean beforeTransmit(String command, World world, IWirelessDevice wirelessDevice);

}
