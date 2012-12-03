package net.minecraft.src.wirelessredstone.addon.remote.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;


import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteOpenGui;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteSettings;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class NetworkConnection implements INetworkConnections {

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = WirelessRedstone.getWorld(network);
			EntityPlayer entityplayer = WirelessRedstone.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ADDON:
				PacketWirelessRemoteSettings pWR = new PacketWirelessRemoteSettings();
				pWR.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pWR, world, entityplayer);
				break;
			case PacketIds.GUI:
				PacketWirelessRemoteOpenGui pRG = new PacketWirelessRemoteOpenGui();
				pRG.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pRG, world, entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login) {
		MessageManager.getInstance().registerChannel(network, this,
				"WIFI-REMOTE");
		ModLoader.getLogger().fine(
				"Wireless Redstone : Remote Registered for - "
						+ WirelessRedstone.getPlayer(network).username);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}
}