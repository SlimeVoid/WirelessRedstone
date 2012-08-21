package wirelessredstone.presentation;

import net.minecraft.src.Block;
import net.minecraft.src.BlockRedstoneRepeater;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class RenderBlockRedstoneWireless {
	public static boolean renderBlockRedstoneWireless(
			RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j,
			int k, Block block, int l)// (Block par1Block, int par2, int par3,
										// int par4)
	{
		int var5 = iblockaccess.getBlockMetadata(i, j, k);
		int var6 = var5 & 3;
		int var7 = (var5 & 12) >> 2;
		renderblocks.renderStandardBlock(block, i, j, k);
		Tessellator var8 = Tessellator.instance;
		var8.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, i, j,
				k));
		var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double var9 = -0.1875D;
		double var11 = 0.0D;
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;

		switch (var6) {
		case 0:
			var17 = -0.3125D;
			var13 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
			break;

		case 1:
			var15 = 0.3125D;
			var11 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
			break;

		case 2:
			var17 = 0.3125D;
			var13 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
			break;

		case 3:
			var15 = -0.3125D;
			var11 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
		}

		renderblocks.renderTorchAtAngle(block, i + var11, j + var9, k + var13,
				0.0D, 0.0D);
		renderblocks.renderTorchAtAngle(block, i + var15, j + var9, k + var17,
				0.0D, 0.0D);
		int var19 = block.getBlockTextureFromSide(1);
		int var20 = (var19 & 15) << 4;
		int var21 = var19 & 240;
		double var22 = (var20 / 256.0F);
		double var24 = ((var20 + 15.99F) / 256.0F);
		double var26 = (var21 / 256.0F);
		double var28 = ((var21 + 15.99F) / 256.0F);
		double var30 = 0.125D;
		double var32 = (i + 1);
		double var34 = (i + 1);
		double var36 = (i + 0);
		double var38 = (i + 0);
		double var40 = (i + 0);
		double var42 = (i + 1);
		double var44 = (i + 1);
		double var46 = (i + 0);
		double var48 = j + var30;

		if (var6 == 2) {
			var32 = var34 = (i + 0);
			var36 = var38 = (i + 1);
			var40 = var46 = (k + 1);
			var42 = var44 = (k + 0);
		} else if (var6 == 3) {
			var32 = var38 = (i + 0);
			var34 = var36 = (i + 1);
			var40 = var42 = (k + 0);
			var44 = var46 = (k + 1);
		} else if (var6 == 1) {
			var32 = var38 = (i + 1);
			var34 = var36 = (i + 0);
			var40 = var42 = (k + 1);
			var44 = var46 = (k + 0);
		}

		var8.addVertexWithUV(var38, var48, var46, var22, var26);
		var8.addVertexWithUV(var36, var48, var44, var22, var28);
		var8.addVertexWithUV(var34, var48, var42, var24, var28);
		var8.addVertexWithUV(var32, var48, var40, var24, var26);
		return true;
	}
}
