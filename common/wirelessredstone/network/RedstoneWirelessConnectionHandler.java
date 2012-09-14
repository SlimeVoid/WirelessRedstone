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

import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import cpw.mods.fml.common.FMLCommonHandler;
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
	 * Sends ether tiles to the player.
	 */
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, NetworkManager manager) {
		if (player instanceof EntityPlayerMP) {
			System.out.println("Side: " + FMLCommonHandler
					.instance()
						.getSide()
						.toString());
			EntityPlayerMP entityplayermp = (EntityPlayerMP) player;
			ServerRedstoneEtherPacketHandler.sendEtherTilesTo(entityplayermp);
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, NetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, NetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub

	}

}
