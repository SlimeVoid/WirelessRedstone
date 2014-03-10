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
package com.slimevoid.wirelessredstone.network.handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.slimevoid.library.network.handlers.SubPacketHandler;
import com.slimevoid.library.util.helpers.PacketHelper;
import com.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import com.slimevoid.wirelessredstone.ether.RedstoneEther;
import com.slimevoid.wirelessredstone.ether.RedstoneEtherNode;
import com.slimevoid.wirelessredstone.network.packets.PacketRedstoneEther;
import com.slimevoid.wirelessredstone.network.packets.PacketWireless;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

import cpw.mods.fml.common.FMLCommonHandler;

/**
 * A server-side RedstoneEther packet sub-handler.
 * 
 * @author ali4z
 */
public class RedstoneEtherPacketHandler extends SubPacketHandler {

    @Override
    protected PacketWireless createNewPacket() {
        return new PacketRedstoneEther();
    }

    /**
     * Broadcasts an ether tile to all clients.
     * 
     * @param entity
     *            The ether tile.
     * @param world
     *            The world object.
     */
    public static void sendEtherTileToAll(TileEntityRedstoneWireless entity, World world) {
        // Assemble packet.
        PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(world.isRemote,
                                                                                     "sendEtherTileToAll("
                                                                                             + packet.toString()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Broadcast packet.
        PacketHelper.broadcastPacket(packet);
    }

    /**
     * Broadcasts an ether tile to all clients.
     * 
     * @param entity
     *            The ether tile.
     * @param world
     *            The world object.
     */
    public static void sendEtherTileToAllInRange(TileEntityRedstoneWireless entity, World world, int range) {
        // Assemble packet.
        PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(world.isRemote,
                                                                                     "sendEtherTileToAllInRange("
                                                                                             + packet.toString()
                                                                                             + ", "
                                                                                             + range
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Broadcast packet.
        PacketHelper.sendToAllAround(packet,
                                     entity.xCoord,
                                     entity.yCoord,
                                     entity.zCoord,
                                     range,
                                     world.provider.dimensionId);
    }

    /**
     * Send an ether tile to a specific player.
     * 
     * @param entityplayermp
     *            The receiving player.
     * @param entity
     *            The ether tile.
     * @param world
     *            The world object.
     */
    public static void sendEtherTileTo(EntityPlayerMP entityplayermp, TileEntityRedstoneWireless entity, World world) {
        // Assemble packet.
        PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(world.isRemote,
                                                                                     "sendEtherTileTo("
                                                                                             + entityplayermp.getDisplayName()
                                                                                             + ","
                                                                                             + packet.toString()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Send packet.
        PacketHelper.sendToPlayer(packet,
                                  entityplayermp);
    }

    /**
     * Broadcast a ether node to all clients.
     * 
     * @param node
     *            Ether node.
     */
    public static void sendEtherNodeTileToAll(RedstoneEtherNode node) {
        // Fetch required data.
        World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);
        TileEntity entity = world.getTileEntity(node.i,
                                                node.j,
                                                node.k);

        if (entity instanceof TileEntityRedstoneWireless) {
            // Send the required data.
            sendEtherTileToAll((TileEntityRedstoneWireless) entity,
                               world);
        }
    }

    /**
     * Send all ether node to a specific player.
     * 
     * @param entityplayermp
     *            Receiving player.
     */
    public static void sendEtherTilesTo(EntityPlayerMP entityplayermp) {
        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(false,
                                                                                     "sendEtherTilesTo("
                                                                                             + entityplayermp.getDisplayName()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Prepare required data.
        PacketRedstoneEther packet;
        World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

        // Fetch all receivers
        List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();

        // Send each receivers to the player.
        for (RedstoneEtherNode node : list) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessR) {
                sendEtherTileTo(entityplayermp,
                                (TileEntityRedstoneWirelessR) entity,
                                world);
            }
        }

        // Fetch all transmitters
        list = RedstoneEther.getInstance().getTXNodes();

        // Send all transmitters to the player.
        for (RedstoneEtherNode node : list) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessT) {
                sendEtherTileTo(entityplayermp,
                                (TileEntityRedstoneWirelessT) entity,
                                world);
            }
        }
    }

    /**
     * Broadcast all ether nodes
     */
    public static void sendEtherTilesToAll() {
        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(false,
                                                                                     "sendEtherTilesToAll()",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Prepare required data.
        PacketRedstoneEther packet;
        World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

        // Fetch all receivers
        List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();

        // Broadcast all receivers
        for (RedstoneEtherNode node : list) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessR) {
                sendEtherTileToAll((TileEntityRedstoneWirelessR) entity,
                                   world);
            }
        }

        // Fetch all transmitters
        list = RedstoneEther.getInstance().getTXNodes();

        // Broadcast all transmitters
        for (RedstoneEtherNode node : list) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessT) {
                sendEtherTileToAll((TileEntityRedstoneWirelessT) entity,
                                   world);
            }
        }
    }

    /**
     * Broadcast a set of transmitters and receivers to all.
     * 
     * @param txs
     * @param rxs
     */
    public static void sendEtherFrequencyTilesToAll(List<RedstoneEtherNode> txs, List<RedstoneEtherNode> rxs) {
        LoggerRedstoneWireless.getInstance("ServerRedstoneEtherPacketHandler").write(false,
                                                                                     "sendEtherFrequencyTilesToAll()",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Assemble required data.
        World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

        // Broadcast receivers.
        for (RedstoneEtherNode node : rxs) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessR) {
                sendEtherTileToAll((TileEntityRedstoneWirelessR) entity,
                                   world);
            }
        }

        // Broadcast transmitters.
        for (RedstoneEtherNode node : txs) {
            TileEntity entity = world.getTileEntity(node.i,
                                                    node.j,
                                                    node.k);
            if (entity instanceof TileEntityRedstoneWirelessT) {
                sendEtherTileToAll((TileEntityRedstoneWirelessT) entity,
                                   world);
            }
        }
    }

    /*
     * CLIENT SIDE
     */
    public static void sendEtherPacketToServer(String command, int i, int j, int k, Object freq, boolean state) {
        PacketRedstoneEther packet = new PacketRedstoneEther(command);
        packet.setPosition(i,
                           j,
                           k,
                           0);
        packet.setFreq(freq);
        packet.setState(state);
        LoggerRedstoneWireless.getInstance("ClientRedstoneEtherPacketHandler").write(true,
                                                                                     "sendRedstoneEtherPacket("
                                                                                             + packet.toString()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);
        PacketHelper.sendToServer(packet);
    }
}
