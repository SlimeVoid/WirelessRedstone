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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;

/**
 * A GUI Button<br>
 * PopupText is the mouseover text
 * 
 * @author Eurymachus
 * 
 */
public class GuiButtonWireless extends GuiButton {
    private String popupText;

    public GuiButtonWireless(int x, int y, int z, int l, int i1, String s, String popupText) {
        super(x, y, z, l, i1, s);
        this.popupText = popupText;
    }

    public GuiButtonWireless(int x, int y, int z, int l, int i1, String s) {
        super(x, y, z, l, i1, s);
    }

    public boolean inBounds(int x, int y) {
        return x >= xPosition && y >= yPosition && x < xPosition + width
               && y < yPosition + height;
    }

    public String getPopupText() {
        if (popupText != null && !popupText.isEmpty()) return this.popupText;
        return "";
    }

    /**
     * Draw a tooltip at the mouse pointer when.<br>
     * Called when the mouse pointer is within button bounds.
     * 
     * @param button
     *            A GuiButtonWireless
     * @param x
     *            mouse X coordinate
     * @param y
     *            mouse Y coordinate
     */
    protected void drawToolTip(Minecraft mc, int x, int y) {
        String buttonPopupText = this.getPopupText();
        if (!buttonPopupText.isEmpty()) {

            int l1 = mc.fontRendererObj.getStringWidth(buttonPopupText);
            int i = 0;
            int j = -10;
            int j2 = (x - i) + 12;
            int l2 = y - j - 12;
            int i3 = l1 + 5;
            int j3 = 8;

            zLevel = 300.0F;
            int k3 = 0xf0100010;
            drawGradientRect(j2 - 3,
                             l2 - 4,
                             j2 + i3 + 3,
                             l2 - 3,
                             k3,
                             k3);
            drawGradientRect(j2 - 3,
                             l2 + j3 + 3,
                             j2 + i3 + 3,
                             l2 + j3 + 4,
                             k3,
                             k3);
            drawGradientRect(j2 - 3,
                             l2 - 3,
                             j2 + i3 + 3,
                             l2 + j3 + 3,
                             k3,
                             k3);
            drawGradientRect(j2 - 4,
                             l2 - 3,
                             j2 - 3,
                             l2 + j3 + 3,
                             k3,
                             k3);
            drawGradientRect(j2 + i3 + 3,
                             l2 - 3,
                             j2 + i3 + 4,
                             l2 + j3 + 3,
                             k3,
                             k3);
            int l3 = 0x505000ff;
            int i4 = (l3 & 0xfefefe) >> 1 | l3 & 0xff000000;
            drawGradientRect(j2 - 3,
                             (l2 - 3) + 1,
                             (j2 - 3) + 1,
                             (l2 + j3 + 3) - 1,
                             l3,
                             i4);
            drawGradientRect(j2 + i3 + 2,
                             (l2 - 3) + 1,
                             j2 + i3 + 3,
                             (l2 + j3 + 3) - 1,
                             l3,
                             i4);
            drawGradientRect(j2 - 3,
                             l2 - 3,
                             j2 + i3 + 3,
                             (l2 - 3) + 1,
                             l3,
                             l3);
            drawGradientRect(j2 - 3,
                             l2 + j3 + 2,
                             j2 + i3 + 3,
                             l2 + j3 + 3,
                             i4,
                             i4);

            mc.fontRendererObj.drawSplitString(buttonPopupText,
                                            x + 15,
                                            y - 1,
                                            l1 * 2,
                                            0xFFFFFFFF);
            zLevel = 0.0F;
        }
    }

    /**
     * Used to return a String Texture based on the button state
     * 
     * @param state
     */
    protected ResourceLocation getButtonTexture(boolean state) {
        return state ? GuiLib.GUI_ON : GuiLib.GUI_OFF;
    }
}
