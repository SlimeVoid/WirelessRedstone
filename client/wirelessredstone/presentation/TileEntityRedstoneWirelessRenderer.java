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
package wirelessredstone.presentation;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityRedstoneWirelessRenderer extends
		TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		try {
			float f4 = 0.01F;

			GL11.glPushMatrix();
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2
					+ 1F + f4);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);

			renderFreq((TileEntityRedstoneWireless) tileentity);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef((float) d + 1F + f4, (float) d1 + 1.5F,
					(float) d2 + 0.5F);
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);

			renderFreq((TileEntityRedstoneWireless) tileentity);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2
					- f4);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, 1F * f4);

			renderFreq((TileEntityRedstoneWireless) tileentity);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef((float) d - f4, (float) d1 + 1.5F,
					(float) d2 + 0.5F);
			GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);

			renderFreq((TileEntityRedstoneWireless) tileentity);
			GL11.glPopMatrix();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"TileEntityRedstoneWirelessRenderer"
			).writeStackTrace(e);
		}
	}

	private void renderFreq(TileEntityRedstoneWireless tileentity) {
		FontRenderer fontrenderer = getFontRenderer();
		String s = tileentity.getFreq() + "";
		GL11.glDepthMask(false);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 50, 0);
		GL11.glDepthMask(true);
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
