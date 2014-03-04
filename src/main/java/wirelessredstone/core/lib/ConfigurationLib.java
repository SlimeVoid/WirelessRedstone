package wirelessredstone.core.lib;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationLib {

    private static File          configurationFile;
    private static Configuration configuration;

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

        WRCore.txID = configuration.get(Configuration.CATEGORY_GENERAL,
                                        BlockLib.WIRELESS_TRANSMITTER,
                                        1750).getInt();
        WRCore.rxID = configuration.get(Configuration.CATEGORY_GENERAL,
                                        BlockLib.WIRELESS_RECEIVER,
                                        1751).getInt();

        LoggerRedstoneWireless.getInstance("Wireless Redstone").setFilterLevel(configuration.get(Configuration.CATEGORY_GENERAL,
                                                                                                 "Log Level",
                                                                                                 "INFO").getString());

        configuration.save();

        WRCore.wirelessRedstone = new CreativeTabs(CoreLib.MOD_RESOURCES) {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(WRCore.blockWirelessT);
            }

        };
    }

    public static Configuration getConfig() {
        return configuration;
    }
}
