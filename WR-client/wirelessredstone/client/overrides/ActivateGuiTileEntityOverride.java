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
package wirelessredstone.client.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import wirelessredstone.api.IActivateGuiOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.proxy.WRClientProxy;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class ActivateGuiTileEntityOverride implements IActivateGuiOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
		if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
			WRClientProxy.guiWirelessR
					.assTileEntity(tileentityredstonewireless);
			ModLoader.openGUI(entityplayer, WRClientProxy.guiWirelessR);
			return true;
		}
		if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
			WRClientProxy.guiWirelessT
					.assTileEntity(tileentityredstonewireless);
			ModLoader.openGUI(entityplayer, WRClientProxy.guiWirelessT);
			return true;
		}
		return false;
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		return false;
	}

}
