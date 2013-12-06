package wirelessredstone.addon.camouflager.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import wirelessredstone.addon.camouflager.items.ItemRedstoneWirelessCamouflager;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.core.lib.ConfigurationLib;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CamouCore {

	@SidedProxy(
			clientSide = "wirelessredstone.addon.camouflager.client.proxy.CamouClientProxy",
			serverSide = "wirelessredstone.addon.camouflager.proxy.CamouCommonProxy")
	public static ICommonProxy	proxy;

	public static int			camouID	= 6244;
	public static Item			itemCamouflager;

	/**
	 * Fires off all the canons.<br>
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.
	 */
	public static boolean initialize() {
		loadConfig();

		proxy.init();

		proxy.initPacketHandlers();

		initItems();

		proxy.addOverrides();

		registerItems();

		proxy.registerRenderInformation();

		addRecipes();
		return true;
	}

	private static void loadConfig() {
		Configuration wirelessconfig = ConfigurationLib.getConfig();

		wirelessconfig.load();

		camouID = wirelessconfig.get(	Configuration.CATEGORY_ITEM,
										"Camouflager",
										camouID).getInt();

		wirelessconfig.save();
	}

	private static void initItems() {
		itemCamouflager = (new ItemRedstoneWirelessCamouflager(camouID)).setUnlocalizedName("wirelessredstone.camou");
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
		LanguageRegistry.addName(	itemCamouflager,
									"Wireless Camouflager");
	}

	private static void addRecipes() {
		GameRegistry.addRecipe(	new ItemStack(itemCamouflager, 1),
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
