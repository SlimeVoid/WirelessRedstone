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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public abstract class SubPacketHandler implements IPacketHandler {

    private Map<String, IPacketExecutor> executors = new HashMap<String, IPacketExecutor>();

    /**
     * Register an executor with the server-side packet sub-handler.
     * 
     * @param commandID
     *            Command ID for the executor to handle.
     * @param executor
     *            The executor
     */
    public void registerPacketHandler(String commandString, IPacketExecutor executor) {
        if (executors.containsKey(commandString)) {
            LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.toString())).write(false,
                                                                                                              "Command String ["
                                                                                                                      + commandString
                                                                                                                      + "] already registered.",
                                                                                                              LoggerRedstoneWireless.LogLevel.ERROR);
            throw new RuntimeException("Command String [" + commandString
                                       + "] already registered.");
        }
        executors.put(commandString,
                      executor);
    }

    /**
     * Receive a packet from the handler.<br>
     * Assembles the packet into an wireless packet and routes to
     * handlePacket().
     */
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        EntityPlayer entityplayer = (EntityPlayer) player;
        World world = entityplayer.worldObj;
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            // Assemble packet
            int packetID = data.read();
            PacketWireless pW = this.createNewPacketWireless();
            pW.readData(data);
            // Route to handlePacket()
            handlePacket(pW,
                         world,
                         entityplayer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abstract method for returning a new instance of PacketWireless
     * 
     * @return new PacketWireless
     */
    protected abstract PacketWireless createNewPacketWireless();

    /**
     * Handles a received packet.
     * 
     * @param packet
     *            The received packet
     * @param world
     *            The world object
     * @param entityplayer
     *            The sending player.
     */
    protected void handlePacket(PacketWireless packet, World world, EntityPlayer entityplayer) {
        LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                                     "handlePacket("
                                                                                                                             + packet.toString()
                                                                                                                             + ", world,"
                                                                                                                             + entityplayer.username
                                                                                                                             + ")",
                                                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);
        // Fetch the command.
        String command = packet.getCommand();

        // Execute the command.
        if (executors.containsKey(command)) {
            executors.get(command).execute(packet,
                                           world,
                                           entityplayer);
        } else {
            LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                                         "handlePacket("
                                                                                                                                 + packet.toString()
                                                                                                                                 + ", world,"
                                                                                                                                 + entityplayer.username
                                                                                                                                 + ") - UNKNOWN COMMAND",
                                                                                                                         LoggerRedstoneWireless.LogLevel.WARNING);
            throw new RuntimeException("Tried to get a Packet Executor for command: "
                                       + command
                                       + " that has not been registered.");
        }
    }
}
