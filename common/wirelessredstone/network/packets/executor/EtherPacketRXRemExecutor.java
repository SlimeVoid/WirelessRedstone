package wirelessredstone.network.packets.executor;

import net.minecraft.src.World;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneEther;

/**
 * Execute a remove receiver command.<br>
 * <br>
 * Removes the receiver from the ether.
 * 
 * @author ali4z
 */
public class EtherPacketRXRemExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().remReceiver(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

}
