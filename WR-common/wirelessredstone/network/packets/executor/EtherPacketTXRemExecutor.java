package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

/**
 * Execute a remove transmitter command.<br>
 * <br>
 * Removes the transmitter from the ether.
 * 
 * @author ali4z
 */
public class EtherPacketTXRemExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		RedstoneEther.getInstance().remTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getDeviceFreq());
	}

}
