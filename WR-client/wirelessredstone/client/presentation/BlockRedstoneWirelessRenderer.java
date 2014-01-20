package wirelessredstone.client.presentation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockRedstoneWirelessRenderer implements
		ISimpleBlockRenderingHandler {

	public static int									renderID	= RenderingRegistry.getNextAvailableRenderId();

	private static List<ISimpleBlockRenderingHandler>	overrides	= new ArrayList<ISimpleBlockRenderingHandler>();

	public static void addOverride(ISimpleBlockRenderingHandler handler) {
		overrides.add(handler);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		boolean wasHandled = false;
		for (ISimpleBlockRenderingHandler handler : overrides) {
			boolean isHandled = handler.renderWorldBlock(	world,
															x,
															y,
															z,
															block,
															modelId,
															renderer);
			if (!wasHandled && isHandled) {
				wasHandled = isHandled;
			}
		}
		if (!wasHandled) {
			renderer.renderStandardBlock(	block,
											x,
											y,
											z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

}
