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
package com.slimevoid.wirelessredstone.client.overrides;

import com.slimevoid.wirelessredstone.api.IActivateGuiOverride;
import com.slimevoid.wirelessredstone.api.IWirelessDevice;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ActivateGuiTileEntityOverride implements IActivateGuiOverride {

    @Override
    public boolean beforeOpenGui(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
        if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
            // WRClientProxy.guiWirelessR.assTileEntity(tileentityredstonewireless);
            // FMLClientHandler.instance().displayGuiScreen( entityplayer,
            // WRClientProxy.guiWirelessR);
            return true;
        }
        if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
            // WRClientProxy.guiWirelessT.assTileEntity(tileentityredstonewireless);
            // FMLClientHandler.instance().displayGuiScreen( entityplayer,
            // WRClientProxy.guiWirelessT);
            return true;
        }
        return false;
    }

    @Override
    public boolean beforeOpenGui(World world, EntityPlayer entityplayer, IWirelessDevice wirelessDevice) {
        return false;
    }

}
