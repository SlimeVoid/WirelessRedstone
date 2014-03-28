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

import java.util.HashMap;
import java.util.Map;

public class PacketRedstoneWirelessCommands {
    public enum wirelessCommands {
        addTransmitter,
        remTransmitter,
        addReceiver,
        remReceiver,
        setTransmitterState,
        changeFreq,
        fetchTile,
        fetchEther,
        sendGui,
        sendDeviceGui;

        private int    value;
        private String name;

        public int getCommand() {
            if (this != null) {
                return this.value;
            }
            return -1;
        }

        @Override
        public String toString() {
            if (this != null && this.name != null && !this.name.isEmpty()) {
                return this.name;
            }
            return "Command[" + this + " is not initialzed";
        }
    }

    public static String commandToString(int command) {
        for (wirelessCommands value : wirelessCommands.values()) {
            if (value != null & value.getCommand() == command) {
                return value.toString();
            }
        }
        String commandString = getRegisteredCommandString(command);
        if (!commandString.equals("")) {
            return commandString;
        }
        return "No Command Exists with value " + command;
    }

    private static String getRegisteredCommandString(int command) {
        if (commandList.containsKey(command)) {
            return commandList.get(command);
        }
        return "";
    }

    public static void registerCommands() {
        wirelessCommands.addTransmitter.value = 0;
        wirelessCommands.addTransmitter.name = "addTransmitter";
        registerCommand(wirelessCommands.addTransmitter.name);

        wirelessCommands.remTransmitter.value = 1;
        wirelessCommands.remTransmitter.name = "remTransmitter";
        registerCommand(wirelessCommands.remTransmitter.name);

        wirelessCommands.addReceiver.value = 2;
        wirelessCommands.addReceiver.name = "addReceiver";
        registerCommand(wirelessCommands.addReceiver.name);

        wirelessCommands.remReceiver.value = 3;
        wirelessCommands.remReceiver.name = "remReceiver";
        registerCommand(wirelessCommands.remReceiver.name);

        wirelessCommands.setTransmitterState.value = 4;
        wirelessCommands.setTransmitterState.name = "setTransmitterState";
        registerCommand(wirelessCommands.setTransmitterState.name);

        wirelessCommands.changeFreq.value = 5;
        wirelessCommands.changeFreq.name = "changeFreq";
        registerCommand(wirelessCommands.changeFreq.name);

        wirelessCommands.fetchTile.value = 6;
        wirelessCommands.fetchTile.name = "fetchTile";
        registerCommand(wirelessCommands.fetchTile.name);

        wirelessCommands.fetchEther.value = 7;
        wirelessCommands.fetchEther.name = "fetchEther";
        registerCommand(wirelessCommands.fetchEther.name);

        wirelessCommands.sendGui.value = 8;
        wirelessCommands.sendGui.name = "sendGui";
        registerCommand(wirelessCommands.sendGui.name);

        wirelessCommands.sendDeviceGui.value = 9;
        wirelessCommands.sendDeviceGui.name = "sendDeviceGui";
        registerCommand(wirelessCommands.sendDeviceGui.name);
    }

    private static Map<Integer, String> commandList = new HashMap<Integer, String>();

    private static int getNextAvailableCommand() {
        return commandList.size() - 1;
    }

    public static void registerCommand(String name) {
        int nextID = getNextAvailableCommand();
        if (!commandList.containsKey(nextID)) {
            commandList.put(nextID,
                            name);
        }
    }
}
