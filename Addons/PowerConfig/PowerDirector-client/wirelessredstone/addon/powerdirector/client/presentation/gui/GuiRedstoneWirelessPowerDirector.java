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
package wirelessredstone.addon.powerdirector.client.presentation.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import wirelessredstone.addon.powerdirector.network.packets.PacketPowerDirectorCommands;
import wirelessredstone.addon.powerdirector.network.packets.PacketPowerDirectorSettings;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.client.presentation.gui.GuiButtonBoolean;
import wirelessredstone.client.presentation.gui.GuiButtonWirelessExit;
import wirelessredstone.client.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiRedstoneWirelessPowerDirector extends
		GuiRedstoneWirelessInventory {
	protected List<IGuiRedstoneWirelessOverride>	powerOverrides;

	public GuiRedstoneWirelessPowerDirector() {
		super();
		powerOverrides = new ArrayList<IGuiRedstoneWirelessOverride>();
	}

	@Override
	public void addOverride(IGuiRedstoneWirelessOverride override) {
		this.powerOverrides.add(override);
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		return GuiLib.GUI_MEDIUM;
	}

	@Override
	protected void addControls() {
		buttonList = new ArrayList();
		buttonList.add(new GuiButtonBoolean(0, (width / 2) - 60, (height / 2) - 42, 20, 20, "N", inventory.isPoweringDirection(3), "North Face"));
		buttonList.add(new GuiButtonBoolean(1, (width / 2) - 40, (height / 2) - 42, 20, 20, "E", inventory.isPoweringDirection(4), "East Face"));
		buttonList.add(new GuiButtonBoolean(2, (width / 2) - 20, (height / 2) - 42, 20, 20, "S", inventory.isPoweringDirection(2), "South Face"));
		buttonList.add(new GuiButtonBoolean(3, (width / 2), (height / 2) - 42, 20, 20, "W", inventory.isPoweringDirection(5), "West Face"));
		buttonList.add(new GuiButtonBoolean(4, (width / 2) + 20, (height / 2) - 42, 20, 20, "U", inventory.isPoweringDirection(0), "Upward Face"));
		buttonList.add(new GuiButtonBoolean(5, (width / 2) + 40, (height / 2) - 42, 20, 20, "D", inventory.isPoweringDirection(1), "Downward Face"));

		buttonList.add(new GuiButtonBoolean(6, (width / 2) - 60, (height / 2), 20, 20, "N", inventory.isPoweringIndirectly(3), "North Face"));
		buttonList.add(new GuiButtonBoolean(7, (width / 2) - 40, (height / 2), 20, 20, "E", inventory.isPoweringIndirectly(4), "East Face"));
		buttonList.add(new GuiButtonBoolean(8, (width / 2) - 20, (height / 2), 20, 20, "S", inventory.isPoweringIndirectly(2), "South Face"));
		buttonList.add(new GuiButtonBoolean(9, (width / 2), (height / 2), 20, 20, "W", inventory.isPoweringIndirectly(5), "West Face"));
		buttonList.add(new GuiButtonBoolean(10, (width / 2) + 20, (height / 2), 20, 20, "U", inventory.isPoweringIndirectly(0), "Upward Face"));
		buttonList.add(new GuiButtonBoolean(11, (width / 2) + 40, (height / 2), 20, 20, "D", inventory.isPoweringIndirectly(1), "Downward Face"));

		buttonList.add(new GuiButtonWirelessExit(100, (((width - xSize) / 2)
														+ xSize - 13 - 1), (((height - ySize) / 2) + 1)));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (inventory instanceof TileEntityRedstoneWirelessR) {
			int dir = -1;
			int indir = -1;
			switch (guibutton.id) {
			case 0: // N
				dir = 3;
				break;
			case 1: // E
				dir = 4;
				break;
			case 2: // S
				dir = 2;
				break;
			case 3: // W
				dir = 5;
				break;
			case 4: // U
				dir = 0;
				break;
			case 5: // D
				dir = 1;
				break;
			case 6: // N
				indir = 3;
				break;
			case 7: // E
				indir = 4;
				break;
			case 8: // S
				indir = 2;
				break;
			case 9: // W
				indir = 5;
				break;
			case 10: // U
				indir = 0;
				break;
			case 11: // D
				indir = 1;
				break;
			case 100:
				close();
				break;
			}
			if (dir >= 0) {
				notifyServer(	PacketPowerDirectorCommands.powerConfigCommands.setDirection.toString(),
								dir);
				inventory.flipPowerDirection(dir);
				initGui();
			} else if (indir >= 0) {
				notifyServer(	PacketPowerDirectorCommands.powerConfigCommands.setInDirection.toString(),
								indir);
				inventory.flipIndirectPower(indir);
				initGui();
			}
		}
	}

	private void notifyServer(String command, int side) {
		PacketPowerDirectorSettings packet = new PacketPowerDirectorSettings(command, side, this.inventory);
		PacketDispatcher.sendPacketToServer(packet.getPacket());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j, float f) {
		drawStringBorder(	(xSize / 2)
									- (fontRenderer.getStringWidth("Power Direction") / 2),
							28,
							(xSize / 2)
									+ (fontRenderer.getStringWidth("Power Direction") / 2));
		fontRenderer.drawString("Power Direction",
								(xSize / 2)
										- (fontRenderer.getStringWidth("Power Direction") / 2),
								28,
								0x404040);

		drawStringBorder(	(xSize / 2)
									- (fontRenderer.getStringWidth("Indirect Power") / 2),
							70,
							(xSize / 2)
									+ (fontRenderer.getStringWidth("Indirect Power") / 2));
		fontRenderer.drawString("Indirect Power",
								(xSize / 2)
										- (fontRenderer.getStringWidth("Indirect Power") / 2),
								70,
								0x404040);
	}
}
