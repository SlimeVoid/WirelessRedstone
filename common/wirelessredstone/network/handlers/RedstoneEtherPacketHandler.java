package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.CommonPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class RedstoneEtherPacketHandler implements IPacketHandler {

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
			CommonPacketHandler.PacketHandlerOutput.sendEtherTileToAll(
					(TileEntityRedstoneWireless) entity, 
					world, 
					0
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
}
