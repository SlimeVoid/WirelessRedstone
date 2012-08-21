package wirelessredstone.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import wirelessredstone.api.IBaseModOverride;

public class BaseModOverrideSMP implements IBaseModOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		return (world.isRemote);
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			WorldSavedData data) {
		return (world.isRemote);
	}
}