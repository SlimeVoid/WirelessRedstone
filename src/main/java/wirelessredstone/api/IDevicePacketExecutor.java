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
package wirelessredstone.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.network.packets.PacketWireless;

public interface IDevicePacketExecutor extends IPacketExecutor {
    /**
     * Execute the packet.
     * 
     * @param packet
     *            The redstone wireless device packet.
     * @param world
     *            The world object.
     * @param entityplayer
     *            the player
     */
    @Override
    public void execute(PacketWireless packet, World world, EntityPlayer entityplayer);
}
