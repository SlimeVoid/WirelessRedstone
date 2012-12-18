package wirelessredstone.device;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;

public abstract class WirelessReceiverDevice extends WirelessDevice {
	
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
		RedstoneEther.getInstance().remReceiver(this.getWorld(),
				this.getCoords().getX(), this.getCoords().getY(),
				this.getCoords().getZ(), this.getFreq());
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
