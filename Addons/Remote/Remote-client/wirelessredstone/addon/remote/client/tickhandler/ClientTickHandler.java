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
package wirelessredstone.addon.remote.client.tickhandler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;

import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.inventory.WirelessRemoteDevice;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

	public static boolean	mouseDown, wasMouseDown, remotePulsing;
	Minecraft				mc	= FMLClientHandler.instance().getClient();

	public static void processRemote(World world, EntityPlayer entityplayer, GuiScreen gui, MovingObjectPosition mop) {
		if (world.isRemote) {
			if (WirelessRemoteDevice.remoteTransmitter != null && !mouseDown
				&& !remotePulsing) {
				// ThreadWirelessRemote.pulse(entityplayer, "hold");
				WirelessRemoteDevice.deactivatePlayerWirelessRemote(world,
																	entityplayer);
			}

			if (mouseClicked()
				&& WirelessRemoteDevice.remoteTransmitter == null
				&& entityplayer.inventory.getCurrentItem() != null
				&& entityplayer.inventory.getCurrentItem().getItem() == WRemoteCore.itemRemote
				&& !entityplayer.isSneaking()) {
				WirelessRemoteDevice.activatePlayerWirelessRemote(	world,
																	entityplayer);
			}
		}
	}

	public static boolean mouseClicked() {
		return mouseDown && !wasMouseDown;
	}

	public static void checkMouseClicks() {
		wasMouseDown = mouseDown;
		mouseDown = Mouse.isButtonDown(1);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// checkMouseClicks();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (mc.theWorld != null && mc.theWorld.isRemote) {
			// processRemote( mc.theWorld,
			// mc.thePlayer,
			// mc.currentScreen,
			// mc.objectMouseOver);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Client Tickhandler - Wireless Redstone - Remote";
	}

}
