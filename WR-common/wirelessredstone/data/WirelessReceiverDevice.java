package wirelessredstone.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

import wirelessredstone.api.IRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;

public class WirelessReceiverDevice extends WirelessDevice {

	public WirelessReceiverDevice(World world, EntityLiving entity) {
		super(world, entity);
	}
	
	public WirelessReceiverDevice(World world, IWirelessDeviceData deviceData) {
		super(world, deviceData);
	}
	
	@Override
	public String getName() {
		return "Wireless Receiver Device";
	}

	@Override
	public Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessDeviceData.class;
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

}
