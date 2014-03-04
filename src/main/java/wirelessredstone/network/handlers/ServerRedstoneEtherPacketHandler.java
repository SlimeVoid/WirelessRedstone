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
package wirelessredstone.network.handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.ether.RedstoneEtherNode;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * A server-side RedstoneEther packet sub-handler.
 * 
 * @author ali4z
 */
public class ServerRedstoneEtherPacketHandler extends SubPacketHandler {

    @Override
    protected PacketWireless createNewPacketWireless() {
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
        ServerPacketHandler.broadcastPacket(packet.getPacket());
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
        PacketDispatcher.sendPacketToAllAround(entity.xCoord,
                                               entity.yCoord,
                                               entity.zCoord,
                                               range,
                                               world.provider.dimensionId,
                                               packet.getPacket());
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
                                                                                             + entityplayermp.username
                                                                                             + ","
                                                                                             + packet.toString()
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Send packet.
        ServerPacketHandler.sendPacketTo(entityplayermp,
                                         packet.getPacket());
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
        TileEntity entity = world.getBlockTileEntity(node.i,
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
                                                                                             + entityplayermp.username
                                                                                             + ")",
                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Prepare required data.
        PacketRedstoneEther packet;
        World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

        // Fetch all receivers
        List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();

        // Send each receivers to the player.
        for (RedstoneEtherNode node : list) {
            TileEntity entity = world.getBlockTileEntity(node.i,
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
            TileEntity entity = world.getBlockTileEntity(node.i,
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
            TileEntity entity = world.getBlockTileEntity(node.i,
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
            TileEntity entity = world.getBlockTileEntity(node.i,
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
            TileEntity entity = world.getBlockTileEntity(node.i,
                                                         node.j,
                                                         node.k);
            if (entity instanceof TileEntityRedstoneWirelessR) {
                sendEtherTileToAll((TileEntityRedstoneWirelessR) entity,
                                   world);
            }
        }

        // Broadcast transmitters.
        for (RedstoneEtherNode node : txs) {
            TileEntity entity = world.getBlockTileEntity(node.i,
                                                         node.j,
                                                         node.k);
            if (entity instanceof TileEntityRedstoneWirelessT) {
                sendEtherTileToAll((TileEntityRedstoneWirelessT) entity,
                                   world);
            }
        }
    }
}
