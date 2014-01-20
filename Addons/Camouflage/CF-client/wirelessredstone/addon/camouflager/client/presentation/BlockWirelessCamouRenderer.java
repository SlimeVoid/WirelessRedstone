package wirelessredstone.addon.camouflager.client.presentation;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import slimevoidlib.util.helpers.BlockHelper;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockWirelessCamouRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (block instanceof BlockRedstoneWireless) {
			TileEntityRedstoneWireless tRW = (TileEntityRedstoneWireless) BlockHelper.getTileEntity(world,
																									x,
																									y,
																									z,
																									TileEntityRedstoneWireless.class);
			Block blockRef = CamouLib.getBlock(	world,
												tRW);
			if (blockRef != null
				&& !(blockRef instanceof BlockRedstoneWireless)) {
				renderer.renderBlockByRenderType(	blockRef,
													x,
													y,
													z);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return 0;
	}

}
