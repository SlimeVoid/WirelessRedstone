package wirelessredstone.device;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;

public class WirelessReceiverDevice extends WirelessDevice {
	
	public WirelessReceiverDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		super(world, entityliving, deviceData);
	}
	
	@Override
	public String getName() {
		return "Wireless Receiver Device";
	}

	@Override
	public void doActivateCommand() {
		RedstoneEther.getInstance().addReceiver(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq());
	}

	@Override
	public void doDeactivateCommand() {
		RedstoneEther.getInstance().remReceiver(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq());
	}

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessDeviceData.class;
	}

}
