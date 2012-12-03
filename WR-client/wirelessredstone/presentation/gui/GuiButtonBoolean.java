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

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

import org.lwjgl.opengl.GL11;

/**
 * A boolean GUI Button<br>
 * On/Off state decides button color.
 * 
 * @author ali4z & Eurymachus
 */
public class GuiButtonBoolean extends GuiButtonWireless {
	/**
	 * Button state
	 */
	protected boolean state;

	/**
	 * Constructor.
	 * 
	 * @param i
	 *            button index handle
	 * @param j
	 *            screen X coordinate
	 * @param k
	 *            screen Y coordinate
	 * @param l
	 *            width
	 * @param i1
	 *            height
	 * @param s
	 *            displayed text
	 * @param state
	 *            initial state
	 */
	public GuiButtonBoolean(int i, int j, int k, int l, int i1, String s,
			boolean state, String popupText) {
		super(i, j, k, l, i1, s, popupText);
		changeState(state);
	}

	public GuiButtonBoolean(int i, int j, int k, int l, int i1, String s,
			boolean state) {
		super(i, j, k, l, i1, s);
		changeState(state);
	}

	/**
	 * Changes the state of the button.
	 * 
	 * @param state
	 *            button state
	 */
	public void changeState(boolean state) {
		this.state = state;
	}

	/**
	 * Renders the button to the screen.
	 * 
	 * @param minecraft
	 *            minecraft instance
	 * @param i
	 *            mouse X coordinate
	 * @param j
	 *            mouse Y coordinate
	 */
	@Override
	public void drawButton(Minecraft minecraft, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		if (state) {
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */,
					minecraft.renderEngine.getTexture("/gui/guiOn.png"));
		} else {
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */,
					minecraft.renderEngine.getTexture("/gui/guiOff.png"));
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = inBounds(i, j);// >= xPosition && j >= yPosition && i <
										// xPosition + width && j < yPosition +
										// height;
		int k = getHoverState(flag);
		drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2,
				height);
		drawTexturedModalRect(xPosition + width / 2, yPosition,
				200 - width / 2, 46 + k * 20, width / 2, height);
		mouseDragged(minecraft, i, j);
		if (!enabled) {
			drawCenteredString(fontrenderer, displayString, xPosition + width
					/ 2, yPosition + (height - 8) / 2, 0xffa0a0a0);
		} else if (flag) {
			drawCenteredString(fontrenderer, displayString, xPosition + width
					/ 2, yPosition + (height - 8) / 2, 0xffffa0);
		} else {
			drawCenteredString(fontrenderer, displayString, xPosition + width
					/ 2, yPosition + (height - 8) / 2, 0xe0e0e0);
		}
	}
}
