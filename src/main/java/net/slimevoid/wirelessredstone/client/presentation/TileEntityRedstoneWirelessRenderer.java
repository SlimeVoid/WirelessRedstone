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
package net.slimevoid.wirelessredstone.client.presentation;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

import org.lwjgl.opengl.GL11;

public class TileEntityRedstoneWirelessRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f, int a) {
        try {
            float f4 = 0.01F;

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F,
                              (float) y + 1.5F,
                              (float) z + 1F + f4);
            GL11.glScalef(f4,
                          -f4,
                          f4);
            GL11.glNormal3f(0.0F,
                            0.0F,
                            -1F * f4);

            renderFreq((TileEntityRedstoneWireless) tileentity);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 1F + f4,
                              (float) y + 1.5F,
                              (float) z + 0.5F);
            GL11.glRotatef(90F,
                           0.0F,
                           1.0F,
                           0.0F);
            GL11.glScalef(f4,
                          -f4,
                          f4);
            GL11.glNormal3f(0.0F,
                            0.0F,
                            -1F * f4);

            renderFreq((TileEntityRedstoneWireless) tileentity);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F,
                              (float) y + 1.5F,
                              (float) z - f4);
            GL11.glRotatef(180F,
                           0.0F,
                           1.0F,
                           0.0F);
            GL11.glScalef(f4,
                          -f4,
                          f4);
            GL11.glNormal3f(0.0F,
                            0.0F,
                            1F * f4);

            renderFreq((TileEntityRedstoneWireless) tileentity);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x - f4,
                              (float) y + 1.5F,
                              (float) z + 0.5F);
            GL11.glRotatef(270F,
                           0.0F,
                           1.0F,
                           0.0F);
            GL11.glScalef(f4,
                          -f4,
                          f4);
            GL11.glNormal3f(0.0F,
                            0.0F,
                            -1F * f4);

            renderFreq((TileEntityRedstoneWireless) tileentity);
            GL11.glPopMatrix();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("TileEntityRedstoneWirelessRenderer").writeStackTrace(e);
        }
    }

    private void renderFreq(TileEntityRedstoneWireless tileentity) {
        FontRenderer fontrenderer = this.getFontRenderer();
        String s = String.valueOf(tileentity.getFreq()) + "";
        GL11.glDepthMask(false);
        fontrenderer.drawString(s,
                                -fontrenderer.getStringWidth(s) / 2,
                                50,
                                0);
        GL11.glDepthMask(true);
        // GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
