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
package net.slimevoid.wirelessredstone.network.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneWirelessOpenGuiInventory;
import net.slimevoid.wirelessredstone.network.packets.PacketWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * A server-side GUI packet sub-handler.
 * 
 * @author ali4z
 */
public class GuiPacketHandler {

    //@Override
    //protected PacketWireless createNewPacket() {
    //    return new PacketRedstoneWirelessOpenGuiInventory();
    //}

    /**
     * Send a GUI packet to specified player.
     * 
     * @param player
     *            Receiving player.
     * @param data
     *            the packet to send.
     */
    public static void sendGuiPacketTo(EntityPlayerMP player, TileEntityRedstoneWireless data) {
        // Assemble a OpenGUI packet.

        LoggerRedstoneWireless.getInstance("ServerGuiPacketHandler").write(false,
                                                                           "sendGuiPacketTo("
                                                                                   + player.getDisplayName()
                                                                                   + ", entity)",
                                                                           LoggerRedstoneWireless.LogLevel.DEBUG);

        PacketRedstoneWirelessOpenGuiInventory packet = new PacketRedstoneWirelessOpenGuiInventory(data);
        // Send the packet.
        PacketHelper.sendToPlayer(packet,
                                  player);
    }
}
