package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.core.WRCore;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class ClientEtherPacketRXAddExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketRedstoneEther packet, World world, EntityPlayer entityplayer) {
		TileEntity tileentity = packet.getTarget(world);
		if (
				tileentity != null && 
				tileentity instanceof TileEntityRedstoneWirelessR
		) {
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
/*		} else {
			tileentity = new TileEntityRedstoneWirelessR();
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
			world.setBlockTileEntity(
					packet.xPosition,
					packet.yPosition, 
					packet.zPosition, 
					tileentity
			);*/
		}
		RedstoneEther.getInstance().addReceiver(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq().toString()
		);
		((BlockRedstoneWireless)WRCore.blockWirelessR).setState(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getState()
		);
	}

}
