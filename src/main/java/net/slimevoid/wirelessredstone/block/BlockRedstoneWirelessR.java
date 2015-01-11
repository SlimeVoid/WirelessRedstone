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
package net.slimevoid.wirelessredstone.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;
import net.slimevoid.wirelessredstone.ether.RedstoneEther;
import net.slimevoid.wirelessredstone.network.handlers.RedstoneEtherPacketHandler;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

/**
 * Wireless Receiver Block.
 * 
 * @author ali4z
 */
public class BlockRedstoneWirelessR extends BlockRedstoneWireless {
    private boolean initialSchedule;

    /**@Override
    public void registerIcons(IIconRegister iconRegister) {
        this.iconBuffer = new IIcon[2][6];
        this.iconBuffer[0][0] = iconRegister.registerIcon(IconLib.WIRELESS_BOTTOM_OFF);
        this.iconBuffer[0][1] = iconRegister.registerIcon(IconLib.WIRELESS_TOP_OFF);
        this.iconBuffer[0][2] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_OFF);
        this.iconBuffer[0][3] = iconRegister.registerIcon(IconLib.WIRELESS_RX_FRONT_OFF);
        this.iconBuffer[0][4] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_OFF);
        this.iconBuffer[0][5] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_OFF);
        this.iconBuffer[1][0] = iconRegister.registerIcon(IconLib.WIRELESS_BOTTOM_ON);
        this.iconBuffer[1][1] = iconRegister.registerIcon(IconLib.WIRELESS_TOP_ON);
        this.iconBuffer[1][2] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_ON);
        this.iconBuffer[1][3] = iconRegister.registerIcon(IconLib.WIRELESS_RX_FRONT_ON);
        this.iconBuffer[1][4] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_ON);
        this.iconBuffer[1][5] = iconRegister.registerIcon(IconLib.WIRELESS_RX_SIDE_ON);
    }**/

    /**
     * Constructor.<br>
     * Sets the Block ID.<br>
     * Tells the Block to tick on load.
     * 
     * @param i
     *            Block ID
     */
    public BlockRedstoneWirelessR(int x, float hardness, float resistance) {
        super(x, hardness, resistance);
        setStepSound(Block.soundTypeMetal);
        setTickRandomly(true);
        initialSchedule = true;
    }

    /**
     * Checks if the Block has already ticked once.
     */
    public boolean hasTicked() {
        return !this.initialSchedule;
    }

