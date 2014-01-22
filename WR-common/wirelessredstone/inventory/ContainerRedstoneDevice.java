package wirelessredstone.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import wirelessredstone.api.IWirelessDevice;

public class ContainerRedstoneDevice extends Container {

	public IWirelessDevice	redstoneDevice;

	public ContainerRedstoneDevice(IWirelessDevice device) {
		super();
		this.redstoneDevice = device;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

}
