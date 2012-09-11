package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientTilePacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			PacketWirelessTile pORW = new PacketWirelessTile();
			pORW.readData(data);
			handlePacket(pORW, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handlePacket(PacketWirelessTile packet, World world, EntityPlayer player ) {
		LoggerRedstoneWireless.getInstance("ClientTilePacketHandler").write(
				"handlePacket:" + packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		TileEntity tileentity = packet.getTarget(world);
		if (packet.getCommand() == PacketRedstoneWirelessCommands.fetchTile.getCommand()) {
			handleFetchTile(packet,tileentity);
		}
	}
	private void handleFetchTile(PacketWirelessTile packet, TileEntity tileentity) {
		if (
				tileentity != null && 
				tileentity instanceof TileEntityRedstoneWireless
		) {
			TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless) tileentity;
			tileentityredstonewireless.handleData(packet);

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
