package wirelessredstone.network.packets;

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
		sendGui;
	
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

	public static String commandToString(int command) {
		for (wirelessCommands value : wirelessCommands
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
