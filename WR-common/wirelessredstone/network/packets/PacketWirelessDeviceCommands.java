package wirelessredstone.network.packets;

import java.util.HashMap;
import java.util.Map;

public class PacketWirelessDeviceCommands {
	public enum deviceCommands {
		activateTX,
		deactivateTX,
		activateRX,
		deactivateRX,
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
	}

	public static void registerCommands() {
		deviceCommands.activateTX.value = 0;
		deviceCommands.activateTX.name = "activateTX";
		registerCommand(deviceCommands.activateTX.name);
		deviceCommands.deactivateTX.value = 1;
		deviceCommands.deactivateTX.name = "deactivateTX";
		registerCommand(deviceCommands.deactivateTX.name);
		deviceCommands.activateRX.value = 2;
		deviceCommands.activateRX.name = "activateRX";
		registerCommand(deviceCommands.activateRX.name);
		deviceCommands.deactivateRX.value = 3;
		deviceCommands.deactivateRX.name = "deactivateRX";
		registerCommand(deviceCommands.deactivateRX.name);
		deviceCommands.changeFreq.value = 4;
		deviceCommands.changeFreq.name = "changeDeviceFreq";
		registerCommand(deviceCommands.changeFreq.name);
	}

	public static String commandToString(int command) {
		for (deviceCommands value : deviceCommands
				.values()) {
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
