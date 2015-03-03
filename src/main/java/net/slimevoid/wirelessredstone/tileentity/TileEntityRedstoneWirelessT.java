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
package net.slimevoid.wirelessredstone.tileentity;

import net.slimevoid.wirelessredstone.block.BlockRedstoneWireless;
import net.slimevoid.wirelessredstone.core.WRCore;
import net.slimevoid.wirelessredstone.core.lib.BlockLib;

public class TileEntityRedstoneWirelessT extends TileEntityRedstoneWireless {
    public TileEntityRedstoneWirelessT() {
        super((BlockRedstoneWireless) WRCore.blockWirelessT);
    }

    @Override
    public String getCommandSenderName() {
        return BlockLib.WIRELESS_TRANSMITTER;
    }

    @Override
    protected void onUpdateEntity() {
    }
}
