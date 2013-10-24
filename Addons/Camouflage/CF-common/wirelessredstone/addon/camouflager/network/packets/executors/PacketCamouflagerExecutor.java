package wirelessredstone.addon.camouflager.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.api.ICamouflaged;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.addon.camouflager.network.packets.PacketCamouflagerCommands;
import wirelessredstone.addon.camouflager.network.packets.PacketCamouflagerSetRef;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketCamouflagerExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		TileEntity tileentity = packet.getTarget(world);
		if (tileentity != null
			&& tileentity instanceof TileEntityRedstoneWireless) {
			TileEntityRedstoneWireless tRW = (TileEntityRedstoneWireless) tileentity;
			if (packet.getCommand().equals(PacketCamouflagerCommands.camouflagerCommands.setBlockRef.toString())) {
				PacketCamouflagerSetRef packetSet = (PacketCamouflagerSetRef) packet;
				if (!(tRW instanceof ICamouflaged)) {
					NBTTagCompound tags = new NBTTagCompound();
					tRW.writeToNBT(tags);
					world.setBlockTileEntity(	packet.xPosition,
												packet.yPosition,
												packet.zPosition,
												CamouLib.createCamouflagedTile(tRW));
				}
				ICamouflaged camouTile = (ICamouflaged) world.getBlockTileEntity(	packet.xPosition,
																					packet.yPosition,
																					packet.zPosition);

				// camouTile.setBlockReference(packetSet.getRef
			}
		}
	}
}
