package wirelessredstone.network.handlers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ClientTilePacketHandler extends SubPacketHandler {
	
	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketWirelessTile();
	}
	
	@Override
	protected void handlePacket(PacketWireless packet, World world, EntityPlayer player ) {
		LoggerRedstoneWireless.getInstance(
				"ClientTilePacketHandler"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		TileEntity tileentity = packet.getTarget(world);
		if (packet.getCommand().equals(PacketRedstoneWirelessCommands.wirelessCommands.fetchTile.toString())) {
			handleFetchTile(packet,tileentity);
		}
	}
	
	private void handleFetchTile(PacketWireless packet, TileEntity tileentity) {
		if (
				tileentity != null && 
				tileentity instanceof TileEntityRedstoneWireless
		) {
			TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless) tileentity;
			tileentityredstonewireless.handleData((PacketWirelessTile)packet);

			GuiScreen screen = ModLoader.getMinecraftInstance().currentScreen;
			if (
					screen != null && 
					screen instanceof GuiRedstoneWireless &&
					screen instanceof GuiRedstoneWirelessInventory && 
					((GuiRedstoneWirelessInventory) screen).compareInventory(tileentityredstonewireless)
			)
				((GuiRedstoneWireless) screen).refreshGui();
		}
	}
}
