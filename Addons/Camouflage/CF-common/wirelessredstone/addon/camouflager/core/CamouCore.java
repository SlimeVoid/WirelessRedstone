package wirelessredstone.addon.camouflager.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import wirelessredstone.addon.camouflager.core.lib.IconLib;
import wirelessredstone.addon.camouflager.core.lib.ItemLib;
import wirelessredstone.addon.camouflager.items.ItemRedstoneWirelessCamouflager;
import wirelessredstone.core.lib.ConfigurationLib;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CamouCore {

    public static int  camouID = 6244;
    public static Item itemCamouflager;

    /**
     * Fires off all the canons.<br>
     * Loads configurations and initializes objects. Loads ModLoader related
     * stuff.
     */
    public static boolean initialize() {
        loadConfig();

        WirelessCamouflager.proxy.init();

        WirelessCamouflager.proxy.initPacketHandlers();

        initItems();

        WirelessCamouflager.proxy.addOverrides();

        registerItems();

        WirelessCamouflager.proxy.registerRenderInformation();

        registerdRecipes();
        return true;
    }

    private static void loadConfig() {
        Configuration wirelessconfig = ConfigurationLib.getConfig();

        wirelessconfig.load();

        camouID = wirelessconfig.get(Configuration.CATEGORY_ITEM,
                                     "Camouflager",
                                     camouID).getInt();

        wirelessconfig.save();
    }

    private static void initItems() {
        itemCamouflager = (new ItemRedstoneWirelessCamouflager(camouID)).setUnlocalizedName(ItemLib.CAMOUFLAGER).setTextureName(IconLib.CAMOUFLAGER);
    }

    /*
     * private static void addOverrides() {
     * TileEntityRedstoneWirelessCamouflagerOverride tileOverride = new
     * TileEntityRedstoneWirelessCamouflagerOverride();
     * TileEntityRedstoneWireless.addOverride(tileOverride);
     * PacketWirelessCamouflagerOverride packetOverride = new
     * PacketWirelessCamouflagerOverride();
     * PacketWireless.addOverride(packetOverride); }
     */

    /**
     * Registers the item names
     */
    private static void registerItems() {
        LanguageRegistry.addName(itemCamouflager,
                                 "Wireless Camouflager");
    }

    private static void registerdRecipes() {
        GameRegistry.addRecipe(new ItemStack(itemCamouflager, 1),
                               new Object[] {
                                       "GRG",
                                       "RXR",
                                       "RGR",
                                       Character.valueOf('G'),
                                       Block.glass,
                                       Character.valueOf('R'),
                                       Item.redstone,
                                       Character.valueOf('X'),
                                       Block.blockIron });
    }
}
