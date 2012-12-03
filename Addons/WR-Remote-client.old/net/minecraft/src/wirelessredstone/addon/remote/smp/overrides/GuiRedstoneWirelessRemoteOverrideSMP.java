package net.minecraft.src.wirelessredstone.addon.remote.smp.overrides;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.PacketHandlerWirelessRemote.PacketHandlerOutput;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessDeviceOverride;

public class GuiRedstoneWirelessRemoteOverrideSMP implements
		GuiRedstoneWirelessDeviceOverride {

	@Override
	public boolean beforeFrequencyChange(WirelessDeviceData data,
			Object oldFreq, Object newFreq) {
		if (WirelessRedstone.getWorld().isRemote) {
			int OLD = Integer.parseInt(oldFreq.toString());
			int NEW = Integer.parseInt(newFreq.toString());
			if (OLD != NEW)
				PacketHandlerOutput.sendWirelessRemotePacket("changeFreq",
						data.getID(), (NEW - OLD));
		}
		return false;
	}
}
