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
package wirelessredstone.presentation.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.data.LoggerRedstoneWireless;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author Eurymachus
 */
public abstract class GuiRedstoneWireless extends GuiScreen {
	/**
	 * Current World
	 */
	protected World world;
	/**
	 * Current Player
	 */
	protected EntityPlayer entityplayer;
	/**
	 * Width
	 */
	protected int xSize;
	/**
	 * Height
	 */
	protected int ySize;
	/**
	 * GUI overrides.
	 */
	protected List<IGuiRedstoneWirelessOverride> overrides;

	/**
	 * Constructor.<br>
	 * Sets default width,height and initializes override list object.
	 */
	public GuiRedstoneWireless() {
		super();
		this.xSize = 177;
		this.ySize = 166;
		this.overrides = new ArrayList<IGuiRedstoneWirelessOverride>();
	}

	/**
	 * Adds a GUI override to the GUI screen.
	 * 
	 * @param override
	 *            GUI override.
	 */
	public void addOverride(IGuiRedstoneWirelessOverride override) {
		this.overrides.add(override);
	}

	/**
	 * Adds a selection of controls to be used in the GUI
	 * 
	 * Override to replace control set use super.addControls() if adding to
	 * control set
	 */
	protected abstract void addControls();

	/**
	 * Initializes the GUI.<br>
	 * Adds buttons.
	 */
	@Override
	public void initGui() {
		addControls();
		super.initGui();
	}

	/**
	 * Check is Mouse pointer is within the bounds of a given GuiButtonWireless
	 * 
	 * @param button
	 *            A GuiButtonWireless
	 * @param i
	 *            mouse X coordinate
	 * @param j
	 *            mouse Y coordinate
	 * @return
	 */
	private boolean isMouseOverButton(GuiButtonWireless button, int i, int j) {
		if (button != null)
			return button.inBounds(i, j);
		return false;
	}

	/**
	 * Fetches the Background Image to be used
	 * 
	 * @return Background Image
	 */
	protected abstract String getBackgroundImage();

	/**
	 * Fetches the Gui Name to display at the top of the Gui
	 * 
	 * @return Gui Name
	 */
	protected abstract String getGuiName();

	/**
	 * Fetches the frequency
	 * 
	 * @return Frequency.
	 */
	protected abstract Object getFreq();

	/**
	 * Sets the frequency
	 * 
	 * @param freq
	 *            frequency.
	 */
	protected abstract void setFreq(String freq);

