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
package wirelessredstone.client.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.core.WRCore;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

import com.slimevoid.library.network.PacketUpdate;

public class ClientEtherPacketRXAddExecutor implements IEtherPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketWireless) {
            PacketWireless wireless = (PacketWireless) packet;
            TileEntity tileentity = wireless.getTarget(world);
            if (tileentity != null
                && tileentity instanceof TileEntityRedstoneWirelessR) {
                ((TileEntityRedstoneWireless) tileentity).setFreq(wireless.getFreq().toString());
                /*
                 * } else { tileentity = new TileEntityRedstoneWirelessR();
                 * ((TileEntityRedstoneWireless)
                 * tileentity).setFreq(packet.getFreq().toString());
                 * world.setBlockTileEntity( packet.xPosition, packet.yPosition,
                 * packet.zPosition, tileentity );
                 */
            }
            RedstoneEther.getInstance().addReceiver(world,
                                                    wireless.xPosition,
                                                    wireless.yPosition,
                                                    wireless.zPosition,
                                                    wireless.getFreq().toString());
            ((BlockRedstoneWireless) WRCore.blockWirelessR).setState(world,
                                                                     wireless.xPosition,
                                                                     wireless.yPosition,
                                                                     wireless.zPosition,
                                                                     wireless.getState());
        }
    }

}
