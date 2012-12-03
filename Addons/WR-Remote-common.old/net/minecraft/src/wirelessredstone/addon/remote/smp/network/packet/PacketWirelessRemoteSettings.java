package net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessRemoteSettings extends PacketWirelessRemote {
	public PacketWirelessRemoteSettings() {
		super(PacketIds.ADDON);
	}

	public PacketWirelessRemoteSettings(String command) {
		this();
		this.payload = new PacketPayload(1, 0, 2, 1);
		this.setCommand(command);
	}

	public void setRemoteID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public int getRemoteID() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public String toString() {
		return "Freq[" + this.getFreq() + "](" + this.xPosition + ","
				+ this.yPosition + "," + this.zPosition + ")";
	}
}
