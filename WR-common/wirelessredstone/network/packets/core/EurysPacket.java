/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.network.packets.core;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

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
	public boolean	isChunkDataPacket	= false;

	private String	channel;

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public abstract void writeData(DataOutputStream data) throws IOException;

	public abstract void readData(DataInputStream data) throws IOException;

	protected abstract int getPacketID();

	public String toString(boolean full) {
		return toString();
	}

	@Override
	public String toString() {
		return getPacketID() + " " + getClass().getSimpleName();
	}

	/**
	 * Retrieves the Custom Packet and Payload data as a Forge
	 * Packet250CustomPayload
	 */
	public Packet250CustomPayload getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getPacketID());
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
