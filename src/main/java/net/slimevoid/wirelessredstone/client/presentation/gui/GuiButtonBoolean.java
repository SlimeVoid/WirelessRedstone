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
package net.slimevoid.wirelessredstone.client.presentation.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

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
     * @param x
     *            button index handle
     * @param y
     *            screen X coordinate
     * @param z
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
    public GuiButtonBoolean(int x, int y, int z, int l, int i1, String s, boolean state, String popupText) {
        super(x, y, z, l, i1, s, popupText);
        changeState(state);
    }

    public GuiButtonBoolean(int x, int y, int z, int l, int i1, String s, boolean state) {
        super(x, y, z, l, i1, s);
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
     * @param mc
     *            minecraft instance
     * @param x
     *            mouse X coordinate
     * @param y
     *            mouse Y coordinate
     */
    @Override
    public void drawButton(Minecraft mc, int x, int y) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            if (state) {
                mc.getTextureManager().bindTexture(this.getButtonTexture(state));
            } else {
                mc.getTextureManager().bindTexture(this.getButtonTexture(state));
            }

            GL11.glColor4f(1.0F,
                           1.0F,
                           1.0F,
                           1.0F);
            this.hovered = inBounds(x, y);// >= xPosition && j >= yPosition
                                              // && i <
            // xPosition + width && j < yPosition +
            // height;
            int k = getHoverState(this.hovered);
            drawTexturedModalRect(xPosition,
                                  yPosition,
                                  0,
                                  46 + k * 20,
                                  width / 2,
                                  height);
            drawTexturedModalRect(xPosition + width / 2,
                                  yPosition,
                                  200 - width / 2,
                                  46 + k * 20,
                                  width / 2,
                                  height);
            mouseDragged(mc,
                         x,
                         y);

            int l = 14737632;

            if (!this.enabled) {
                l = -6250336;/* 0xffa0a0a0 */
            } else if (this.hovered) {
                l = 16777120;/* 0xffffa0 */
            } else {
                l = 0xe0e0e0;
            }
            this.drawCenteredString(fontrenderer,
                                    displayString,
                                    xPosition + width / 2,
                                    yPosition + (height - 8) / 2,
                                    l);
        }
    }
}
