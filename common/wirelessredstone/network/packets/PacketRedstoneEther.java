package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Redstone Ether packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneEther extends PacketWireless {
	public PacketRedstoneEther() {
		super(PacketIds.ETHER);
	}

	public PacketRedstoneEther(String command) {
		super(PacketIds.ETHER, new PacketPayload(0, 0, 2, 1));
		setCommand(command);
	}

	public PacketRedstoneEther(TileEntityRedstoneWireless entity, World world) {
		super(PacketIds.ETHER, new PacketPayload(0, 0, 2, 1));
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1),
				entity.getBlockCoord(2), 0);
		if (entity instanceof TileEntityRedstoneWirelessR) {
			setState(((BlockRedstoneWireless) WRCore.blockWirelessR)
					.getState(world, this.xPosition, this.yPosition,
							this.zPosition));
			setCommand("addReceiver");
		} else if (entity instanceof TileEntityRedstoneWirelessT) {
			setState(((BlockRedstoneWireless) WRCore.blockWirelessT)
					.getState(world, this.xPosition, this.yPosition,
							this.zPosition));
			setCommand("addTransmitter");
		}
		setFreq(entity.getFreq());
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + ","
				+ zPosition + ")[" + this.getFreq() + "]:" + this.getState();
	}

	@Override
	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	@Override
	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setCommand(" + command + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public String getFreq() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setFreq(" + freq.toString() + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public void setState(boolean state) {
		this.payload.setBoolPayload(0, state);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setState(" + state + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public boolean getState() {
		return this.payload.getBoolPayload(0);
	}

	@Override
	public boolean targetExists(World world) {
		return world.blockHasTileEntity(this.xPosition, this.yPosition, this.zPosition);
	}
}
