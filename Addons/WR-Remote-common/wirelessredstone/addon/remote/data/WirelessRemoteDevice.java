package wirelessredstone.addon.remote.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.overrides.RedstoneWirelessRemoteOverride;
import wirelessredstone.api.IWirelessDevice;
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
	
	public static HashMap<EntityLiving, IWirelessDevice> remoteTransmitters;
	public static TreeMap<WirelessCoordinates, IWirelessDevice> remoteWirelessCoords;
	
	protected int slot;
	protected static List<RedstoneWirelessRemoteOverride> overrides = new ArrayList();

	protected WirelessRemoteDevice(World world, EntityLiving entity) {
		super(world, entity, null);
		if (entity instanceof EntityPlayer) {
			this.slot = ((EntityPlayer)entity).inventory.currentItem;
			ItemStack itemstack = ((EntityPlayer)entity).inventory.getStackInSlot(this.slot);
		}
	}

/*	@Override
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
	}*/

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
		super.doActivateCommand();
	}
	
	@Override
	public void doDeactivateCommand() {
		super.doDeactivateCommand();
	}
	
	@Override
	public void activate(World world, Entity entity) {
		if (entity instanceof EntityLiving) {
			super.activate(world, entity);
		}
	}
	
	@Override
	public void deactivate(World world, Entity entity) {
		System.out.println(world.isRemote + " | deactivate");
		if (entity instanceof EntityLiving) {
			super.deactivate(world, entity);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void activatePlayerWirelessRemote(World world, EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityliving;
			if (remoteTransmitter != null) {
				if (remoteTransmitter.isBeingHeld()) {
					System.out.println("isBeingHeld");
					return;
				}
				deactivatePlayerWirelessRemote(world, entityplayer);
			}
			remoteTransmitter = new WirelessRemoteDevice(world, entityplayer);
			remoteTransmitter.activate(world, entityplayer);
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean deactivatePlayerWirelessRemote(World world, EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityliving;
			if (remoteTransmitter == null) {
				return false;
			} else {
				remoteTransmitter.deactivate(world, entityplayer);
				PacketWirelessDevice packet = new PacketWirelessDevice(remoteTransmitter.data);
				packet.setCommand(PacketWirelessDeviceCommands.deviceCommands.deactivateTX.toString());
				packet.setPosition(remoteTransmitter.xCoord, remoteTransmitter.yCoord, remoteTransmitter.zCoord, 0);
				ClientPacketHandler.sendPacket((Packet250CustomPayload)packet.getPacket());
				remoteTransmitter = null;
				return true;
			}
		}
		return false;
	}

	public static void activateWirelessRemote(World world, EntityLiving entityliving) {
		System.out.println("Name: " + entityliving.getEntityName());
		System.out.println("No.Tx: " + remoteTransmitters.size());
		if (remoteTransmitters.containsKey(entityliving)) {
			if (((WirelessRemoteDevice)remoteTransmitters.get(entityliving)).isBeingHeld()) {
				return;
			}

			deactivateWirelessRemote(world, entityliving);
		}
		WirelessRemoteDevice remoteTransmitter = new WirelessRemoteDevice(world,
				entityliving);
		remoteTransmitters.put(entityliving, remoteTransmitter);
		if (remoteTransmitters.containsKey(entityliving)) {
			System.out.println("No.Tx: " + remoteTransmitters.size());
			remoteWirelessCoords.put(remoteTransmitter.getCoords(), remoteTransmitter);
			remoteTransmitter.activate(world, entityliving);
		}
	}

	public static boolean deactivateWirelessRemote(World world,
			EntityLiving entityliving) {
		if (remoteTransmitters.containsKey(entityliving)) {
			WirelessRemoteDevice remote = (WirelessRemoteDevice) remoteTransmitters.get(entityliving);
			remote.deactivate(world, entityliving);
			remoteWirelessCoords.remove(remote.getCoords());
			remoteTransmitters.remove(entityliving);
			return true;
		} else {
			return false;
		}
	}
}
