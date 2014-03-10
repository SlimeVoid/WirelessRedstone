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
package com.slimevoid.wirelessredstone.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.wirelessredstone.api.IEtherPacketExecutor;
import com.slimevoid.wirelessredstone.network.handlers.RedstoneEtherPacketHandler;
import com.slimevoid.wirelessredstone.network.packets.PacketWireless;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Execute a change frequency command.<br>
 * <br>
 * Changes the frequency for a node, updates said node then broadcasts the
 * change to all clients.
 * 
 * @author ali4z
 */
public class EtherPacketChangeFreqExecutor implements IEtherPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        // Fetch the tile from the packet
        TileEntity entity = ((PacketWireless) packet).getTarget(world);

        if (entity instanceof TileEntityRedstoneWireless) {
            // Assemble frequencies.
            int dFreq = Integer.parseInt(((PacketWireless) packet).getFreq());
            int oldFreq = Integer.parseInt(((TileEntityRedstoneWireless) entity).getFreq().toString());

            // Set the frequency to the tile
            ((TileEntityRedstoneWireless) entity).setFreq(Integer.toString(oldFreq
                                                                           + dFreq));
            entity.markDirty();

            // Makr the block for update with the world.
            world.markBlockForUpdate(packet.xPosition,
                                     packet.yPosition,
                                     packet.zPosition);

            // Broadcast change to all clients.
            RedstoneEtherPacketHandler.sendEtherTileToAll((TileEntityRedstoneWireless) entity,
                                                                world);
        }
    }
}
