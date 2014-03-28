package net.slimevoid.wirelessredstone.client.presentation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockRedstoneWirelessRenderer implements
        ISimpleBlockRenderingHandler {

    public static int                                 renderID  = RenderingRegistry.getNextAvailableRenderId();

    private static List<ISimpleBlockRenderingHandler> overrides = new ArrayList<ISimpleBlockRenderingHandler>();

    public static void addOverride(ISimpleBlockRenderingHandler handler) {
        overrides.add(handler);
    }

    private static void renderBlockAsItem(Block par1Block, int metadata, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        par1Block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(par1Block);
        GL11.glRotatef(90.0F,
                       0.0F,
                       1.0F,
                       0.0F);
        GL11.glTranslatef(-0.5F,
                          -0.5F,
                          -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              -1.0F,
                              0.0F);
        renderer.renderFaceYNeg(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         0,
                                                                         metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              1.0F,
                              0.0F);
        renderer.renderFaceYPos(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         1,
                                                                         metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              0.0F,
                              -1.0F);
        renderer.renderFaceZNeg(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         2,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F,
                              0.0F,
                              1.0F);
        renderer.renderFaceZPos(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         3,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F,
                              0.0F,
                              0.0F);
        renderer.renderFaceXNeg(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         4,
                                                                         metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F,
                              0.0F,
                              0.0F);
        renderer.renderFaceXPos(par1Block,
                                0.0D,
                                0.0D,
                                0.0D,
                                renderer.getBlockIconFromSideAndMetadata(par1Block,
                                                                         5,
                                                                         metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F,
                          0.5F,
                          0.5F);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        renderBlockAsItem(block,
                          metadata,
                          renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        boolean wasHandled = false;
        for (ISimpleBlockRenderingHandler handler : overrides) {
            boolean isHandled = handler.renderWorldBlock(world,
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
            renderer.renderStandardBlock(block,
                                         x,
                                         y,
                                         z);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }

}
