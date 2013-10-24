package wirelessredstone.core.lib;

import java.io.File;

import net.minecraftforge.common.Configuration;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {

	private static File				configurationFile;
	private static Configuration	configuration;

	@SideOnly(Side.CLIENT)
	public static void ClientConfig(File configFile) {
		if (configurationFile == null) {
			configurationFile = configFile;
			configuration = new Configuration(configFile);
		}
	}

	public static void CommonConfig(File configFile) {
		if (configurationFile == null) {
			configurationFile = configFile;
			configuration = new Configuration(configFile);
		}

		configuration.load();

		WRCore.txID = configuration.get(Configuration.CATEGORY_BLOCK,
										"Wireless Transmitter",
										1750).getInt();
		WRCore.rxID = configuration.get(Configuration.CATEGORY_BLOCK,
										"Wireless Reciever",
										1751).getInt();

		LoggerRedstoneWireless.getInstance("Wireless Redstone").setFilterLevel(configuration.get(	Configuration.CATEGORY_GENERAL,
																									"Log Level",
																									"INFO").getString());

		configuration.save();
	}

	public static Configuration getConfig() {
		return configuration;
	}
}
