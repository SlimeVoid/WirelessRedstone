package wirelessredstone.network.packets.executor;

import net.minecraft.src.World;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneEther;

/**
 * Execute a remove transmitter command.<br>
 * <br>
 * Removes the transmitter from the ether.
 * 
 * @author ali4z
 */
public class EtherPacketTXRemExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().remTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

}
