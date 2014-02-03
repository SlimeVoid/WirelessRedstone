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
package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.core.lib.BlockLib;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class ReceiverChangeFreqExecutor implements IPacketExecutor {

    @Override
    public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
        if (p instanceof PacketRedstoneEther) {
            PacketRedstoneEther packet = (PacketRedstoneEther) p;
            TileEntityRedstoneWireless tileentity = BlockLib.getBlockTileEntity(world,
                                                                                packet.xPosition,
                                                                                packet.yPosition,
                                                                                packet.zPosition);
            if (tileentity != null
                && tileentity instanceof TileEntityRedstoneWirelessR) {
                String freq = packet.getFreq();
                tileentity.setFreq(freq);
                tileentity.onInventoryChanged();
                ServerRedstoneEtherPacketHandler.sendEtherTileToAllInRange(tileentity,
                                                                           world,
                                                                           16);
            }

        }
    }
}