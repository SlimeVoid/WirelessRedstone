package wirelessredstone.addon.remote.api;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.api.ICommonProxy;

public interface IRemoteCommonProxy extends ICommonProxy {
	public void activateRemote(World world, EntityLiving entityliving);

	public boolean deactivateRemote(World world, EntityLiving entityliving);

	public boolean isRemoteOn(World world, EntityPlayer entityplayer, String freq);
}
