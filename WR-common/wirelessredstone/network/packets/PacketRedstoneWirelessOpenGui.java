package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneWirelessOpenGui extends PacketWireless {
	public PacketRedstoneWirelessOpenGui() {
		super(PacketIds.GUI);
	}

	public PacketRedstoneWirelessOpenGui(TileEntityRedstoneWireless entity) {
		this();
		this.setCommand(PacketRedstoneWirelessCommands.wirelessCommands.sendGui.toString());
		this.setPosition(
				entity.getBlockCoord(0),
				entity.getBlockCoord(1),
				entity.getBlockCoord(2),
				0);
		this.payload = new PacketPayload(1, 0, 1, 1);
		if (entity instanceof TileEntityRedstoneWirelessR) {
			this.setType(0);
		} else if (entity instanceof TileEntityRedstoneWirelessT) {
			this.setType(1);
		}
		this.setFreq(entity.currentFreq);
		this.setFirstTick(entity.firstTick);
	}

	@Override
	public String toString() {
		return this.payload.getIntPayload(0) + " - (" + xPosition + "," + yPosition + "," + zPosition + ")[" + this.payload
				.getStringPayload(0) + "]";
	}

	public int getType() {
		return this.payload.getIntPayload(0);
	}

	public void setType(int type) {
		this.payload.setIntPayload(0, type);
	}

	public boolean getFirstTick() {
		return this.payload.getBoolPayload(0);
	}

	public void setFirstTick(boolean firstTick) {
		this.payload.setBoolPayload(0, firstTick);
	}

	@Override
	public boolean targetExists(World world) {
		return world.blockHasTileEntity(
				this.xPosition,
				this.yPosition,
				this.zPosition);
	}
}
