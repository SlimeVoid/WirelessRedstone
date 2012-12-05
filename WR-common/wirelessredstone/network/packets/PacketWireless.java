package wirelessredstone.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.network.packets.core.PacketPayload;
import wirelessredstone.network.packets.core.PacketUpdate;

/**
 * Extend for new packets
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketWireless extends PacketUpdate {

	private String command;

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeUTF(this.command);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.command = data.readUTF();
	}

	/**
	 * Constructor for Default Wireless Packets
	 * 
	 * @param packetId the packet ID used to identify the type of packet data
	 *            being sent or received
	 */
	public PacketWireless(int packetId) {
		super(packetId);
		this.setChannel("WR");
	}

	/**
	 * Constructor for Default Wireless Packets Used to add payload data to the
	 * packet
	 * 
	 * @param packetId the packet ID used to identify the type of packet data
	 *            being sent or received
	 * @param payload the new payload to be associated with the packet
	 */
	public PacketWireless(int packetId, PacketPayload payload) {
		super(packetId, payload);
		this.setChannel("WR");
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + "," + zPosition + ")[" + this
				.getFreq() + "]";
	}

	/**
	 * Retrieves the command String corresponding to the executor
	 * 
	 * @return Returns command
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * Sets the command in the packet
	 * 
	 * @param command The command to be added
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Retrieves the frequency from the payload Override to change the index
	 * position
	 * 
	 * @return Returns getStringPayload(0) by default
	 */
	public String getFreq() {
		return this.payload.getStringPayload(0);
	}

	/**
	 * Sets the command in the payload Override to change the index position
	 * 
	 * @param freq The command to be added
	 */
	public void setFreq(Object freq) {
		this.payload.setStringPayload(0, freq.toString());
	}

	/**
	 * Retrieves the frequency from the payload Override to change the index
	 * position
	 * 
	 * @return Returns getStringPayload(1) by default
	 */
	public boolean getState() {
		return this.payload.getBoolPayload(0);
	}

	/**
	 * Sets the command in the payload Override to change the index position
	 * 
	 * @param freq The command to be added
	 */
	public void setState(boolean state) {
		this.payload.setBoolPayload(0, state);
	}

	public TileEntity getTarget(World world) {
		if (this.targetExists(world))
			return world.getBlockTileEntity(
					this.xPosition,
					this.yPosition,
					this.zPosition);
		return null;
	}
}
