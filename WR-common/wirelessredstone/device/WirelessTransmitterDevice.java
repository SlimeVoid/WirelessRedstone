package wirelessredstone.device;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;

public class WirelessTransmitterDevice extends WirelessDevice {
	
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
		PacketWirelessDevice packet = new PacketWirelessDevice(this.data);
		packet.setPosition(
				this.xCoord,
				this.yCoord,
				this.zCoord,
				0);
		packet.setCommand(PacketWirelessDeviceCommands.deviceCommands.activateTX.toString());
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload)packet.getPacket());
	}
	
	@Override
	public void doDeactivateCommand() {
		RedstoneEther.getInstance().remTransmitter(
				this.getWorld(),
				this.xCoord,
				this.yCoord,
				this.zCoord,
				this.getFreq());
		PacketWirelessDevice packet = new PacketWirelessDevice(this.data);
		packet.setPosition(
				this.xCoord,
				this.yCoord,
				this.zCoord,
				0);
		packet.setCommand(PacketWirelessDeviceCommands.deviceCommands.deactivateTX.toString());
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload)packet.getPacket());
	}

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessDeviceData.class;
	}

}
