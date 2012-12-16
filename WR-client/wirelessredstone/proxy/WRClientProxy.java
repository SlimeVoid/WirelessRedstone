package wirelessredstone.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetHandler;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import wirelessredstone.api.IBaseModOverride;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.handlers.ClientGuiPacketHandler;
import wirelessredstone.network.handlers.ClientRedstoneEtherPacketHandler;
import wirelessredstone.network.handlers.ClientTilePacketHandler;
import wirelessredstone.network.handlers.ClientWirelessDevicePacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.executor.ClientDevicePacketChangeFreqExecutor;
import wirelessredstone.network.packets.executor.ClientEtherPacketRXAddExecutor;
import wirelessredstone.network.packets.executor.ClientEtherPacketTXAddExecutor;
import wirelessredstone.network.packets.executor.DevicePacketActivateRXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketActivateTXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateRXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateTXExecutor;
import wirelessredstone.overrides.RedstoneEtherOverrideSMP;
import wirelessredstone.overrides.TileEntityRedstoneWirelessOverrideSMP;
import wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessR;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessT;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.client.registry.ClientRegistry;
/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class WRClientProxy extends WRCommonProxy {

	/**
	 * Wireless Receiver GUI
	 */
	public static GuiRedstoneWirelessInventory guiWirelessR;
	/**
	 * Wireless Transmitter GUI
	 */
	public static GuiRedstoneWirelessInventory guiWirelessT;


	private static List<IBaseModOverride> overrides;
	
	@Override
	public void init() {
		initGUIs();
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessR = new GuiRedstoneWirelessR();
		guiWirelessT = new GuiRedstoneWirelessT();
	}


	/**
	 * Adds a GUI override to the Receiver.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToReceiver(IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance(
				"WRClientProxy"
		).write(
				true,
				"Override added to "
						+ LoggerRedstoneWireless.filterClassName(guiWirelessR.getClass().toString())
						+ " - " + 
						LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		guiWirelessR.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Transmitter.
	 * 
	 * @param override
	 *            GUI override
	 */
	public static void addGuiOverrideToTransmitter(
			IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance(
				"WRClientProxy"
		).write(
				true,
				"Override added to "
						+ LoggerRedstoneWireless.filterClassName(guiWirelessT.getClass().toString())
						+ " - " + 
						LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		guiWirelessT.addOverride(override);
	}
	
	@Override
	public void registerRenderInformation() {
		loadBlockTextures();
	}

	/**
	 * Loads all Block textures from ModLoader override and stores the indices
	 * into the sprite integers.
	 */
	public static void loadBlockTextures() {
		MinecraftForgeClient.preloadTexture("/WirelessSprites/terrain.png");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
		ClientRegistry.bindTileEntitySpecialRenderer(clazz, new TileEntityRedstoneWirelessRenderer());
	}

	@Override
	public void addOverrides() {
		overrides = new ArrayList();
		
		RedstoneEtherOverrideSMP etherOverride = new RedstoneEtherOverrideSMP();
		RedstoneEther.getInstance().addOverride(etherOverride);
		
		//GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new GuiRedstoneWirelessInventoryOverrideSMP();
		//addGuiOverrideToReceiver(GUIOverride);
		//addGuiOverrideToTransmitter(GUIOverride);

		//BlockRedstoneWirelessOverrideSMP blockOverride = new BlockRedstoneWirelessOverrideSMP();
		//WRCore.addOverrideToReceiver(blockOverride);
		//WRCore.addOverrideToTransmitter(blockOverride);

		TileEntityRedstoneWirelessOverrideSMP tileOverride = new TileEntityRedstoneWirelessOverrideSMP();
		TileEntityRedstoneWireless.addOverride(tileOverride);

		//RedstoneEtherOverrideSMP etherOverrideSMP = new RedstoneEtherOverrideSMP();
		//RedstoneEther.getInstance().addOverride(etherOverrideSMP);

		//BaseModOverrideSMP baseModOverride = new BaseModOverrideSMP();
		//this.addOverride(baseModOverride);
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

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer,
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
	
	/**
	 * Retrieves the world object without parameters
	 * 
	 * @return the world
	 */
	@Override
	public World getWorld() {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	/**
	 * Retrieves the player object
	 * 
	 * @return the player
	 */
	@Override
	public EntityPlayer getPlayer() {
		return ModLoader.getMinecraftInstance().thePlayer;
	}
	
	/**
	 * Retrieves the world object with NetHandler parameters.
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld(NetHandler handler) {
		if (handler instanceof NetClientHandler) {
			return ((NetClientHandler)handler).getPlayer().worldObj;
		}
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
		World world = getWorld(handler);
		if (world != null) {
			ClientPacketHandler.sendPacket((Packet250CustomPayload)((new PacketRedstoneEther(PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString())).getPacket()));
		}
	}
	
	@Override
	public void initPacketHandlers() {
		super.initPacketHandlers();
		/////////////////////
		// Client Handlers //
		/////////////////////
		ClientPacketHandler.init();
		// Ether Packets
		ClientRedstoneEtherPacketHandler etherPacketHandler = new ClientRedstoneEtherPacketHandler();
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
				new ClientEtherPacketTXAddExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
				new ClientEtherPacketRXAddExecutor());
		ClientPacketHandler.registerPacketHandler(
				PacketIds.ETHER,
				etherPacketHandler);
		// Device Packets
		ClientWirelessDevicePacketHandler devicePacketHandler = new ClientWirelessDevicePacketHandler();
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.changeFreq.toString(),
				new ClientDevicePacketChangeFreqExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.activateTX.toString(),
				new DevicePacketActivateTXExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.activateRX.toString(),
				new DevicePacketActivateRXExecutor());
		ClientPacketHandler.registerPacketHandler(
				PacketIds.DEVICE,
				devicePacketHandler);
		// GUI Packets
		ClientPacketHandler.registerPacketHandler(
				PacketIds.GUI,
				new ClientGuiPacketHandler());
		// Tile Packets
		ClientPacketHandler.registerPacketHandler(
				PacketIds.TILE,
				new ClientTilePacketHandler());
	}
}
