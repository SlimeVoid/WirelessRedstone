package wirelessredstone.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;

/**
 * Base Mod override.<br>
 * Used for injecting code into Client side BaseMod code.<br>
 * Useful for addons that changes the mechanics of existing Base code.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * @author Eurymachus
 * 
 */
public interface IBaseModOverride {
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			TileEntity tileentity);

	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			WorldSavedData data);
}