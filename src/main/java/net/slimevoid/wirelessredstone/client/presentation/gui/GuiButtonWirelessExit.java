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
import net.minecraft.util.ResourceLocation;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;

import org.lwjgl.opengl.GL11;

/**
 * GUI exit button.
 * 
 * @author ali4z
 * 
 */
public class GuiButtonWirelessExit extends GuiButtonWireless {
    /**
     * Constructor.<br>
     * Height and Width is always 13, text is empty.
     * 
     * @param x
     *            button index handle
     * @param y
     *            screen X coordinate
     * @param z
     *            screen Y coordinate
     */
    public GuiButtonWirelessExit(int x, int y, int z) {
        super(x, y, z, 13, 13, "");
    }

    /**
     * Renders the button to the screen.<br>
     * Uses gui/wifi_exit.png
     * 
     * @param minecraft
     *            minecraft instance
     * @param x
     *            mouse X coordinate
     * @param y
     *            mouse Y coordinate
     */
    @Override
    public void drawButton(Minecraft minecraft, int x, int y) {
        FontRenderer fontrenderer = minecraft.fontRenderer;

        minecraft.getTextureManager().bindTexture(this.getButtonTexture(false));
        GL11.glColor4f(1.0F,
                       1.0F,
                       1.0F,
                       1.0F);
        boolean flag = x >= xPosition && y >= yPosition
                       && x < xPosition + width && y < yPosition + height;
        int k = getHoverState(flag);
        drawTexturedModalRect(xPosition,
                              yPosition,
                              0,
                              ((k - 1) * 13),
                              13,
                              13);
        drawTexturedModalRect(xPosition + width / 2,
                              yPosition,
                              200 - width / 2,
                              ((k - 1) * 13),
                              width / 2,
                              height);
        mouseDragged(minecraft,
                     x,
                     y);
    }

    @Override
    protected ResourceLocation getButtonTexture(boolean state) {
        return GuiLib.GUI_EXIT;
    }
}
