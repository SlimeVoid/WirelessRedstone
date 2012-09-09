package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless.LogLevel;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketWirelessTile extends PacketWireless implements
		IRedstoneWirelessData {
	public PacketWirelessTile() {
		super(PacketIds.TILE);
	}

	public PacketWirelessTile(String command, TileEntityRedstoneWireless entity) {
		super(PacketIds.TILE, new PacketPayload(0, 0, 2, 13));
		this.setPosition(entity.getBlockCoord(0), entity.getBlockCoord(1),
				entity.getBlockCoord(2), 0);
		LoggerRedstoneWireless.getInstance(
				"WirelessRedstone: " + this.getClass().toString()).write(
				"[fetchTile]" + this.xPosition + this.yPosition
						+ this.zPosition, LogLevel.INFO);
		this.setCommand(command);
		this.setFreq(entity.getFreq());
		this.setState(RedstoneEther.getInstance().getFreqState(entity.worldObj,
				entity.getFreq()));
		this.setPowerDirections(entity.getPowerDirections());
		this.setInDirectlyPowering(entity.getInDirectlyPowering());
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + ","
				+ zPosition + ")[" + this.getFreq() + "]";
	}

	public boolean[] getPowerDirections() {
		int j = this.payload.getBoolSize() - 12;
		boolean[] dir = new boolean[6];
		for (int i = 0; i < 6; i++) {
			dir[i] = this.payload.getBoolPayload(j);
			j++;
		}
		return dir;
	}

	public void setPowerDirections(boolean[] dir) {
		int j = this.payload.getBoolSize() - 12;
		for (int i = 0; i < 6; i++) {
			this.payload.setBoolPayload(j, dir[i]);
			j++;
		}
	}

	public boolean[] getInDirectlyPowering() {
		int j = this.payload.getBoolSize() - 6;
		boolean[] indir = new boolean[6];
		for (int i = 0; i < 6; i++) {
			indir[i] = this.payload.getBoolPayload(j);
			j++;
		}
		return indir;
	}

	public void setInDirectlyPowering(boolean[] indir) {
		int j = this.payload.getBoolSize() - 6;
		for (int i = 0; i < 6; i++) {
			this.payload.setBoolPayload(j, indir[i]);
			j++;
		}
	}

	@Override
	public boolean targetExists(World world) {
		return world.blockHasTileEntity(this.xPosition, this.yPosition, this.zPosition);
	}
}
