/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src.wirelessredstone.addon.remote.presentation;

import net.minecraft.src.GuiButton;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.ThreadWirelessRemote;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessDevice;

public class GuiRedstoneWirelessRemote extends GuiRedstoneWirelessDevice {

	public GuiRedstoneWirelessRemote() {
		super();
	}

	@Override
	protected void addControls() {
		super.addControls();
		controlList.add(new GuiButtonBoolean(20, (width / 2) - 20,
				(height / 2) + 5, 40, 20, "Pulse", false));
	}

	@Override
	protected String getBackgroundImage() {
		return "/gui/wifi_medium.png";
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);

		if (guibutton.id == 20) {
			ThreadWirelessRemote.pulse(WirelessRedstone.getPlayer(), "pulse");
			close();
		}
	}
}
