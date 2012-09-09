package wirelessredstone.core;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;

import wirelessredstone.api.IBlockRedstoneWirelessOverride;
import wirelessredstone.api.IBaseModOverride;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.block.BlockItemRedstoneWirelessR;
import wirelessredstone.block.BlockItemRedstoneWirelessT;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.block.BlockRedstoneWirelessR;
import wirelessredstone.block.BlockRedstoneWirelessT;
import wirelessredstone.data.ConfigStoreRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.CommonPacketHandler;
import wirelessredstone.network.handlers.ClientGuiPacketHandler;
import wirelessredstone.network.handlers.ClientRedstoneEtherPacketHandler;
import wirelessredstone.network.handlers.ClientTilePacketHandler;
import wirelessredstone.network.handlers.RedstoneEtherPacketHandler;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.overrides.RedstoneEtherOverrideSSP;
import wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessR;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessT;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
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
	public static int rxID = 180;
	/**
	 * Wireless Transmitter Block ID
	 */
	public static int txID = 179;

	/**
	 * Block texture, top, on state.
	 */
	public static int spriteTopOn = 1;
	/**
	 * Block texture, top, off state.
	 */
	public static int spriteTopOff = 0;
	/**
	 * Wireless Receiver Block texture, on state.
	 */
	public static int spriteROn = 3;
	/**
	 * Wireless Receiver Block texture, off state.
	 */
	public static int spriteROff = 2;
	/**
	 * Wireless Transmitter Block texture, on state.
	 */
	public static int spriteTOn = 5;
	/**
	 * Wireless Transmitter Block texture, off state.
	 */
	public static int spriteTOff = 4;
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
	
	@SidedProxy(
			clientSide="wirelessredstone.proxy.WRClientProxy",
			serverSide="wirelessredstone.proxy.WRCommonProxy")
	public static ICommonProxy proxy;

	/**
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.<br>
	 * - Load Block textures<br>
	 * - Register Blocks and Tile Entities<br>
	 * - Recipes
	 */
	public static boolean initialize() {

			loadConfig();
			
			proxy.init();

			initPacketHandlers();
			
			initBlocks();

			proxy.addOverrides();

			registerBlocks();
			
			proxy.registerRenderInformation();
			
			proxy.registerTileEntitySpecialRenderer(TileEntityRedstoneWireless.class);
			
			addRecipes();

			return true;

	}

	public static void initPacketHandlers() {
		CommonPacketHandler.reigsterPacketHandler(PacketIds.ETHER, new RedstoneEtherPacketHandler());
		
		ClientPacketHandler.reigsterPacketHandler(PacketIds.ETHER, new ClientRedstoneEtherPacketHandler());
		ClientPacketHandler.reigsterPacketHandler(PacketIds.GUI, new ClientGuiPacketHandler());
		ClientPacketHandler.reigsterPacketHandler(PacketIds.TILE, new ClientTilePacketHandler());
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
	 * Registers the Blocks and TileEntities with ModLoader
	 */
	public static void registerBlocks() {
		GameRegistry.registerBlock(blockWirelessR,
				BlockItemRedstoneWirelessR.class);
		ModLoader.addName(blockWirelessR, "Wireless Receiver");
		ModLoader.addName(blockWirelessR, "de_DE", "Drahtloser Empfänger");
		GameRegistry.registerTileEntity(TileEntityRedstoneWirelessR.class,
				"Wireless Receiver");

		ModLoader.registerBlock(blockWirelessT,
				BlockItemRedstoneWirelessT.class);
		ModLoader.addName(blockWirelessT, "Wireless Transmitter");
		ModLoader.addName(blockWirelessT, "de_DE", "Drahtloser Sender");
		GameRegistry.registerTileEntity(TileEntityRedstoneWirelessT.class,
				"Wireless Transmitter");
	}

	/**
	 * Registers receipts with ModLoader
	 */
	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
				"IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('L'),
				Block.lever });
		GameRegistry.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
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
	 * Adds a Block override to the Receiver.
	 * 
	 * @param override
	 *            Block override
	 */
	public static void addOverrideToReceiver(
			IBlockRedstoneWirelessOverride override) {
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
			IBlockRedstoneWirelessOverride override) {
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

	public static Entity getEntityByID(World world, EntityPlayer entityplayer,
			int entityId) {
		if (entityId == entityplayer.entityId) {
			return entityplayer;
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
