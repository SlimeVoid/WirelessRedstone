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
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;
import net.slimevoid.wirelessredstone.ether.RedstoneEther;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

/**
 * Redstone Wireless Transmitter
 * 
 * @author ali4z
 * 
 */
public class BlockRedstoneWirelessT extends BlockRedstoneWireless {

    /**@Override
    public void registerIcons(IIconRegister iconRegister) {
        this.iconBuffer = new IIcon[2][6];
        this.iconBuffer[0][0] = iconRegister.registerIcon(IconLib.WIRELESS_BOTTOM_OFF);
        this.iconBuffer[0][1] = iconRegister.registerIcon(IconLib.WIRELESS_TOP_OFF);
        this.iconBuffer[0][2] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_OFF);
        this.iconBuffer[0][3] = iconRegister.registerIcon(IconLib.WIRELESS_TX_FRONT_OFF);
        this.iconBuffer[0][4] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_OFF);
        this.iconBuffer[0][5] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_OFF);
        this.iconBuffer[1][0] = iconRegister.registerIcon(IconLib.WIRELESS_BOTTOM_ON);
        this.iconBuffer[1][1] = iconRegister.registerIcon(IconLib.WIRELESS_TOP_ON);
        this.iconBuffer[1][2] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_ON);
        this.iconBuffer[1][3] = iconRegister.registerIcon(IconLib.WIRELESS_TX_FRONT_ON);
        this.iconBuffer[1][4] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_ON);
        this.iconBuffer[1][5] = iconRegister.registerIcon(IconLib.WIRELESS_TX_SIDE_ON);
    }**/

    /**
     * Constructor.<br>
     * Sets the Block ID.
     * 
     * @param i
     *            Block ID
     */
    public BlockRedstoneWirelessT(int x, float hardness, float resistance) {
        super(x, hardness, resistance);
        setStepSound(Block.soundTypeMetal);
    }

    /**
     * Stores state in metadata and marks Block for update.<br>
     * - Sets the transmitter state in the Ether.
     */
    @Override
    public void setState(World world, BlockPos pos, boolean state) {
        super.setState(world,
                       pos,
                       state);

        String freq = getFreq(world,
                              pos);
        RedstoneEther.getInstance().setTransmitterState(world,
                                                        pos.getX(),
                                                        pos.getY(),
                                                        pos.getZ(),
                                                        freq,
                                                        state);
    }

    /**
     * Changes the frequency.<br>
     * - Remove transmitter from old frequency.<br>
     * - Add transmitter to new frequency.<br>
     * - Set transmitter state in the Ether.
     */
    @Override
    public void changeFreq(World world, BlockPos pos, Object oldFreq, Object freq) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        // Remove transmitter from current frequency on the ether.
        RedstoneEther.getInstance().remTransmitter(world,
                                                   x,
                                                   y,
                                                   z,
                                                   oldFreq);

        // Add transmitter to the ether on new frequency.
        RedstoneEther.getInstance().addTransmitter(world,
                                                   x,
                                                   y,
                                                   z,
                                                   freq);
        RedstoneEther.getInstance().setTransmitterState(world,
                                                        x,
                                                        y,
                                                        z,
                                                        freq,
                                                        getState(world,
                                                                 pos));
    }

    /**
     * Is triggered after the Block and TileEntity is added to the world.<br>
     * - Add transmitter to ether.<br>
     * - Notify self of neighbor change. (forcing transmitter to check for
     * neighboring power sources)
     */
    @Override
    protected void onBlockRedstoneWirelessAdded(World world, BlockPos pos, IBlockState state) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        RedstoneEther.getInstance().addTransmitter(world,
                                                   x,
                                                   y,
                                                   z,
                                                   getFreq(world,
                                                           pos));

        onBlockRedstoneWirelessNeighborChange(world,
                                              pos,
                                              Blocks.redstone_wire);
    }

    /**
     * Is triggered after the Block is removed from the world and before the
     * TileEntity is removed.<br>
     * - Remove transmitter from ether.
     */
    @Override
    protected void onBlockRedstoneWirelessRemoved(World world, BlockPos pos, IBlockState state) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        RedstoneEther.getInstance().remTransmitter(world,
                                                   x,
                                                   y,
                                                   z,
                                                   getFreq(world,
                                                           pos));
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
            && tileentity instanceof TileEntityRedstoneWirelessT) {
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
     * Is triggered on Block's neighboring Block change.<br>
     * - Exit if the neighboring Block is not of the same type. - If Block is
     * getting powered directly or indirectly, and the power is not already on,
     * set the power on. - If Block is not getting powered directly or
     * indirectly, and the power is already on, set the power off.
     */
    @Override
    protected void onBlockRedstoneWirelessNeighborChange(IBlockAccess iblockaccess, BlockPos pos, Block neighbor) {
    	World world = (World) iblockaccess;
        if (neighbor.equals(this)) return;

        // It was not removed, can provide power and is indirectly getting
        // powered.
        if (neighbor != null
            && !neighbor.equals(Blocks.air)
            && !getState(iblockaccess,
                         pos)
            && (world.isBlockPowered(pos) || world.isBlockIndirectlyGettingPowered(pos) > 0)) setState(world,
                                                                                                       pos,
                                                                                                       true);
        // There are no powering entities, state is deactivated.
        else if (getState(world,
                          pos)
                 && !(world.isBlockPowered(pos) || world.isBlockIndirectlyGettingPowered(pos) > 0)) setState(world,
                                                                                                             pos,
                                                                                                             false);
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
            return getBlockRedstoneWirelessTextureFromSide(l);
        }
    }

    @Override
    protected IIcon getBlockRedstoneWirelessTextureFromSide(int l) {
        return this.getIconFromStateAndSide(0,
                                            l);
    }**/

    @Override
    protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity() {
        return new TileEntityRedstoneWirelessT();
    }

    /**
     * Does nothing.
     */
    @Override
    protected void updateRedstoneWirelessTick(World world, BlockPos pos, IBlockState state, Random random) {
    }

    /**
     * Always returns false.<br>
     * Transmitters never emitt power.
     */
    @Override
    protected int isRedstoneWirelessPoweringTo(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        return 0;
    }

    /**
     * Always returns false.<br>
     * Transmitters never emitt power.
     */
    @Override
    protected int isRedstoneWirelessIndirectlyPoweringTo(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        return 0;
    }

    @Override
    protected boolean isBlockRedstoneWirelessSolidOnSide(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    protected boolean isBlockRedstoneWirelessOpaqueCube() {
        return true;
    }
}