    /**
     * Changes the frequency.<br>
     * - Remove receiver from old frequency.<br>
     * - Add receiver to new frequency.<br>
     * - Update the tick.
     */
    @Override
    public void changeFreq(World world, BlockPos pos, Object oldFreq, Object freq) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        RedstoneEther.getInstance().remReceiver(world,
                                                x,
                                                y,
                                                z,
                                                oldFreq);
        RedstoneEther.getInstance().addReceiver(world,
                                                x,
                                                y,
                                                z,
                                                freq);
        updateTick(world,
                   pos,
                   world.getBlockState(pos),
                   world.rand);
    }

    /**
     * Is triggered after the Block and TileEntity is added to the world.<br>
     * - Add receiver to ether.<br>
     * - Notify all neighboring Blocks.<br>
     * - Update the tick.
     */
    @Override
    protected void onBlockRedstoneWirelessAdded(World world, BlockPos pos, IBlockState state) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        RedstoneEther.getInstance().addReceiver(world,
                                                x,
                                                y,
                                                z,
                                                getFreq(world,
                                                        pos));
        notifyNeighbors(world, pos, state.getBlock());

        updateTick(world,
                   pos,
                   state,
                   null);
    }

    /**
     * Is triggered after the Block is removed from the world and before the
     * TileEntity is removed.<br>
     * - Remove receiver from ether.<br>
     * - Notify all neighboring Blocks.
     */
    @Override
    protected void onBlockRedstoneWirelessRemoved(World world, BlockPos pos, IBlockState state) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        RedstoneEther.getInstance().remReceiver(world,
                                                x,
                                                y,
                                                z,
                                                getFreq(world,
                                                        pos));
        ;
    }

    /**
     * Is triggered on Block activation unless overrides exits prematurely.<br>
     * - Associates the TileEntity with the GUI. - Opens the GUI.
     */
    @Override
    protected boolean onBlockRedstoneWirelessActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumFacing side) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity != null
            && tileentity instanceof TileEntityRedstoneWirelessR) {
            entityplayer.openGui(WirelessRedstone.instance,
                                 GuiLib.GUIID_INVENTORY,
                                 world,
                                 x,
                                 y,
                                 z);
        }

        return true;
    }

    /**
     * Is triggered on Block's neighbouring Block change.<br>
     * - Update the tick if the neighbouring Block is not of the same type.
     */
    @Override
    protected void onBlockRedstoneWirelessNeighborChange(IBlockAccess iblockaccess, BlockPos pos, Block neighbor) {
        if (neighbor.equals(this)) return;

        updateTick((World) iblockaccess,
                   pos,
                   iblockaccess.getBlockState(pos),
                   null);
    }

    /**@Override
    protected IIcon getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess, int x, int y, int z, int l) {
        if (getState(iblockaccess,
                     x,
                     y,
                     z)) {
            return this.getIconFromStateAndSide(1,
                                                l);
        } else {
            return this.getBlockRedstoneWirelessTextureFromSide(l);
        }
    }

    @Override
    protected IIcon getBlockRedstoneWirelessTextureFromSide(int side) {
        return this.getIconFromStateAndSide(0,
                                            side);
    }**/

    @Override
    protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity() {
        return new TileEntityRedstoneWirelessR();
    }

    /**
     * Triggered on a Block update tick.<br>
     * If the state in the ether has changed from metadata state:<br>
     * - Update the metadata state.<br>
     * - Mark the Block for update.<br>
     * - Notify neighbors of changes.
     */
    @Override
    protected void updateRedstoneWirelessTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (initialSchedule) initialSchedule = false;
        if (world == null) return;
        String freq = getFreq(world,
                              pos);

        boolean oldState = this.getState(world,
                                    	 pos);
        boolean newState = RedstoneEther.getInstance().getFreqState(world,
                                                                    freq);

        if (newState != oldState) {
            setState(world,
                     pos,
                     newState);
            world.markBlockForUpdate(pos);
            notifyNeighbors(world,
                            pos,
                            this);

            if (!world.isRemote) {
                TileEntity entity = world.getTileEntity(pos);
                if (entity instanceof TileEntityRedstoneWireless) RedstoneEtherPacketHandler.sendEtherTileToAll((TileEntityRedstoneWireless) entity,
                                                                                                                world);
            }
        }
    }

    /**
     * Checks whether or not the Block is directly emitting power to a
     * direction.<br>
     * - Query metadata state. - Query the TileEntity for directional state.
     */
    @Override
    protected int isRedstoneWirelessPoweringTo(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityRedstoneWireless) {
            return (((TileEntityRedstoneWireless) entity).isPoweringDirection(side) && getState(world,
                                                                                             	pos)) ? 16 : 0;
        }
        return 0;
    }

    /**
     * Checks whether or not the Block is indirectly emitting power to a
     * direction.<br>
     * - Query directional state. - Query the TileEntity for indirect
     * directional state.
     */
    @Override
    protected int isRedstoneWirelessIndirectlyPoweringTo(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityRedstoneWireless) {
            if (!((TileEntityRedstoneWireless) entity).isPoweringIndirectly(side)) return 0;
            else return isProvidingStrongPower(world,
                                               pos,
                                               state,
                                               side);
        }
        return 0;
    }

    @Override
    protected boolean isBlockRedstoneWirelessSolidOnSide(IBlockAccess iblockaccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    protected boolean isBlockRedstoneWirelessOpaqueCube() {
        return true;
    }
}
