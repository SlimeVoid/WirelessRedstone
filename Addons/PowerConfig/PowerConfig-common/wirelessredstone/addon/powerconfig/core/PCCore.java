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
package wirelessredstone.addon.powerconfig.core;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import wirelessredstone.addon.powerconfig.items.ItemRedstoneWirelessPowerDirector;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.ConfigStoreRedstoneWireless;

public class PCCore {
	public static boolean		isLoaded	= false;
	public static Item			itemPowDir;
	public static int			spritePowerC;
	public static int			pdID		= 6243;

	@SidedProxy(
			clientSide = "wirelessredstone.addon.powerconfig.client.proxy.PowerConfigClientProxy",
			serverSide = "wirelessredstone.addon.powerconfig.proxy.PowerConfigCommonProxy")
	public static ICommonProxy	proxy;

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
		pdID = (Integer) ConfigStoreRedstoneWireless.getInstance("PowerConfigurator").get(	"ID",
																							Integer.class,
																							new Integer(pdID));
	}

	private static void initItems() {
		itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID)).setUnlocalizedName("wirelessredstone.powdir");
	}

	/**
	 * Registers the item names
	 */
	private static void registerItems() {
		LanguageRegistry.addName(	itemPowDir,
									"Power Configurator");
	}

	private static void addRecipes() {
		GameRegistry.addRecipe(	new ItemStack(itemPowDir, 1),
								new Object[] {
										"R R",
										" X ",
										"R R",
										Character.valueOf('X'),
										WRCore.blockWirelessR,
										Character.valueOf('R'),
										Item.redstone });
	}
}
