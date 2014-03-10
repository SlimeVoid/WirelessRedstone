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
package com.slimevoid.wirelessredstone.network.packets;

import net.minecraft.world.World;

import com.slimevoid.library.network.PacketPayload;
import com.slimevoid.wirelessredstone.block.BlockRedstoneWireless;
import com.slimevoid.wirelessredstone.core.WRCore;
import com.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import com.slimevoid.wirelessredstone.network.packets.core.PacketIds;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Used to send Redstone Ether packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneEther extends PacketWireless {
    public PacketRedstoneEther() {
        super(PacketIds.ETHER);
    }

    public PacketRedstoneEther(String command) {
        super(PacketIds.ETHER, new PacketPayload(0, 0, 1, 1));
        setCommand(command);
    }

    public PacketRedstoneEther(TileEntityRedstoneWireless entity, World world) {
        super(PacketIds.ETHER, new PacketPayload(0, 0, 1, 1));
        this.setPosition(entity.getBlockCoord(0),
                         entity.getBlockCoord(1),
                         entity.getBlockCoord(2),
                         0);
        if (entity instanceof TileEntityRedstoneWirelessR) {
            setCommand(PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString());
            setState(((BlockRedstoneWireless) WRCore.blockWirelessR).getState(world,
                                                                              this.xPosition,
                                                                              this.yPosition,
                                                                              this.zPosition));
        } else if (entity instanceof TileEntityRedstoneWirelessT) {
            setCommand(PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString());
            setState(((BlockRedstoneWireless) WRCore.blockWirelessT).getState(world,
                                                                              this.xPosition,
                                                                              this.yPosition,
                                                                              this.zPosition));
        }
        setFreq(entity.getFreq());
    }

    @Override
    public String toString() {
        return this.getCommand() + "(" + xPosition + "," + yPosition + ","
               + zPosition + ") [" + this.getFreq() + "] - " + this.getState();
    }

    @Override
    public void setState(boolean state) {
        this.payload.setBoolPayload(0,
                                    state);
        LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(true,
                                                                        "setState("
                                                                                + state
                                                                                + ")",
                                                                        LoggerRedstoneWireless.LogLevel.DEBUG);
    }

    @Override
    public boolean getState() {
        return this.payload.getBoolPayload(0);
    }

    @Override
    public boolean targetExists(World world) {
        return world.getTileEntity(this.xPosition,
                                   this.yPosition,
                                   this.zPosition) != null;
    }
}
