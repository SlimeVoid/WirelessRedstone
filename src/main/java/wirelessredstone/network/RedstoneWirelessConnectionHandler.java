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
package wirelessredstone.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import wirelessredstone.core.WirelessRedstone;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Connection handler for Wireless Redstone.
 * 
 * @author Eurymachus
 */
public class RedstoneWirelessConnectionHandler implements IConnectionHandler {

    /**
     * Called when a player logs into the server SERVER SIDE.<br>
     * Initializes packetHandlers (for integrated Servers).
     */
    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
        // TODO Auto-generated method stub

    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
        // TODO Auto-generated method stub

    }

    @Override
    public void connectionClosed(INetworkManager manager) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
        WirelessRedstone.proxy.login(clientHandler,
                                     manager,
                                     login);
    }

}
