package wirelessredstone.core;

import java.util.ArrayList;
import java.util.List;

import wirelessredstone.api.IBaseModOverride;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.block.BlockItemRedstoneWirelessR;
import wirelessredstone.block.BlockItemRedstoneWirelessT;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.block.BlockRedstoneWirelessOverride;
import wirelessredstone.block.BlockRedstoneWirelessR;
import wirelessredstone.block.BlockRedstoneWirelessT;
import wirelessredstone.data.ConfigStoreRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.overrides.RedstoneEtherOverrideSSP;
import wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessR;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessT;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

/**
 * WirelessRedstone class
 * 
 * To allow abstraction from the BaseMod code
 * 
 * @author Eurymachus & Aliz4
 * 
 */
public class WRCore {
	/**
	 * Wireless Receiver Block
	 */
	public static Block blockWirelessR;
	/**
	 * Wireless Transmitter Block
	 */
	public static Block blockWirelessT;
	/**
	 * Wireless Receiver GUI
	 */
	public static GuiRedstoneWirelessInventory guiWirelessR;
	/**
	 * Wireless Transmitter GUI
	 */
	public static GuiRedstoneWirelessInventory guiWirelessT;

	/**
	 * Wireless Receiver Block ID
	 */
	public static int rxID = 127;
	/**
	 * Wireless Transmitter Block ID
	 */
	public static int txID = 126;

	/**
	 * Block texture, top, on state.
	 */
	public static int spriteTopOn;
	/**
	 * Block texture, top, off state.
	 */
	public static int spriteTopOff;
	/**
	 * Wireless Receiver Block texture, on state.
	 */
	public static int spriteROn;
	/**
	 * Wireless Receiver Block texture, off state.
	 */
	public static int spriteROff;
	/**
	 * Wireless Transmitter Block texture, on state.
	 */
	public static int spriteTOn;
	/**
	 * Wireless Transmitter Block texture, off state.
	 */
	public static int spriteTOff;
	/**
	 * Wireless Transmitter Item texture.
	 */
	public static int spriteTItem;
	/**
	 * Wireless Receiver Item texture.
	 */
	public static int spriteRItem;
	/**
	 * Wireless Redstone Ether maximum nodes
	 */
	public static int maxEtherFrequencies = 10000;
	/**
	 * Wireless Redstone load state
	 */
	public static boolean isLoaded = false;

	private static List<IBaseModOverride> overrides;

	private static List<Block> creativeBlockList;

	/**
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.<br>
	 * - Load Block textures<br>
	 * - Register Blocks and Tile Entities<br>
	 * - Recipes
	 */
	public static boolean initialize() {
		try {
			overrides = new ArrayList();
			creativeBlockList = new ArrayList();

			loadConfig();
			loadBlockTextures();
			loadItemTextures();

			addOverrides();

			initBlocks();
			initGUIs();

			registerBlocks();

			addRecipes();

			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless
					.getInstance(
							LoggerRedstoneWireless
									.filterClassName(WRCore.class
											.toString())).write(
							"Initialization failed.",
							LoggerRedstoneWireless.LogLevel.WARNING);
			return false;
		}
	}

	/**
	 * Creates the ether override for SSP
	 */
	private static void addOverrides() {
		RedstoneEtherOverrideSSP etherOverride = new RedstoneEtherOverrideSSP();
		RedstoneEther.getInstance().addOverride(etherOverride);
	}