	/**
	 * Draws the entire GUI to the screen.
	 * 
	 * @param i
	 *            mouse X coordinate
	 * @param j
	 *            mouse Y coordinate
	 * @param f
	 *            tick partial
	 */
	@Override
	public void drawScreen(int i, int j, float f) {
		try {
			drawDefaultBackground();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			drawGuiContainerBackgroundLayer(i, j, f);

			GL11.glPushMatrix();
			GL11.glTranslatef(k, l, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);

			RenderHelper.disableStandardItemLighting();

			GL11.glDisable(2896 /* GL_LIGHTING */);
			GL11.glDisable(2929 /* GL_DEPTH_TEST */);

			drawGuiName();
			drawGuiContainerForegroundLayer(i, j, f);

			GL11.glPopMatrix();

			super.drawScreen(i, j, f);

			drawButtonTooltips(i, j, f);

			GL11.glEnable(2896 /* GL_LIGHTING */);
			GL11.glEnable(2929 /* GL_DEPTH_TEST */);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"GuiRedstoneWireless"
			).writeStackTrace(e);
		}
	}

	/**
	 * Draw a bordered box around a string.<br>
	 * Outer height is always 10, inner 8.
	 * 
	 * @param x1
	 *            screen X coordinate, top left
	 * @param y1
	 *            screen Y coordinate, top left
	 * @param x2
	 *            screen X coordinate, bottom right
	 */
	protected void drawStringBorder(int x1, int y1, int x2) {
		drawRect(x1 - 3, y1 - 3, x2 + 3, y1 + 10, 0xff000000);
		drawRect(x1 - 2, y1 - 2, x2 + 2, y1 + 9, 0xffffffff);
	}

	/**
	 * Draws the Name of the Gui at the top of the Gui
	 */
	protected void drawGuiName() {
		drawStringBorder(
				(xSize / 2)
						- (fontRenderer.getStringWidth(this.getGuiName()) / 2),
				6,
				(xSize / 2)
						+ (fontRenderer.getStringWidth(this.getGuiName()) / 2));
		fontRenderer.drawString(getGuiName(),
				(xSize / 2)
						- (fontRenderer.getStringWidth(this.getGuiName()) / 2),
				6, 0x404040);
	}

	/**
	 * Draws the frequency at a distance at Y from the centred value
	 * 
	 * @param y
	 */
	protected void drawFrequency(int y) {
		fontRenderer.drawString(this.getFreq() + "", (xSize / 2)
				- (fontRenderer.getStringWidth(this.getFreq() + "") / 2),
				(ySize / 2) + y, 0x404040);
	}

	/**
	 * Draws the frequency with a border at a distance at Y from the centred
	 * value
	 * 
	 * Y the Y position of the text
	 */
	protected void drawFrequencyAndBox(int y) {
		drawStringBorder(
				(xSize / 2) - (fontRenderer.getStringWidth(getFreq() + "") / 2),
				(ySize / 2) + y,
				(xSize / 2) + (fontRenderer.getStringWidth(getFreq() + "") / 2));
		drawFrequency(y);
	}

	protected void drawFrequencyLabel(int y) {
		fontRenderer.drawString("Frequency",
				(xSize / 2) - (fontRenderer.getStringWidth("Frequency") / 2),
				y, 0x404040);
	}

	protected void drawFrequencyLabelAndBox(int y) {
		drawStringBorder(
				(xSize / 2) - (fontRenderer.getStringWidth("Frequency") / 2),
				y, (xSize / 2) + (fontRenderer.getStringWidth("Frequency") / 2));
		drawFrequencyLabel(y);
	}

	/**
	 * Draws the strings.<br>
	 * - Name.<br>
	 * - Frequency.
	 */
	protected void drawGuiContainerForegroundLayer(int i, int j, float f) {
		drawFrequencyLabelAndBox(32);
		drawFrequencyAndBox(-35);
	}

	/**
	 * Draws the background layer.
	 * 
	 * @param f
	 *            tick partial
	 */
	protected void drawGuiContainerBackgroundLayer(int i, int j, float f) {
		int tex = mc.renderEngine.getTexture(getBackgroundImage());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int sizeX = (width - xSize) / 2;
		int sizeY = (height - ySize) / 2;
		drawTexturedModalRect(sizeX, sizeY, 0, 0, xSize, ySize);
	}

	/**
	 * Draws the tooltips of a button when Mouse is hovered
	 * 
	 * @param i
	 *            x Coordinate
	 * @param j
	 *            y Coordinate
	 * @param f
	 *            tick partial
	 */
	private void drawButtonTooltips(int i, int j, float f) {
		for (int control = 0; control < this.controlList.size(); control++) {
			if (this.controlList.get(control) instanceof GuiButtonWireless) {
				GuiButtonWireless button = (GuiButtonWireless) this.controlList
						.get(control);

				if (this.isMouseOverButton(button, i, j)) {
					button.drawToolTip(fontRenderer, i, j);
				}
			}
		}
	}

	/**
	 * Action listener.<br>
	 * Triggers when a button was clicked on the GUI.<br>
	 * - Runs all override beforeFrequencyChange, exits if premature exit was
	 * returned, skipping the frequency from being set.
	 * 
	 * @param guibutton
	 *            button that was clicked
	 */
	@Override
	protected abstract void actionPerformed(GuiButton guibutton);

	/**
	 * Handles keyboard input.<br>
	 * If inventory key is pressed or ESC, close the GUI.
	 */
	@Override
	public void handleKeyboardInput() {
		try {
			super.handleKeyboardInput();

			if (Keyboard.getEventKeyState()) {
				int inventoryKey = 0;
				for (Object o : KeyBinding.keybindArray) {
					if (((KeyBinding) o).keyDescription.equals("key.inventory")) {
						inventoryKey = ((KeyBinding) o).keyCode;
						break;
					}
				}
				if (Keyboard.getEventKey() == inventoryKey
						|| Keyboard.getEventKey() == 28) {
					close();
					return;
				}
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"GuiRedstoneWireless"
			).writeStackTrace(e);
		}
	}

	/**
	 * Handles mouse input.<br>
	 * Close GUI on right click.
	 */
	@Override
	public void handleMouseInput() {
		try {
			super.handleMouseInput();
			if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState())
				close();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"GuiRedstoneWireless"
			).writeStackTrace(e);
		}
	}

	/**
	 * Closes the GUI.
	 */
	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"GuiRedstoneWireless"
			).writeStackTrace(e);
		}
	}

	/**
	 * Always returns false, prevents game from pausing when GUI is open.
	 * 
	 * @return false
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Refresh the current GUI
	 * 
	 * Removing and re-adding all listed controls
	 */
	public void refreshGui() {
		this.removeControls();
		this.addControls();
	}

	/**
	 * Clears the control list
	 * 
	 * Used in refreshGui()
	 */
	private void removeControls() {
		this.controlList.clear();
	}
}