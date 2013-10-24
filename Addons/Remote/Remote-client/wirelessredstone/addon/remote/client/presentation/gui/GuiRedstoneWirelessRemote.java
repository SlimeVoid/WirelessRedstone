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
package wirelessredstone.addon.remote.client.presentation.gui;

import net.minecraft.util.ResourceLocation;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessDevice;
import wirelessredstone.core.lib.GuiLib;

public class GuiRedstoneWirelessRemote extends GuiRedstoneWirelessDevice {

	public GuiRedstoneWirelessRemote() {
		super();
	}

	@Override
	protected ResourceLocation getBackgroundImage() {
		return GuiLib.GUI_SMALL;
	}

	@Override
	protected String getCommand() {
		return PacketRemoteCommands.remoteCommands.changeFreq.toString();
	}
}
