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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.world.World;

/**
 * Packet Information for Reading/Writing packet data
 * 
 * packetId The ID of the packet used to identify which packet handler to use
 * payload The payload to be delivered with the packet
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketUpdate extends EurysPacket {
	private int packetId;

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int side;

	public float vecX;
	public float vecY;
	public float vecZ;

	public void setPosition(int x, int y, int z, int side) {
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.side = side;
	}

	public void setVecs(float vecX, float vecY, float vecZ) {
		this.vecX = vecX;
		this.vecY = vecY;
		this.vecZ = vecZ;
	}

	public PacketPayload payload;

	public PacketUpdate() {
	}

	public PacketUpdate(int packetId, PacketPayload payload) {
		this(packetId);
		this.payload = payload;
	}

	public PacketUpdate(int packetId) {
		this.packetId = packetId;
		this.isChunkDataPacket = true;
	}

	/**
	 * Writes a String to the DataOutputStream
	 */
	public static void writeString(String par0Str, DataOutputStream par1DataOutputStream) throws IOException {
		if (par0Str.length() > 32767) {
			throw new IOException("String too big");
		} else {
			par1DataOutputStream.writeShort(par0Str.length());
			par1DataOutputStream.writeChars(par0Str);
		}
	}

	/**
	 * Reads a string from a packet
	 */
	public static String readString(DataInputStream par0DataInputStream, int par1) throws IOException {
		short var2 = par0DataInputStream.readShort();

		if (var2 > par1) {
			throw new IOException(
					"Received string length longer than maximum allowed (" + var2 + " > " + par1 + ")");
		} else if (var2 < 0) {
			throw new IOException(
					"Received string length is less than zero! Weird string!");
		} else {
			StringBuilder var3 = new StringBuilder();

			for (int var4 = 0; var4 < var2; ++var4) {
				var3.append(par0DataInputStream.readChar());
			}

			return var3.toString();
		}
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {

		data.writeInt(this.xPosition);
		data.writeInt(this.yPosition);
		data.writeInt(this.zPosition);
		data.writeInt(Integer.valueOf(this.side) != null ? this.side : 0);
		data.writeFloat(Float.valueOf(this.vecX) != null ? this.vecX : 0.0F);
		data.writeFloat(Float.valueOf(this.vecY) != null ? this.vecY : 0.0F);
		data.writeFloat(Float.valueOf(this.vecZ) != null ? this.vecZ : 0.0F);

		// No payload means no data
		if (this.payload == null) {
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			return;
		}

		data.writeInt(this.payload.getIntSize());
		data.writeInt(this.payload.getFloatSize());
		data.writeInt(this.payload.getStringSize());
		data.writeInt(this.payload.getBoolSize());
		data.writeInt(this.payload.getDoubleSize());

		for (int i = 0; i < this.payload.getIntSize(); i++)
			data.writeInt(this.payload.getIntPayload(i));
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			data.writeFloat(this.payload.getFloatPayload(i));
		for (int i = 0; i < this.payload.getStringSize(); i++)
			data.writeUTF(this.payload.getStringPayload(i));
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			data.writeBoolean(this.payload.getBoolPayload(i));
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			data.writeDouble(this.payload.getDoublePayload(i));
	}

	@Override
	public void readData(DataInputStream data) throws IOException {

		this.setPosition(
				data.readInt(),
				data.readInt(),
				data.readInt(),
				data.readInt());
		this.setVecs(data.readFloat(), data.readFloat(), data.readFloat());

		int intSize = data.readInt();
		int floatSize = data.readInt();
		int stringSize = data.readInt();
		int boolSize = data.readInt();
		int doubleSize = data.readInt();

		this.payload = new PacketPayload(
				intSize,
					floatSize,
					stringSize,
					boolSize,
					doubleSize);

		for (int i = 0; i < this.payload.getIntSize(); i++)
			this.payload.setIntPayload(i, data.readInt());
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			this.payload.setFloatPayload(i, data.readFloat());
		for (int i = 0; i < this.payload.getStringSize(); i++)
			this.payload.setStringPayload(i, data.readUTF());
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			this.payload.setBoolPayload(i, data.readBoolean());
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			this.payload.setDoublePayload(i, data.readDouble());
	}

	public abstract boolean targetExists(World world);

	@Override
	protected int getPacketID() {
		return this.packetId;
	}
}