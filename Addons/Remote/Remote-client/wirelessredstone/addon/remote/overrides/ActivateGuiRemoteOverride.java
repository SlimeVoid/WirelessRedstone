package wirelessredstone.addon.remote.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.proxy.WRemoteClientProxy;
import wirelessredstone.api.IActivateGuiOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ActivateGuiRemoteOverride implements IActivateGuiOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
		return false;
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		if (devicedata.getDeviceType().equals(WRemoteCore.itemRemote.getItemName())) {
			WRemoteClientProxy.guiWirelessRemote.assWirelessDevice(devicedata, entityplayer);
			ModLoader.openGUI(entityplayer, WRemoteClientProxy.guiWirelessRemote);
			return true;
		}
		return false;
	}

}