	/**
	 * Loads all Block textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadBlockTextures() {
		spriteTopOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOn.png");
		spriteTopOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOff.png");
		spriteROn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOn.png");
		spriteROff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOff.png");
		spriteTOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOn.png");
		spriteTOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOff.png");
	}

	/**
	 * Loads all Item textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadItemTextures() {
		spriteTItem = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/txOn.png");
		spriteRItem = ModLoader.addOverride("/gui/items.png",
				"/WirelessSprites/rxOn.png");
	}

	/**
	 * Initializes Block objects.
	 */
	public static void initBlocks() {
		blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F))
				.setBlockName("wirelessredstone.receiver");
		blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F))
				.setBlockName("wirelessredstone.transmitter");
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessR = new GuiRedstoneWirelessR();
		guiWirelessT = new GuiRedstoneWirelessT();
	}

	/**
	 * Registers the Blocks and TileEntities with ModLoader
	 */
	public static void registerBlocks() {
		ModLoader.registerBlock(blockWirelessR,
				BlockItemRedstoneWirelessR.class);
		ModLoader.addName(blockWirelessR, "Wireless Receiver");
		ModLoader.addName(blockWirelessR, "de_DE", "Drahtloser Empfänger");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class,
				"Wireless Receiver", new TileEntityRedstoneWirelessRenderer());

		ModLoader.registerBlock(blockWirelessT,
				BlockItemRedstoneWirelessT.class);
		ModLoader.addName(blockWirelessT, "Wireless Transmitter");
		ModLoader.addName(blockWirelessT, "de_DE", "Drahtloser Sender");
		ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class,
				"Wireless Transmitter",
				new TileEntityRedstoneWirelessRenderer());
	}

	/**
	 * Registers receipts with ModLoader
	 */
	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
				"IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('L'),
				Block.lever });
		ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
				"IRI", "RTR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('T'),
				Block.torchRedstoneActive });
	}

	/**
	 * Loads configurations from the properties file.<br>
	 * - Receiver block ID: (Receiver.ID)<br>
	 * - Transmitter block ID: (Transmitter.ID)<br>
	 */
	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Receiver.ID", Integer.class,
				new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Transmitter.ID", Integer.class,
				new Integer(txID));
	}

	/**
	 * Retrieves the world object without parameters
	 * 
	 * @return the world
	 */
	public static World getWorld() {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	/**
	 * Retrieves the world object
	 * 
	 * @param network
	 *            the Network manager (used for Server)
	 * @return the world
	 */
	public static World getWorld(NetworkManager network) {
		return getWorld();
	}

	/**
	 * Retrieves the player object
	 * 
	 * @return the player
	 */
	public static EntityPlayer getPlayer() {
		return ModLoader.getMinecraftInstance().thePlayer;
	}

	/**
	 * Retrieves the player object
	 * 
	 * @param network
	 *            the Network manager (used for Server)
	 * @return the player
	 */
	public static EntityPlayer getPlayer(NetworkManager network) {
		return ModLoader.getMinecraftInstance().thePlayer;
	}

	/**
	 * Adds a Block override to the Receiver.
	 * 
	 * @param override
	 *            Block override
	 */
	public static void addOverrideToReceiver(
			BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WRCore.blockWirelessR.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless) WRCore.blockWirelessR)
				.addOverride(override);
	}

	/**
	 * Adds a Block override to the Transmitter.
	 * 
	 * @param override
	 *            Block override
	 */
	public static void addOverrideToTransmitter(
			BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WRCore.blockWirelessT.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless) WRCore.blockWirelessT)
				.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Receiver.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToReceiver(
			IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WRCore.guiWirelessR.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
		WRCore.guiWirelessR.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Transmitter.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToTransmitter(
			IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WRCore.guiWirelessT.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
		WRCore.guiWirelessT.addOverride(override);
	}

	/**
	 * Adds a Base override to the The Mod.
	 * 
	 * @param override
	 *            Mod override
	 */
	public static void addOverride(IBaseModOverride override) {
		overrides.add(override);
	}

	public static void activateGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (tileentity instanceof TileEntityRedstoneWirelessR) {
			guiWirelessR
					.assTileEntity((TileEntityRedstoneWirelessR) tileentity);
			ModLoader.openGUI(entityplayer, guiWirelessR);
		}
		if (tileentity instanceof TileEntityRedstoneWirelessT) {
			guiWirelessT
					.assTileEntity((TileEntityRedstoneWirelessT) tileentity);
			ModLoader.openGUI(entityplayer, guiWirelessT);
		}
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		boolean prematureExit = false;
		for (IBaseModOverride override : overrides) {
			if (override.beforeOpenGui(world, entityplayer, tileentity))
				prematureExit = true;
		}

		if (!prematureExit) {
			activateGUI(world, entityplayer, tileentity);
		}
	}

	public static Entity getEntityByID(World world, EntityPlayer entityplayer,
			int entityId) {
		if (entityId == entityplayer.entityId) {
			return WRCore.getPlayer();
		} else {
			for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entity = (Entity) world.loadedEntityList.get(i);

				if (entity == null) {
					return null;
				}

				if (entity.entityId == entityId) {
					return entity;
				}
			}
			return null;
		}
	}
}
