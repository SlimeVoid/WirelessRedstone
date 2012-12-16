package wirelessredstone.addon.remote.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
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
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.overrides.GuiRedstoneWirelessRemoteOverride;
import wirelessredstone.addon.remote.presentation.gui.GuiRedstoneWirelessRemote;
import wirelessredstone.addon.remote.tickhandler.ClientTickHandler;
import wirelessredstone.api.IBaseModOverride;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.registry.TickRegistry;
/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class WRemoteClientProxy extends WRemoteCommonProxy {

	/**
	 * Wireless Remote GUI
	 */
	public static GuiRedstoneWirelessRemote guiWirelessRemote;


	private static List<IBaseModOverride> overrides;
	
	@Override
	public void init() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		initGUIs();
	}

	/**
	 * Initializes GUI objects.
	 */
	public static void initGUIs() {
		guiWirelessRemote = new GuiRedstoneWirelessRemote();
		IGuiRedstoneWirelessDeviceOverride override = new GuiRedstoneWirelessRemoteOverride();
		guiWirelessRemote.addOverride(override);
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
	}

	@Override
	public void addOverrides() {
		overrides = new ArrayList();
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
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		guiWirelessRemote.assWirelessDevice(devicedata, entityplayer);
		ModLoader.openGUI(entityplayer, guiWirelessRemote);
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
		if (ModLoader.getMinecraftInstance().isSingleplayer()) {
			super.initPacketHandlers();
			return;
		}
		/////////////////////
		// Client Handlers //
		/////////////////////
	}

	@Override
	public void activateRemote(World world, EntityLiving entityliving) {
		if (!world.isRemote) {
			super.activateRemote(world, entityliving);
		}
	}

	@Override
	public void deactivateRemote(World world, EntityLiving entityplayer) {
		if (!world.isRemote) {
			super.deactivateRemote(world, entityplayer);
		}
	}
}
