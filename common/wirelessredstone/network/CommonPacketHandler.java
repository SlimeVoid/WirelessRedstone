package wirelessredstone.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.ether.RedstoneEtherNode;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketUpdate;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class CommonPacketHandler implements IPacketHandler {

	private static Map<Integer,IPacketHandler> commonHandlers;
	
	public CommonPacketHandler() {
		commonHandlers = new HashMap<Integer,IPacketHandler>();
	}
	
	public static void reigsterPacketHandler(int packetID, IPacketHandler handler) {
		commonHandlers.put(packetID, handler);
	}
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			
			if ( commonHandlers.containsKey(packetID) )
				commonHandlers.get(packetID).onPacketData(manager, packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	
	
	public static class PacketHandlerOutput {
		private static class PacketHandlerOutputSender {
			private int delay;
			private EntityPlayer player;
			private PacketUpdate packet;

			public PacketHandlerOutputSender(EntityPlayer player,
					PacketUpdate packet, int delay) {
				this(packet, delay);
				this.player = player;
			}

			public PacketHandlerOutputSender(PacketUpdate packet, int delay) {
				this.delay = delay;
				this.packet = packet;
			}

			public void send() {
				if (player == null) {
					CommonPacketHandler.sendToAll(packet);
				} else {
					if (player instanceof EntityClientPlayerMP) {
						((EntityClientPlayerMP)player)
							.sendQueue
							.addToSendQueue(packet.getPacket());
					} else if (player instanceof EntityPlayerMP) {
						((EntityPlayerMP)player)
							.serverForThisPlayer
							.theNetworkManager
							.addToSendQueue(packet.getPacket());
					}
				}
			}
		}

		public static void sendGuiPacketTo(EntityPlayer player,
				TileEntityRedstoneWireless entity) {
			PacketRedstoneWirelessOpenGui packet = new PacketRedstoneWirelessOpenGui(
					entity);

			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendGuiPacketTo:" + player.username,
					LoggerRedstoneWireless.LogLevel.DEBUG);

			(new PacketHandlerOutputSender(player, packet, 0)).send();
		}

		public static void sendEtherTileToAll(
				TileEntityRedstoneWireless entity, World world, int delay) {
			PacketRedstoneEther packet = CommonPacketHandler.prepareRedstoneEtherPacket(entity,
					world);

			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendEtherTileToAll:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			(new PacketHandlerOutputSender(packet, delay)).send();
		}

		public static void sendEtherTileTo(EntityPlayer entityplayermp,
				TileEntityRedstoneWireless entity, World world, int delay) {
			PacketRedstoneEther packet = CommonPacketHandler.prepareRedstoneEtherPacket(entity,
					world);

			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendEtherTileTo:" + entityplayermp.username + ":"
							+ packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);

			(new PacketHandlerOutputSender(entityplayermp, packet, delay))
					.send();
		}

		public static void sendWirelessTileToAll(
				TileEntityRedstoneWireless tileentity, World world, int delay) {
			PacketWirelessTile packet = prepareWirelessTileEntityPacket(
					tileentity, world);
			CommonPacketHandler.sendToAll(packet);
		}

		public static void sendEtherNodeTileToAll(RedstoneEtherNode node,
				int delay) {
			World world = ModLoader.getMinecraftServerInstance()
					.worldServerForDimension(0);
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWireless) {
				CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWireless) entity, world,
						delay);
			}
		}

		public static void sendEtherTilesTo(EntityPlayer entityplayermp,
				int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendEtherTilesTo" + entityplayermp.username,
					LoggerRedstoneWireless.LogLevel.DEBUG);

			List<RedstoneEtherNode> list = RedstoneEther.getInstance()
					.getRXNodes();
			PacketRedstoneEther packet;
			World world = ModLoader.getMinecraftServerInstance()
					.worldServerForDimension(0);
			for (RedstoneEtherNode node : list) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessR) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileTo(entityplayermp,
							(TileEntityRedstoneWirelessR) entity, world, delay);
				}
			}

			list = RedstoneEther.getInstance().getTXNodes();
			for (RedstoneEtherNode node : list) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessT) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileTo(entityplayermp,
							(TileEntityRedstoneWirelessT) entity, world, delay);
				}
			}
		}

		public static void sendEtherTilesToAll(int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendEtherTilesToAll",
					LoggerRedstoneWireless.LogLevel.DEBUG);

			List<RedstoneEtherNode> list = RedstoneEther.getInstance()
					.getRXNodes();
			PacketRedstoneEther packet;

			World world = ModLoader.getMinecraftServerInstance()
					.worldServerForDimension(0);
			for (RedstoneEtherNode node : list) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessR) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWirelessR) entity,
							world, delay);
				}
			}

			list = RedstoneEther.getInstance().getTXNodes();
			for (RedstoneEtherNode node : list) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessT) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWirelessT) entity,
							world, delay);
				}
			}
		}

		public static void sendEtherFrequencyTilesToAll(
				List<RedstoneEtherNode> txs, List<RedstoneEtherNode> rxs,
				int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendEtherFrequencyTilesToAll",
					LoggerRedstoneWireless.LogLevel.DEBUG);

			World world = ModLoader.getMinecraftServerInstance()
					.worldServerForDimension(0);
			for (RedstoneEtherNode node : rxs) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessR) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWirelessR) entity,
							world, delay);
				}
			}

			for (RedstoneEtherNode node : txs) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j,
						node.k);
				if (entity instanceof TileEntityRedstoneWirelessT) {
					CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWirelessT) entity,
							world, delay);
				}
			}
		}
	}

	private static PacketRedstoneEther prepareRedstoneEtherPacket(
			TileEntityRedstoneWireless entity, World world) {
		PacketRedstoneEther out = new PacketRedstoneEther(entity, world);
		return out;
	}

	private static PacketWirelessTile prepareWirelessTileEntityPacket(
			TileEntityRedstoneWireless tileentity, World world) {
		PacketWirelessTile out = new PacketWirelessTile("fetchTile", tileentity);
		return out;
	}

	public static void sendToAll(PacketUpdate packet) {
		sendToAllWorlds(null, packet.getPacket(), packet.xPosition,
				packet.yPosition, packet.zPosition, true);
	}

	public static void sendToAllWorlds(EntityPlayer entityplayer,
			Packet packet, int x, int y, int z, boolean sendToPlayer) {
		World[] worlds = DimensionManager.getWorlds();
		for (int i = 0; i < worlds.length; i++) {
			sendToAllPlayers(worlds[i], entityplayer, packet, x, y, z,
					sendToPlayer);
		}
	}

	public static void sendToAllPlayers(World world, EntityPlayer entityplayer,
			Packet packet, int x, int y, int z, boolean sendToPlayer) {
		for (int j = 0; j < world.playerEntities.size(); j++) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) world.playerEntities
					.get(j);
			boolean shouldSendToPlayer = true;
			if (entityplayer != null) {
				if (entityplayer.username.equals(entityplayermp.username)
						&& !sendToPlayer)
					shouldSendToPlayer = false;
			}
			if (shouldSendToPlayer) {
				if (Math.abs(entityplayermp.posX - x) <= 16
						&& Math.abs(entityplayermp.posY - y) <= 16
						&& Math.abs(entityplayermp.posZ - z) <= 16)
					entityplayermp.serverForThisPlayer.theNetworkManager
							.addToSendQueue(packet);
			}
		}
	}
}
