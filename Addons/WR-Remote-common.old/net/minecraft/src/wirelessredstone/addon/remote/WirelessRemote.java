package net.minecraft.src.wirelessredstone.addon.remote;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.presentation.gui.GuiRedstoneWirelessRemote;
import wirelessredstone.data.WirelessCoordinates;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.BaseModOverrideRemote;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.PacketHandlerWirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.smp.overrides.BaseModOverrideRemoteServer;
import net.minecraft.src.wirelessredstone.addon.remote.smp.overrides.RedstoneEtherOverrideRemoteSMP;
import net.minecraft.src.wirelessredstone.addon.remote.smp.overrides.RedstoneWirelessRemoteOverrideServer;

public class WirelessRemote {
	public static boolean isLoaded = false;
	public static Item itemRemote;
	public static int remoteID = 6245;

	public static HashMap<EntityPlayer, WirelessRemoteDevice> remoteTransmitters;
	public static TreeMap<WirelessCoordinates, WirelessRemoteDevice> remoteWirelessCoords;

	public static long pulseTime = 2500;
	public static boolean duraTogg = true;
	public static int maxPulseThreads = 2;
	public static int remoteon, remoteoff;

	public static WirelessRemoteDevice remoteTransmitter;
	public static GuiRedstoneWirelessRemote guiRemote;

	public static boolean mouseDown, wasMouseDown, remotePulsing;
	private static List<BaseModOverride> overrides;
	
	static int ticksInGui;

	public static boolean initialize() {
		try {
			remoteTransmitters = new HashMap<EntityPlayer, WirelessRemoteDevice>();
			remoteWirelessCoords = new TreeMap<WirelessCoordinates, WirelessRemoteDevice>();

			registerConnHandler();

			addGuiOverride();
			addEtherOverride();
			addBaseModOverride();
			addRemoteOverride();

			loadConfig();
			loadItemTextures();

			initItem();

			addRecipes();
			addNames();
			return true;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(WirelessRemote.class
							.toString())).write("Initialization failed.",
					LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return false;
	}

	private static void addRemoteOverride() {
		RedstoneWirelessRemoteOverrideServer override = new RedstoneWirelessRemoteOverrideServer();
		WirelessRemoteDevice.addOverride(override);
	}

	private static void addBaseModOverride() {
		BaseModOverrideRemoteServer override = new BaseModOverrideRemoteServer();
		WirelessRedstone.addOverride(override);
	}

	private static void addGuiOverride() {
		BaseModOverrideRemote override = new BaseModOverrideRemote();
		WirelessRedstone.addOverride(override);
	}

	private static void registerConnHandler() {
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	private static void addEtherOverride() {
		RedstoneEtherOverrideRemoteSMP etherOverrideRemote = new RedstoneEtherOverrideRemoteSMP();
		RedstoneEther.getInstance().addOverride(etherOverrideRemote);
	}

	private static void initItem() {
		itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256))
				.setItemName("wirelessredstone.remote");
	}

	public static void loadItemTextures() {
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] { "i",
				"#", Character.valueOf('i'), Block.torchRedstoneActive,
				Character.valueOf('#'), WirelessRedstone.blockWirelessT });
	}

	public static void addNames() {
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	private static void loadConfig() {
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote")
				.get("PulseDurration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"Remote").get("MaxPulseThreads", Integer.class,
				new Integer(maxPulseThreads));
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq) {
		return remoteTransmitters.get(entityplayer) == null ? false
				: remoteTransmitters.get(entityplayer).getFreq() == freq;
	}

	public static WirelessRemoteData getDeviceData(String index, int id,
			String name, World world, EntityPlayer entityplayer) {
		index = index + "[" + id + "]";
		WirelessRemoteData data = (WirelessRemoteData) world.loadItemData(
				WirelessRemoteData.class, index);
		if (data == null) {
			data = new WirelessRemoteData(index);
			world.setItemData(index, data);
			data.setID(id);
			data.setName(name);
			data.setDimension(world);
			data.setFreq("0");
			data.setState(false);
		}
		return data;
	}

	public static WirelessRemoteData getDeviceData(ItemStack itemstack,
			World world, EntityPlayer entityplayer) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = "Wireless Remote";
		return getDeviceData(index, id, name, world, entityplayer);
	}

	public static void activateRemote(World world, EntityPlayer entityplayer) {
		if (remoteTransmitters.containsKey(entityplayer)) {
			if (remoteTransmitters.get(entityplayer).isBeingHeld())
				return;

			deactivateRemote(world, entityplayer);
		}
		remoteTransmitters.put(entityplayer, new WirelessRemoteDevice(world,
				entityplayer));
		WirelessRemoteDevice remote = remoteTransmitters.get(entityplayer);
		remoteWirelessCoords.put(remote.getCoords(), remote);
		remote.activate();
	}

	public static boolean deactivateRemote(World world,
			EntityPlayer entityplayer) {
		if (remoteTransmitters.containsKey(entityplayer)) {
			WirelessRemoteDevice remote = remoteTransmitters.get(entityplayer);
			remote.deactivate();
			remoteWirelessCoords.remove(remote.getCoords());
			remoteTransmitters.remove(entityplayer);
			return true;
		} else {
			return false;
		}
	}

	public static int getIconFromDamage(String name, int i) {
		return 0;
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			WirelessRemoteData deviceData) {
		PacketHandlerWirelessRemote.PacketHandlerOutput
				.sendWirelessRemoteGuiPacket(entityplayer, deviceData.getID(),
						deviceData.getFreq());
	}
}
