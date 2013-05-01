package wirelessredstone.addon.powerconfig.core;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import wirelessredstone.addon.powerconfig.client.presentation.gui.GuiRedstoneWirelessPowerDirector;
import wirelessredstone.addon.powerconfig.items.ItemRedstoneWirelessPowerDirector;
import wirelessredstone.addon.powerconfig.overrides.BlockRedstoneWirelessROverridePC;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.ConfigStoreRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PowerConfigurator {
	public static boolean isLoaded = false;
	public static GuiRedstoneWirelessPowerDirector guiPowerC;
	public static Item itemPowDir;
	public static int spritePowerC;
	public static int pdID = 6243;

	public static boolean initialize() {
		try {
			loadConfig();
			loadItemTextures();

			initItem();
			initGui();

			addRecipes();
			addOverrides();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless
					.getInstance(
							LoggerRedstoneWireless
									.filterClassName(PowerConfigurator.class
											.toString())).write(
							false, "Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	private static void loadConfig() {
		pdID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"PowerConfigurator")
				.get("ID", Integer.class, new Integer(pdID));
	}

	private static void loadItemTextures() {
	}

	private static void initItem() {
		itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID))
				.setUnlocalizedName("wirelessredstone.powdir");
		LanguageRegistry.addName(itemPowDir, "Power Configurator");
	}

	private static void initGui() {
		guiPowerC = new GuiRedstoneWirelessPowerDirector();
	}

	private static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(itemPowDir, 1), new Object[] { "R R",
				" X ", "R R", Character.valueOf('X'),
				WRCore.blockWirelessR, Character.valueOf('R'),
				Item.redstone });
	}

	private static void addOverrides() {
		WRCore.addOverrideToReceiver(new BlockRedstoneWirelessROverridePC());
	}

	public static void activateGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (tileentity instanceof TileEntityRedstoneWirelessR) {
			PowerConfigurator.guiPowerC
					.assTileEntity((TileEntityRedstoneWirelessR) tileentity);
			ModLoader.openGUI(entityplayer, guiPowerC);
		}
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		boolean prematureExit = false;
		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(world, entityplayer, tileentity))
				prematureExit = true;
		}
		if (!prematureExit)
			activateGUI(world, entityplayer, tileentity);
	}
}
