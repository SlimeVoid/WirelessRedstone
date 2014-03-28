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
package net.slimevoid.wirelessredstone.network.packets;

import net.minecraft.world.World;
import net.slimevoid.library.network.PacketPayload;
import net.slimevoid.wirelessredstone.network.packets.core.PacketIds;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneWirelessOpenGuiInventory extends PacketWireless {

    public PacketRedstoneWirelessOpenGuiInventory() {
        super(PacketIds.GUI);
    }

    public PacketRedstoneWirelessOpenGuiInventory(TileEntityRedstoneWireless entity) {
        this();
        this.setCommand(PacketRedstoneWirelessCommands.wirelessCommands.sendGui.toString());
        this.setPosition(entity.getBlockCoord(0),
                         entity.getBlockCoord(1),
                         entity.getBlockCoord(2),
                         0);
        this.payload = new PacketPayload(1, 0, 1, 1);
        if (entity instanceof TileEntityRedstoneWirelessR) {
            this.setType(0);
        } else if (entity instanceof TileEntityRedstoneWirelessT) {
            this.setType(1);
        }
        this.setFreq(entity.currentFreq);
        this.setFirstTick(entity.firstTick);
    }

    @Override
    public String toString() {
        return this.payload.getIntPayload(0) + " - (" + xPosition + ","
               + yPosition + "," + zPosition + ")["
               + this.payload.getStringPayload(0) + "]";
    }

    public int getType() {
        return this.payload.getIntPayload(0);
    }

    public void setType(int type) {
        this.payload.setIntPayload(0,
                                   type);
    }

    public boolean getFirstTick() {
        return this.payload.getBoolPayload(0);
    }

    public void setFirstTick(boolean firstTick) {
        this.payload.setBoolPayload(0,
                                    firstTick);
    }

    @Override
    public boolean targetExists(World world) {
        return world.getTileEntity(this.xPosition,
                                   this.yPosition,
                                   this.zPosition) != null;
    }
}
