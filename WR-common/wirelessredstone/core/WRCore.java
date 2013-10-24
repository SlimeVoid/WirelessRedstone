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
package wirelessredstone.core;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.api.IBlockRedstoneWirelessOverride;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.block.BlockRedstoneWirelessR;
import wirelessredstone.block.BlockRedstoneWirelessT;
import wirelessredstone.data.ConfigStoreRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * WirelessRedstone class
 * 
 * To allow abstraction from the BaseMod code
 * 
 * @author Eurymachus & Ali4z
 * 
 */
public class WRCore {
	/**
	 * Wireless Receiver Block
	 */
	public static Block			blockWirelessR;
	/**
	 * Wireless Transmitter Block
	 */
	public static Block			blockWirelessT;

	/**
	 * Wireless Receiver Block ID
	 */
	public static int			rxID				= 180;
	/**
	 * Wireless Transmitter Block ID
	 */
	public static int			txID				= 179;

	/**
	 * Wireless Transmitter Item texture.
	 */
	public static int			spriteTItem;
	/**
	 * Wireless Receiver Item texture.
	 */
	public static int			spriteRItem;
	/**
	 * Wireless Redstone Ether maximum nodes
	 */
	public static int			maxEtherFrequencies	= 10000;
	/**
	 * Wireless Redstone load state
	 */
	public static boolean		isLoaded			= false;

	@SidedProxy(
			clientSide = "wirelessredstone.client.proxy.WRClientProxy",
			serverSide = "wirelessredstone.proxy.WRCommonProxy")
	public static ICommonProxy	proxy;

	/**
	 * Fires off all the canons.<br>
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.
	 */
	public static boolean initialize() {

		loadConfig();

		proxy.init();

		PacketRedstoneWirelessCommands.registerCommands();

		// PacketWirelessDeviceCommands.registerCommands();

		proxy.initPacketHandlers();

		initBlocks();

		proxy.addOverrides();

		registerBlocks();

		proxy.registerRenderInformation();

		proxy.registerTileEntitySpecialRenderer(TileEntityRedstoneWireless.class);

		addRecipes();

		return true;

	}

	/**
	 * Initializes Block objects.
	 */
	private static void initBlocks() {
		blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F)).setUnlocalizedName("wirelessredstone.receiver");
		blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F)).setUnlocalizedName("wirelessredstone.transmitter");
	}

	/**
	 * Registers the Blocks, block names and TileEntities
	 */
	private static void registerBlocks() {
		GameRegistry.registerBlock(	blockWirelessR,
									"wirelessredstone.receiver");
		LanguageRegistry.addName(	blockWirelessR,
									"Wireless Receiver");
		LanguageRegistry.instance().addNameForObject(	blockWirelessR,
														"de_DE",
														"Drahtloser Empfanger");
		LanguageRegistry.instance().addNameForObject(	blockWirelessR,
														"nb_NO",
														"Tradlos Mottaker");
		LanguageRegistry.instance().addNameForObject(	blockWirelessR,
														"nn_NO",
														"Tradlaus Mottaker");
		GameRegistry.registerTileEntity(TileEntityRedstoneWirelessR.class,
										"Wireless Receiver");
		GameRegistry.registerBlock(	blockWirelessT,
									"wirelessredstone.transmitter");
		LanguageRegistry.addName(	blockWirelessT,
									"Wireless Transmitter");
		LanguageRegistry.instance().addNameForObject(	blockWirelessT,
														"de_DE",
														"Drahtloser Sender");
		LanguageRegistry.instance().addNameForObject(	blockWirelessT,
														"nb_NO",
														"Tradlos Sender");
		LanguageRegistry.instance().addNameForObject(	blockWirelessT,
														"nn_NO",
														"Tradlaus Sender");
		GameRegistry.registerTileEntity(TileEntityRedstoneWirelessT.class,
										"Wireless Transmitter");
	}

	/**
	 * Registers receipts with ModLoader<br>
	 * - Receipt for Receiver.<br>
	 * - Receipt for Transmitter.
	 */
	private static void addRecipes() {
		GameRegistry.addRecipe(	new ItemStack(blockWirelessR, 1),
								new Object[] {
										"IRI",
										"RLR",
										"IRI",
										Character.valueOf('I'),
										Item.ingotIron,
										Character.valueOf('R'),
										Item.redstone,
										Character.valueOf('L'),
										Block.lever });
		GameRegistry.addRecipe(	new ItemStack(blockWirelessT, 1),
								new Object[] {
										"IRI",
										"RTR",
										"IRI",
										Character.valueOf('I'),
										Item.ingotIron,
										Character.valueOf('R'),
										Item.redstone,
										Character.valueOf('T'),
										Block.torchRedstoneActive });
	}

	/**
	 * Loads configurations from the properties file.<br>
	 * - Receiver block ID: (Receiver.ID)<br>
	 * - Transmitter block ID: (Transmitter.ID)<br>
	 */
	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get(	"Receiver.ID",
																							Integer.class,
																							new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get(	"Transmitter.ID",
																							Integer.class,
																							new Integer(txID));
	}

	/**
	 * Adds a Block override to the Receiver.
	 * 
	 * @param override
	 *            Block override
	 */
	public static void addOverrideToReceiver(IBlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(	true,
																		"Override added to "
																				+ LoggerRedstoneWireless.filterClassName(WRCore.blockWirelessR.getClass().toString())
																				+ " - "
																				+ LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
																		LoggerRedstoneWireless.LogLevel.DEBUG);

		((BlockRedstoneWireless) WRCore.blockWirelessR).addOverride(override);
	}

	/**
	 * Adds a Block override to the Transmitter.
	 * 
	 * @param override
	 *            Block override
	 */
	public static void addOverrideToTransmitter(IBlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(	true,
																		"Override added to "
																				+ LoggerRedstoneWireless.filterClassName(WRCore.blockWirelessT.getClass().toString())
																				+ " - "
																				+ LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
																		LoggerRedstoneWireless.LogLevel.DEBUG);

		((BlockRedstoneWireless) WRCore.blockWirelessT).addOverride(override);
	}

	/**
	 * Fetches an entity by ID.
	 * 
	 * @param world
	 *            The world object
	 * @param entityId
	 *            Entity ID
	 * 
	 * @return The Entity.
	 */
	public static Entity getEntityByID(World world, int entityId) {
		if (world != null) {
			for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entity = (Entity) world.loadedEntityList.get(i);

				if (entity == null) {
					return null;
				}

				if (entity.entityId == entityId) {
					return entity;
				}
			}
		}
		return null;
	}
}
