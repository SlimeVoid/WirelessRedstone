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

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.slimevoid.library.network.PacketPayload;
import net.slimevoid.wirelessredstone.api.IWirelessData;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless.LogLevel;
import net.slimevoid.wirelessredstone.ether.RedstoneEther;
import net.slimevoid.wirelessredstone.network.packets.core.PacketIds;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketWirelessTile extends PacketWireless implements IWirelessData {
    public PacketWirelessTile() {
        super(PacketIds.TILE);
    }

    public PacketWirelessTile(String command, TileEntityRedstoneWireless entity) {
        super(PacketIds.TILE, new PacketPayload(0, 0, 1, 13));
        this.setPosition(entity.getBlockCoord(0),
                         entity.getBlockCoord(1),
                         entity.getBlockCoord(2),
                         0);
        LoggerRedstoneWireless.getInstance("PacketWirelessTile").write(entity.getWorld().isRemote,
                                                                       this.getCommand()
                                                                               + " - ("
                                                                               + this.xPosition
                                                                               + ","
                                                                               + this.yPosition
                                                                               + ","
                                                                               + this.zPosition
                                                                               + ")",
                                                                       LogLevel.INFO);
        this.setCommand(command);
        this.setFreq(entity.getFreq());
        this.setState(RedstoneEther.getInstance().getFreqState(entity.getWorld(),
                                                               entity.getFreq()));
        this.setPowerDirections(entity.getPowerDirections());
        this.setInDirectlyPowering(entity.getInDirectlyPowering());
    }

    @Override
    public String toString() {
        return this.getCommand() + "(" + xPosition + "," + yPosition + ","
               + zPosition + ")[" + this.getFreq() + "]";
    }

    public boolean[] getPowerDirections() {
        int j = this.payload.getBoolSize() - 12;
        boolean[] dir = new boolean[6];
        for (int i = 0; i < 6; i++) {
            dir[i] = this.payload.getBoolPayload(j);
            j++;
        }
        return dir;
    }

    public void setPowerDirections(boolean[] dir) {
        int j = this.payload.getBoolSize() - 12;
        for (int i = 0; i < 6; i++) {
            this.payload.setBoolPayload(j,
                                        dir[i]);
            j++;
        }
    }

    public boolean[] getInDirectlyPowering() {
        int j = this.payload.getBoolSize() - 6;
        boolean[] indir = new boolean[6];
        for (int i = 0; i < 6; i++) {
            indir[i] = this.payload.getBoolPayload(j);
            j++;
        }
        return indir;
    }

    public void setInDirectlyPowering(boolean[] indir) {
        int j = this.payload.getBoolSize() - 6;
        for (int i = 0; i < 6; i++) {
            this.payload.setBoolPayload(j,
                                        indir[i]);
            j++;
        }
    }

    @Override
    public boolean targetExists(World world) {
        return world.getTileEntity(new BlockPos(this.xPosition,
                this.yPosition,
                this.zPosition)) != null;
    }
}
