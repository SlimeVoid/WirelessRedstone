package wirelessredstone.proxy;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import wirelessredstone.api.IBaseModOverride;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.overrides.BaseModOverrideSMP;
import wirelessredstone.overrides.BlockRedstoneWirelessOverrideSMP;
import wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverrideSMP;
import wirelessredstone.overrides.RedstoneEtherOverrideSMP;
import wirelessredstone.overrides.RedstoneEtherOverrideSSP;
import wirelessredstone.overrides.TileEntityRedstoneWirelessOverrideSMP;
import wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessR;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessT;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
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
	public static void addGuiOverrideToReceiver(
			IGuiRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ guiWirelessR.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
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
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ guiWirelessT.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
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
		
		GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new GuiRedstoneWirelessInventoryOverrideSMP();
		addGuiOverrideToReceiver(GUIOverride);
		addGuiOverrideToTransmitter(GUIOverride);

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

	private static List<IBaseModOverride> overrides;

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
}
