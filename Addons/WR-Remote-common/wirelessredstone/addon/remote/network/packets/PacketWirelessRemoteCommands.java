package wirelessredstone.addon.remote.network.packets;

import java.util.HashMap;
import java.util.Map;

public enum PacketWirelessRemoteCommands {
	activate,
	deactivate,
	changeFreq;

	private int value;
	private String name;

	public int getCommand() {
		if (this != null) {
			return this.value;
		}
		return -1;
	}

	public String toString() {
		if (this != null && this.name != null && !this.name.isEmpty()) {
			return this.name;
		}
		return "Command not initialzed";
	}

	public static String commandToString(int command) {
		for (PacketWirelessRemoteCommands value : PacketWirelessRemoteCommands
				.values()) {
			if (value != null & value.getCommand() == command) {
				return value.toString();
			}
		}
		return "No Command Exists with value " + command;
	}

	public static void registerCommands() {
		activate.value = 0;
		activate.name = "activateDevice";
		registerCommand(activate.name);
		deactivate.value = 1;
		deactivate.name = "deactivateDevice";
		registerCommand(deactivate.name);
		changeFreq.value = 2;
		changeFreq.name = "changeDeviceFreq";
		registerCommand(changeFreq.name);
	}
	
	private static Map<Integer, String> commandList = new HashMap<Integer, String>();
	
	private static int getNextAvailableCommand() {
		return commandList.size() - 1;
	}
	
	public static void registerCommand(String name) {
		int nextID = getNextAvailableCommand();
		if (!commandList.containsKey(nextID)) {
			commandList.put(nextID, name);
		}
	}
}
