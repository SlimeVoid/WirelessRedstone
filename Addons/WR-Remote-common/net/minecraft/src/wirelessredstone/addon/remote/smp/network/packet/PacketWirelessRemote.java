package net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWireless;

public class PacketWirelessRemote extends PacketWireless {
	public PacketWirelessRemote(int packetId) {
		super(packetId);
		this.channel = "WIFI-REMOTE";
	}
}
