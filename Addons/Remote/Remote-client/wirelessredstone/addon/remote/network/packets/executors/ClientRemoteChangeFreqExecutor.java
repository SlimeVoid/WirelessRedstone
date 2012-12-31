package wirelessredstone.addon.remote.network.packets.executors;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessDevice;

public class ClientRemoteChangeFreqExecutor implements IDevicePacketExecutor {

	@Override
	public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
		if (p instanceof PacketWirelessDevice) {
			PacketWirelessDevice packet = (PacketWirelessDevice)p;
			IWirelessDeviceData data = packet.getDeviceData(WirelessRemoteData.class, world, entityplayer);
			data.setDeviceFreq(packet.getDeviceFreq());
			data.setDeviceState(packet.getDeviceState());
			Gui currentScreen = ModLoader.getMinecraftInstance().currentScreen;
			if (currentScreen instanceof GuiRedstoneWirelessDevice) {
				GuiRedstoneWirelessDevice gui = (GuiRedstoneWirelessDevice) currentScreen;
				gui.refreshGui();
			}
		}
	}
}