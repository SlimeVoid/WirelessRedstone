package wirelessredstone.network.packets.executor;

import net.minecraft.src.World;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneEther;

/**
 * Execute a add transmitter command.<br>
 * <br>
 * Adds the transmitter to the ether.
 * 
 * @author ali4z
 */
public class EtherPacketTXAddExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().addTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

}
