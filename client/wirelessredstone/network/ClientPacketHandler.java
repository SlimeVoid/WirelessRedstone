/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package wirelessredstone.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketUpdate;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler extends CommonPacketHandler {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketRedstoneWirelessOpenGui) {
			ClientPacketHandler.PacketHandlerInput.openGUI((PacketRedstoneWirelessOpenGui) packet,
					world, entityplayer);
		} else if (packet instanceof PacketRedstoneEther) {
			ClientPacketHandler.PacketHandlerInput.handleEtherPacket((PacketRedstoneEther) packet,
					world, entityplayer);
		} else if (packet instanceof PacketWirelessTile) {
			ClientPacketHandler.PacketHandlerInput.handleTilePacket((PacketWirelessTile) packet,
					world, entityplayer);
		}
	}

	private static class PacketHandlerInput {
		private static void openGUI(PacketRedstoneWirelessOpenGui packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"openGUI:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity tileentity = packet.getTarget(world);
			WRCore.proxy.activateGUI(world, entityplayer, tileentity);
		}

		private static void handleTilePacket(PacketWirelessTile packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleTilePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(world);
			if (packet.getCommand().equals("fetchTile")) {
				if (tileentity != null
						&& tileentity instanceof TileEntityRedstoneWireless) {

					TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless) tileentity;
					tileentityredstonewireless.handleData(packet);

					GuiScreen screen = ModLoader.getMinecraftInstance().currentScreen;
					if (screen != null && screen instanceof GuiRedstoneWireless) {
						if (screen instanceof GuiRedstoneWirelessInventory) {
							if (((GuiRedstoneWirelessInventory) screen)
									.compareInventory(tileentityredstonewireless)) {
								((GuiRedstoneWireless) screen).refreshGui();
							}
						}
					}
				}
			}
		}

		private static void handleEtherPacket(PacketRedstoneEther packet,
				World world, EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			TileEntity tileentity = packet.getTarget(world);
			if (packet.getCommand().equals("addTransmitter")) {
				if (tileentity != null
						&& tileentity instanceof TileEntityRedstoneWirelessT) {
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
				} else {
					tileentity = new TileEntityRedstoneWirelessT();
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
					world.setBlockTileEntity(packet.xPosition,
							packet.yPosition, packet.zPosition, tileentity);

				}
				RedstoneEther.getInstance().addTransmitter(world,
						packet.xPosition, packet.yPosition, packet.zPosition,
						packet.getFreq().toString());

			} else if (packet.getCommand().equals("addReceiver")) {
				if (tileentity != null
						&& tileentity instanceof TileEntityRedstoneWirelessR) {
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
				} else {
					tileentity = new TileEntityRedstoneWirelessR();
					((TileEntityRedstoneWireless) tileentity).setFreq(packet
							.getFreq().toString());
					world.setBlockTileEntity(packet.xPosition,
							packet.yPosition, packet.zPosition, tileentity);
				}
				RedstoneEther.getInstance().addReceiver(world,
						packet.xPosition, packet.yPosition, packet.zPosition,
						packet.getFreq().toString());
			}
		}
	}

	public static class PacketHandlerOutput {
		public static void sendRedstoneEtherPacket(String command, int i,
				int j, int k, Object freq, boolean state) {
			PacketRedstoneEther packet = new PacketRedstoneEther(command);
			packet.setPosition(i, j, k, 0);
			packet.setFreq(freq);
			packet.setState(state);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendRedstoneEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}

	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		if (!world.isRemote) { 
			super.onPacketData(manager, packet, player);
			return;
		}
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ETHER:
				PacketRedstoneEther pRE = new PacketRedstoneEther();
				pRE.readData(data);
				ClientPacketHandler.handlePacket(pRE, world, entityplayer);
				break;
			case PacketIds.GUI:
				PacketRedstoneWirelessOpenGui pORW = new PacketRedstoneWirelessOpenGui();
				pORW.readData(data);
				ClientPacketHandler.handlePacket(pORW, world, entityplayer);
				break;
			case PacketIds.TILE:
				PacketWirelessTile pWT = new PacketWirelessTile();
				pWT.readData(data);
				ClientPacketHandler.handlePacket(pWT, world, entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
