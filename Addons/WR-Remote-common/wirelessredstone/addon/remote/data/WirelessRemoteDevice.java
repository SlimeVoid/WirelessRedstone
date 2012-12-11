package wirelessredstone.addon.remote.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.overrides.RedstoneWirelessRemoteOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.WirelessCoordinates;
import wirelessredstone.device.WirelessDevice;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.device.WirelessTransmitterDevice;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;


import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

/**
 * 
 * @author Eurymachus
 * 
 */
public class WirelessRemoteDevice extends WirelessTransmitterDevice {
	
	@SideOnly(Side.CLIENT)
	public static WirelessRemoteDevice remoteTransmitter;
	public static HashMap<EntityLiving, WirelessRemoteDevice> remoteTransmitters = new HashMap();
	public static TreeMap<WirelessCoordinates, WirelessRemoteDevice> remoteWirelessCoords = new TreeMap();
	
	protected int slot;
	protected static List<RedstoneWirelessRemoteOverride> overrides = new ArrayList();

	protected WirelessRemoteDevice(World world, EntityLiving entity) {
		super(world, entity);
		if (entity instanceof EntityPlayer) {
			this.slot = ((EntityPlayer)entity).inventory.currentItem;
			ItemStack itemstack = ((EntityPlayer)entity).inventory.getStackInSlot(this.slot);
		}
	}

	@Override
	public boolean isBeingHeld() {
		boolean flag = super.isBeingHeld();
		if (flag) {
			if (this.getOwner() instanceof EntityPlayer) {
				return ((EntityPlayer)this.getOwner()).inventory.currentItem == this.slot
						&& flag;
			} else {
				return flag;
			}
		}
		return false;
	}

	/**
	 * Adds a Remote override to the Remote.
	 * 
	 * @param override
	 *            Remote override.
	 */
	public static void addOverride(RedstoneWirelessRemoteOverride override) {
		overrides.add(override);
	}

	@Override
	public Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}

	@Override
	public String getName() {
		return "Wireless Remote";
	}
	
	@Override
	public void doActivateCommand() {
		PacketWirelessDevice packet = new PacketWirelessDevice(
				this.getCoords().getX(),
				this.getCoords().getY(),
				this.getCoords().getZ(),
				this.data);
		packet.setCommand(PacketWirelessDeviceCommands.deviceCommands.activateTX.toString());
		ClientPacketHandler.sendPacket((Packet250CustomPayload)packet.getPacket());
	}
	
	@Override
	public void doDeactivateCommand() {
		PacketWirelessDevice packet = new PacketWirelessDevice(
				this.getCoords().getX(),
				this.getCoords().getY(),
				this.getCoords().getZ(),
				this.data);
		packet.setCommand(PacketWirelessDeviceCommands.deviceCommands.deactivateTX.toString());
		ClientPacketHandler.sendPacket((Packet250CustomPayload)packet.getPacket());
		
	}
	
	@Override
	public void activate(World world, Entity entity) {
		if (entity instanceof EntityLiving) {
			super.activate(world, entity);
		}
	}
	
	@Override
	public void deactivate(World world, Entity entity) {
		if (entity instanceof EntityLiving) {
			super.deactivate(world, entity);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void activatePlayerWirelessRemote(World world, EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityliving;
			if (entityplayer.entityId == ModLoader.getMinecraftInstance().thePlayer.entityId) {
				if (remoteTransmitter != null) {
					if (remoteTransmitter.isBeingHeld())
						return;
		
					WRemoteCore.proxy.deactivateRemote(world, entityplayer);
				}
				remoteTransmitter = new WirelessRemoteDevice(world, entityplayer);
				remoteTransmitter.activate(world, entityplayer);
			}
		}
	}

	public static void activateWirelessRemote(World world, EntityLiving entityliving) {
		if (remoteTransmitters.containsKey(entityliving)) {
			if (remoteTransmitters.get(entityliving).isBeingHeld())
				return;

			WRemoteCore.proxy.deactivateRemote(world, entityliving);
		}
		remoteTransmitters.put(entityliving, new WirelessRemoteDevice(world,
				entityliving));
		WirelessRemoteDevice remote = remoteTransmitters.get(entityliving);
		remoteWirelessCoords.put(remote.getCoords(), remote);
		remote.activate(world, entityliving);
	}

	@SideOnly(Side.CLIENT)
	public static boolean deactivatePlayerWirelessRemote(World world,
			EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityliving;
			if (entityplayer.entityId == ModLoader.getMinecraftInstance().thePlayer.entityId) {
				if (remoteTransmitter == null) {
					return false;
				} else {
					remoteTransmitter.deactivate(world, entityplayer);
					remoteTransmitter = null;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean deactivateWirelessRemote(World world,
			EntityLiving entityliving) {
		if (remoteTransmitters.containsKey(entityliving)) {
			WirelessRemoteDevice remote = remoteTransmitters.get(entityliving);
			remote.deactivate(world, entityliving);
			remoteWirelessCoords.remove(remote.getCoords());
			remoteTransmitters.remove(entityliving);
			return true;
		} else {
			return false;
		}
	}
}
