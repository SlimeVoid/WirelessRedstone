package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.ether.RedstoneEtherNode;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerRedstoneEtherPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			PacketRedstoneEther pRE = new PacketRedstoneEther();
			pRE.readData(data);
			handlePacket(pRE, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handlePacket(PacketRedstoneEther packet, World world, EntityPlayer player ) {
		LoggerRedstoneWireless.getInstance("RedstoneEtherPacketHandler").write(
				"handlePacket:" + 
				((EntityPlayer)player).username + ":" + 
				packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		if (packet.getCommand().equals("changeFreq")) {
			handleChangeFreq(packet,world);
		} else if (packet.getCommand().equals("addTransmitter")) {
			handleAddTransmitter(packet,world);
		} else if (packet.getCommand().equals("setTransmitterState")) {
			handleSetTransmitterState(packet,world);
		} else if (packet.getCommand().equals("remTransmitter")) {
			handleRemTransmitter(packet,world);
		} else {
			LoggerRedstoneWireless.getInstance("RedstoneEtherPacketHandler").write(
					"handlePacket:" + 
					((EntityPlayer)player).username + ":" + 
					packet.toString() + "UNKNOWN COMMAND",
					LoggerRedstoneWireless.LogLevel.WARNING
			);
		}
	}

	private void handleChangeFreq(PacketRedstoneEther packet, World world) {
		TileEntity entity = packet.getTarget(world);

		if (entity instanceof TileEntityRedstoneWireless) {
			int dFreq = Integer.parseInt(packet.getFreq());
			int oldFreq = Integer.parseInt(((TileEntityRedstoneWireless) entity).getFreq().toString());

			((TileEntityRedstoneWireless) entity).setFreq(Integer.toString(oldFreq + dFreq));
			entity.onInventoryChanged();
			world.markBlockNeedsUpdate(
					packet.xPosition,
					packet.yPosition, 
					packet.zPosition
			);
			
			sendEtherTileToAll(
					(TileEntityRedstoneWireless) entity, 
					world
			);
		}
	}
	private void handleAddTransmitter(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().addTransmitter(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq()
		);
	}
	
	private void handleSetTransmitterState(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().setTransmitterState(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq(), 
				packet.getState()
		);
	}
	
	private void handleRemTransmitter(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().remTransmitter(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq()
		);
	}
	
	

	public static void sendEtherTileToAll(TileEntityRedstoneWireless entity, World world) {
		PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTileToAll:" + packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload) packet.getPacket());
	}

	public static void sendEtherTileTo(EntityPlayerMP entityplayermp, TileEntityRedstoneWireless entity, World world) {
		PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTileTo:" + entityplayermp.username + ":"+ packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		ServerPacketHandler.sendPacketTo(
				entityplayermp, 
				(Packet250CustomPayload) packet.getPacket()
		);
	}
	
	public static void sendEtherNodeTileToAll(RedstoneEtherNode node) {
		World world = ModLoader.getMinecraftServerInstance().worldServerForDimension(0);
		TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
		if (entity instanceof TileEntityRedstoneWireless) {
			sendEtherTileToAll((TileEntityRedstoneWireless) entity, world);
		}
	}
	
	public static void sendEtherTilesTo(EntityPlayerMP entityplayermp) {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTilesTo" + entityplayermp.username,
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
		PacketRedstoneEther packet;
		World world = ModLoader.getMinecraftServerInstance().worldServerForDimension(0);
		
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileTo(
						entityplayermp,
						(TileEntityRedstoneWirelessR) entity, 
						world
				);
			}
		}

		list = RedstoneEther.getInstance().getTXNodes();
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileTo(
						entityplayermp,
						(TileEntityRedstoneWirelessT) entity, 
						world
				);
			}
		}
	}

	public static void sendEtherTilesToAll() {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTilesToAll",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
		PacketRedstoneEther packet;
		World world = ModLoader.getMinecraftServerInstance().worldServerForDimension(0);
		
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileToAll(
						(TileEntityRedstoneWirelessR) entity,
						world
				);
			}
		}

		list = RedstoneEther.getInstance().getTXNodes();
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileToAll(
						(TileEntityRedstoneWirelessT) entity,
						world
				);
			}
		}
	}

	public static void sendEtherFrequencyTilesToAll(List<RedstoneEtherNode> txs, List<RedstoneEtherNode> rxs) {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherFrequencyTilesToAll",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		World world = ModLoader.getMinecraftServerInstance().worldServerForDimension(0);
		
		for (RedstoneEtherNode node : rxs) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileToAll(
						(TileEntityRedstoneWirelessR) entity,
						world
				);
			}
		}

		for (RedstoneEtherNode node : txs) {
			TileEntity entity = world.getBlockTileEntity(
					node.i, 
					node.j,
					node.k
			);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileToAll(
						(TileEntityRedstoneWirelessT) entity,
						world
				);
			}
		}
	}
}
