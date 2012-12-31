/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.addon.remote.client.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.client.proxy.WRemoteClientProxy;
import wirelessredstone.addon.remote.core.WRemoteCore;
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
