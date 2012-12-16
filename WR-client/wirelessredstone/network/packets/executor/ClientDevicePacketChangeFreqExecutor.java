package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Gui;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessDevice;

public class ClientDevicePacketChangeFreqExecutor implements IDevicePacketExecutor {

	@Override
	public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
		if (p instanceof PacketWirelessDevice) {
			PacketWirelessDevice packet = (PacketWirelessDevice)p;
			IWirelessDeviceData data = packet.getDeviceData(world, entityplayer);
			data.setFreq(packet.getFreq());
			data.setState(packet.getState());
			Gui currentScreen = ModLoader.getMinecraftInstance().currentScreen;
			if (currentScreen instanceof GuiRedstoneWirelessDevice) {
				GuiRedstoneWirelessDevice gui = (GuiRedstoneWirelessDevice) currentScreen;
				gui.refreshGui();
			}
		}
	}
}
