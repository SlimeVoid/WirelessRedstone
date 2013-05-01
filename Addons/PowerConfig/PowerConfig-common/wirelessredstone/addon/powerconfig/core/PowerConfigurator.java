package net.minecraft.src.wirelessredstone.addon.powerc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.powerc.overrides.BlockRedstoneWirelessROverridePC;
import net.minecraft.src.wirelessredstone.addon.powerc.presentation.GuiRedstoneWirelessPowerDirector;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PowerConfigurator {
	public static boolean isLoaded = false;
	public static GuiRedstoneWirelessPowerDirector guiPowerC;
	public static Item itemPowDir;
	public static int spritePowerC;
	public static int pdID = 6243;
	private static List<BaseModOverride> overrides;

	public static boolean initialize() {
		try {
			overrides = new ArrayList<BaseModOverride>();
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
							"Initialization failed.",
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
		spritePowerC = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/pd.png");
	}

	private static void initItem() {
		itemPowDir = (new ItemRedstoneWirelessPowerDirector(pdID))
				.setItemName("wirelessredstone.powdir");
		ModLoader.addName(itemPowDir, "Power Configurator");
	}

	private static void initGui() {
		guiPowerC = new GuiRedstoneWirelessPowerDirector();
	}

	private static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemPowDir, 1), new Object[] { "R R",
				" X ", "R R", Character.valueOf('X'),
				WirelessRedstone.blockWirelessR, Character.valueOf('R'),
				Item.redstone });
	}

	private static void addOverrides() {
		WirelessRedstone
				.addOverrideToReceiver(new BlockRedstoneWirelessROverridePC());
	}

	public static void addOverride(BaseModOverride override) {
		overrides.add(override);
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
