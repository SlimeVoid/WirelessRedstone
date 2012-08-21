package wirelessredstone.network.packets.core;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;

/**
 * Packet Information Base
 * 
 * @author Eurymachus
 * 
 */
public abstract class EurysPacket {
	/**
	 * Only true for Packet51MapChunk, Packet52MultiBlockChange,
	 * Packet53BlockChange and Packet59ComplexEntity. Used to separate them into
	 * a different send queue.
	 */
	public boolean isChunkDataPacket = false;

	private String channel;

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public abstract void writeData(DataOutputStream data) throws IOException;

	public abstract void readData(DataInputStream data) throws IOException;

	public abstract int getID();

	public String toString(boolean full) {
		return toString();
	}

	@Override
	public String toString() {
		return getID() + " " + getClass().getSimpleName();
	}

	/**
	 * Retrieves the Custom Packet and Payload data as a Forge
	 * Packet250CustomPayload
	 */
	public Packet getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getID());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = this.channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = this.isChunkDataPacket;
		return packet;
	}
}
