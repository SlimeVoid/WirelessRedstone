package wirelessredstone.inventory;

import net.minecraft.inventory.Container;
import wirelessredstone.api.IWirelessDevice;

public abstract class ContainerRedstoneDevice extends Container {

	public IWirelessDevice	redstoneDevice;

	public ContainerRedstoneDevice(IWirelessDevice device) {
		super();
		this.redstoneDevice = device;
	}

}
