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
package net.slimevoid.wirelessredstone.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.slimevoid.library.network.PacketPayload;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.wirelessredstone.api.IPacketWirelessOverride;
import net.slimevoid.wirelessredstone.api.IRedstoneWirelessData;
import net.slimevoid.wirelessredstone.core.lib.CoreLib;

/**
 * Extend for new packets
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketWireless extends PacketUpdate implements
        IRedstoneWirelessData {

    private static List<IPacketWirelessOverride> overrides = new ArrayList<IPacketWirelessOverride>();

    public static void addOverride(IPacketWirelessOverride override) {
        if (!overrides.contains(override)) {
            overrides.add(override);
        }
    }

    @Override
    public void writeData(ChannelHandlerContext ctx, ByteBuf data) {
        super.writeData(ctx,
                        data);
    }

    @Override
    public void readData(ChannelHandlerContext ctx, ByteBuf data) {
        super.readData(ctx,
                       data);
    }

    /**
     * Constructor for Default Wireless Packets
     * 
     * @param packetId
     *            the packet ID used to identify the type of packet data being
     *            sent or received
     */
    public PacketWireless(int packetId) {
        super(packetId);
        this.setChannel(CoreLib.MOD_CHANNEL);
    }

    /**
     * Constructor for Default Wireless Packets Used to add payload data to the
     * packet
     * 
     * @param packetId
     *            the packet ID used to identify the type of packet data being
     *            sent or received
     * @param payload
     *            the new payload to be associated with the packet
     */
    public PacketWireless(int packetId, PacketPayload payload) {
        super(packetId, payload);
        this.setChannel(CoreLib.MOD_CHANNEL);
    }

    @Override
    public String toString() {
        return this.getCommand() + "(" + xPosition + "," + yPosition + ","
               + zPosition + ")[" + this.getFreq() + "]";
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
     * @param freq
     *            The command to be added
     */
    public void setFreq(Object freq) {
        this.payload.setStringPayload(0,
                                      freq.toString());
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
     * @param freq
     *            The command to be added
     */
    public void setState(boolean state) {
        this.payload.setBoolPayload(0,
                                    state);
    }

    public TileEntity getTarget(World world) {
        boolean skipDefault = false;
        for (IPacketWirelessOverride override : overrides) {
            if (override.shouldSkipDefault()) {
                skipDefault = true;
                break;
            }
        }
        TileEntity tileentity = null;
        if (!skipDefault) {
            if (this.targetExists(world)) {
                tileentity = world.getTileEntity(this.xPosition,
                                                 this.yPosition,
                                                 this.zPosition);
            }
        }
        for (IPacketWirelessOverride override : overrides) {
            tileentity = override.getTarget(world,
                                            this.xPosition,
                                            this.yPosition,
                                            this.zPosition,
                                            tileentity);
        }
        return tileentity;
    }
}
