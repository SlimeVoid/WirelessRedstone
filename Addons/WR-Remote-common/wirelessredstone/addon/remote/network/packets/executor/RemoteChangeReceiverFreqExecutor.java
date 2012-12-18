package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.executor.EtherPacketChangeFreqExecutor;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class RemoteChangeReceiverFreqExecutor extends EtherPacketChangeFreqExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		if (entityplayer.getHeldItem().getItemName().equals(WRemoteCore.itemRemote.getItemName())) {
			// Fetch the tile from the packet
			TileEntity entity = packet.getTarget(world);
	
			if (entity instanceof TileEntityRedstoneWirelessR) {
				// Assemble frequencies.
				IWirelessDeviceData devicedata = WirelessDeviceData.getDeviceData(WirelessRemoteData.class, "Wireless Remote", entityplayer.getHeldItem(), world, entityplayer);

				// Set the frequency to the tile
				((TileEntityRedstoneWirelessR) entity).setFreq(devicedata.getDeviceFreq());
				entity.onInventoryChanged();
	
				// Makr the block for update with the world.
				world.markBlockForRenderUpdate(
						packet.xPosition,
						packet.yPosition,
						packet.zPosition);
	
				// Broadcast change to all clients.
				ServerRedstoneEtherPacketHandler.sendEtherTileToAll(
						(TileEntityRedstoneWirelessR) entity,
						world);
			}
		}
	}
}
