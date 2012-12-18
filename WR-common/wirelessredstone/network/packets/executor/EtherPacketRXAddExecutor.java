package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

/**
 * Execute a add receiver command.<br>
 * <br>
 * Adds the receiver to the ether.
 * 
 * @author ali4z
 */
public class EtherPacketRXAddExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		RedstoneEther.getInstance().addReceiver(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getDeviceFreq());
	}

}
