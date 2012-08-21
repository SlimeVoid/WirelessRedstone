package wirelessredstone.network.packets.core;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class PacketTileEntity extends PacketUpdate {

	public PacketTileEntity() {
		super(PacketIds.TILE);
	}

	public TileEntity getTileEntity(World world) {
		return world.getBlockTileEntity(this.xPosition, this.yPosition,
				this.zPosition);
	}

	@Override
	public boolean targetExists(World world) {
		if (world.blockExists(this.xPosition, this.yPosition, this.zPosition)) {
			return true;
		}
		return false;
	}
}
