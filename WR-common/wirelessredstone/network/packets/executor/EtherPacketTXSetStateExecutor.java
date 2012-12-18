package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

/**
 * Execute a set transmitter state command.<br>
 * <br>
 * Sets the transmitter's state on the ether.
 * 
 * @author ali4z
 */
public class EtherPacketTXSetStateExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		RedstoneEther.getInstance().setTransmitterState(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq(),
				packet.getState());
	}

}
