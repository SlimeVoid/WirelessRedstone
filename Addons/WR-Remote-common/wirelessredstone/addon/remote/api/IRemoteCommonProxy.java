package wirelessredstone.addon.remote.api;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;

public interface IRemoteCommonProxy extends ICommonProxy {
	public void activateRemote(World world, EntityLiving entityliving);

	public void deactivateRemote(World world, EntityLiving entityliving);
}
