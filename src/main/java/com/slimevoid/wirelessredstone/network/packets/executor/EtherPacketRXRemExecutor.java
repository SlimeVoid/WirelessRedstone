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
import net.minecraft.world.World;

import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.wirelessredstone.api.IEtherPacketExecutor;
import com.slimevoid.wirelessredstone.ether.RedstoneEther;
import com.slimevoid.wirelessredstone.network.packets.PacketWireless;

/**
 * Execute a remove receiver command.<br>
 * <br>
 * Removes the receiver from the ether.
 * 
 * @author ali4z
 */
public class EtherPacketRXRemExecutor implements IEtherPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        RedstoneEther.getInstance().remReceiver(world,
                                                packet.xPosition,
                                                packet.yPosition,
                                                packet.zPosition,
                                                ((PacketWireless) packet).getFreq());
    }

}
