package wirelessredstone.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IPacketWirelessOverride {

	boolean shouldSkipDefault();

	TileEntity getTarget(World world, int xPosition, int yPosition, int zPosition, TileEntity tileentity);

}
