package wirelessredstone.device;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;

public abstract class WirelessTransmitterDevice extends WirelessDevice {
	
	public WirelessTransmitterDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		super(world, entityliving, deviceData);
	}
	
	@Override
	public String getName() {
		return "Wireless Transmitting Device";
	}

	@Override
	public void doActivateCommand() {
		RedstoneEther.getInstance().addTransmitter(
				this.getWorld(),
				this.xCoord,
				this.yCoord,
				this.zCoord,
				this.getFreq());
		RedstoneEther.getInstance().setTransmitterState(
				this.getWorld(),
				this.xCoord,
				this.yCoord,
				this.zCoord,
				this.getFreq(),
				true);
		PacketWirelessDevice packet = this.getDevicePacket(this.data);
		packet.setPosition(
				this.xCoord,
				this.yCoord,
				this.zCoord,
				0);
		packet.setCommand(this.getActivateCommand());
		ServerPacketHandler.broadcastPacket(packet.getPacket());
	}
	
	@Override
	public void doDeactivateCommand() {
		RedstoneEther.getInstance().remTransmitter(
				this.getWorld(),
				this.xCoord,
				this.yCoord,
				this.zCoord,
				this.getFreq());
		PacketWirelessDevice packet = this.getDevicePacket(this.data);
		packet.setPosition(
				this.xCoord,
				this.yCoord,
				this.zCoord,
				0);
		packet.setCommand(this.getDeactivateCommand());
		ServerPacketHandler.broadcastPacket(packet.getPacket());
	}

}
