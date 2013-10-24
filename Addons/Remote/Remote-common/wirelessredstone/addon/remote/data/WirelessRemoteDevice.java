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
package wirelessredstone.addon.remote.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.addon.remote.overrides.RedstoneWirelessRemoteOverride;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.network.ClientPacketHandler;
import wirelessredstone.data.WirelessCoordinates;
import wirelessredstone.device.WirelessTransmitterDevice;
import wirelessredstone.network.packets.PacketWirelessDevice;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author Eurymachus
 * 
 */
public class WirelessRemoteDevice extends WirelessTransmitterDevice {

	@SideOnly(Side.CLIENT)
	public static WirelessRemoteDevice							remoteTransmitter;

	public static HashMap<EntityLiving, IWirelessDevice>		remoteTransmitters;
	public static TreeMap<WirelessCoordinates, IWirelessDevice>	remoteWirelessCoords;

	protected int												slot;
	protected static List<RedstoneWirelessRemoteOverride>		overrides	= new ArrayList();

	protected WirelessRemoteDevice(World world, EntityLiving entity) {
		super(world, entity, null);
		if (entity instanceof EntityPlayer) {
			this.slot = ((EntityPlayer) entity).inventory.currentItem;
			ItemStack itemstack = ((EntityPlayer) entity).inventory.getStackInSlot(this.slot);
		}
	}

	public WirelessRemoteDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		super(world, entityliving, deviceData);
	}

	/*
	 * @Override public boolean isBeingHeld() { boolean flag =
	 * super.isBeingHeld(); if (flag) { if (this.getOwner() instanceof
	 * EntityPlayer) { return
	 * ((EntityPlayer)this.getOwner()).inventory.currentItem == this.slot &&
	 * flag; } else { return flag; } } return false; }
	 */

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
			super.activate(	world,
							entity);
		}
	}

	@Override
	public void deactivate(World world, Entity entity, boolean isForced) {
		if (entity instanceof EntityLiving) {
			super.deactivate(	world,
								entity,
								false);
			if (!world.isRemote && isForced
				&& remoteTransmitters.containsKey(entity)) {
				deactivateWirelessRemote(	world,
											(EntityLiving) entity);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void activatePlayerWirelessRemote(World world, EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityliving;
			if (remoteTransmitter != null) {
				boolean isHeld = remoteTransmitter.isBeingHeld();
				if (isHeld) {
					return;
				}
				deactivatePlayerWirelessRemote(	world,
												entityplayer);
			}
			remoteTransmitter = new WirelessRemoteDevice(world, entityplayer);
			remoteTransmitter.activate(	world,
										entityplayer);
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean deactivatePlayerWirelessRemote(World world, EntityLiving entityliving) {
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityliving;
			if (remoteTransmitter == null) {
				return false;
			} else {
				PacketWirelessDevice packet = new PacketWirelessDevice(remoteTransmitter.data);
				packet.setPosition(	remoteTransmitter.xCoord,
									remoteTransmitter.yCoord,
									remoteTransmitter.zCoord,
									0);
				packet.setCommand("deactivateRemote");
				packet.isForced(true);
				ClientPacketHandler.sendPacket(packet.getPacket());
				remoteTransmitter.deactivate(	world,
												entityplayer,
												false);
				remoteTransmitter = null;
				return true;
			}
		}
		return false;
	}

	public static void activateWirelessRemote(World world, EntityLiving entityliving) {
		if (remoteTransmitters.containsKey(entityliving)) {
			IWirelessDevice remote = remoteTransmitters.get(entityliving);
			if (((WirelessRemoteDevice) remote).isBeingHeld()) {
				return;
			}
			deactivateWirelessRemote(	world,
										entityliving);
		}
		WirelessRemoteDevice remoteTransmitter = new WirelessRemoteDevice(world, entityliving);
		remoteTransmitters.put(	entityliving,
								remoteTransmitter);
		if (remoteTransmitters.containsKey(entityliving)) {
			remoteWirelessCoords.put(	remoteTransmitter.getCoords(),
										remoteTransmitter);
			remoteTransmitter.activate(	world,
										entityliving);
		}
	}

	public static boolean deactivateWirelessRemote(World world, EntityLiving entityliving) {
		if (remoteTransmitters.containsKey(entityliving)) {
			IWirelessDevice remote = remoteTransmitters.get(entityliving);
			if (remoteWirelessCoords.containsKey(remote.getCoords())) {
				remoteWirelessCoords.remove(remote.getCoords());
			}
			remoteTransmitters.remove(entityliving);
			remote.deactivate(	world,
								entityliving,
								false);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PacketWirelessDevice getDevicePacket(IWirelessDeviceData devicedata) {
		return new PacketWirelessDevice(devicedata);
	}

	@Override
	protected String getActivateCommand() {
		return PacketRemoteCommands.remoteCommands.activate.toString();
	}

	@Override
	protected String getDeactivateCommand() {
		return PacketRemoteCommands.remoteCommands.deactivate.toString();
	}
}
