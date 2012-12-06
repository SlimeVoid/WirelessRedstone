package wirelessredstone.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

import wirelessredstone.api.IRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;

public class WirelessTransmitterDevice extends WirelessDevice {
	
	public WirelessTransmitterDevice(World world, EntityLiving entity) {
		super(world, entity);
	}
	
	public WirelessTransmitterDevice(World world, IWirelessDeviceData deviceData) {
		super(world, deviceData);
	}
	
	@Override
	public String getName() {
		return "Wireless Transmitting Device";
	}

	@Override
	public Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessDeviceData.class;
	}

	@Override
	public void doActivateCommand() {
		RedstoneEther.getInstance().addTransmitter(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq());
		RedstoneEther.getInstance().setTransmitterState(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq(), true);
	}
	
	@Override
	public void doDeactivateCommand() {
		RedstoneEther.getInstance().remTransmitter(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq());
	}

}
