package wirelessredstone.addon.remote.api;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;

public interface IRemoteCommonProxy extends ICommonProxy {
	public void activateRemote(World world, EntityLiving entityliving);

	public boolean deactivateRemote(World world, EntityLiving entityliving);

	public boolean isRemoteOn(World world, EntityPlayer entityplayer, String freq);
}
