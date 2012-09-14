package wirelessredstone.network.packets;

public enum PacketRedstoneWirelessCommands {
	addTransmitter,
	remTransmitter,
	addReceiver,
	remReceiver,
	setTransmitterState,
	changeFreq,
	fetchTile;

	private int value;
	private String name;

	public int getCommand() {
		if (this != null) {
			return this.value;
		}
		return -1;
	}

	public String toString() {
		if (this != null && !this.name.isEmpty()) {
			return this.name;
		}
		return "Command not initialzed";
	}

	public static String commandToString(int command) {
		for (PacketRedstoneWirelessCommands value : PacketRedstoneWirelessCommands
				.values()) {
			if (value != null & value.getCommand() == command) {
				return value.toString();
			}
		}
		return "No Command Exists with value " + command;
	}

	public static void registerCommands() {
		addTransmitter.value = 0;
		addTransmitter.name = "addTransmitter";

		remTransmitter.value = 1;
		remTransmitter.name = "remTransmitter";

		addReceiver.value = 2;
		addReceiver.name = "addReceiver";

		remReceiver.value = 3;
		remReceiver.name = "remReceiver";

		setTransmitterState.value = 4;
		setTransmitterState.name = "setTransmitterState";

		changeFreq.value = 5;
		changeFreq.name = "changeFreq";

		fetchTile.value = 6;
		fetchTile.name = "fetchTile";
	}
}
