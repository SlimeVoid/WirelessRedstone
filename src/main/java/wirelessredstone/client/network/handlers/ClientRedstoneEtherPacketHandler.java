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
package wirelessredstone.client.network.handlers;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketRedstoneEther;

import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.library.network.handlers.SubPacketHandler;
import com.slimevoid.library.util.helpers.PacketHelper;

public class ClientRedstoneEtherPacketHandler extends SubPacketHandler {

    @Override
    protected PacketUpdate createNewPacket() {
        return new PacketRedstoneEther();
    }

    public static void sendRedstoneEtherPacket(String command, int i, int j, int k, Object freq, boolean state) {
        PacketRedstoneEther packet = new PacketRedstoneEther(command);
        packet.setPosition(i,
                           j,
                           k,
                           0);
        packet.setFreq(freq);
        packet.setState(state);
        LoggerRedstoneWireless.getInstance("ClientRedstoneEtherPacketHandler").write(true,
                                                                                     "sendRedstoneEtherPacket("
                                                                                             + packet.toString()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);
        PacketHelper.sendToServer(packet);
    }
}
