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
package net.slimevoid.wirelessredstone.device;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.core.lib.DeviceLib;
import net.slimevoid.wirelessredstone.ether.RedstoneEther;

public abstract class WirelessReceiverDevice extends WirelessDevice {

    public WirelessReceiverDevice(World world, EntityLiving entityliving, ItemStack itemstack) {
        super(world, entityliving, itemstack);
    }

    @Override
    public String getInventoryName() {
        return DeviceLib.RECEIVER;
    }

    @Override
    public void doActivateCommand() {
        RedstoneEther.getInstance().addReceiver(this.getWorld(),
                                                this.getCoords().getX(),
                                                this.getCoords().getY(),
                                                this.getCoords().getZ(),
                                                this.getFreq());
    }

    @Override
    public void doDeactivateCommand() {
        RedstoneEther.getInstance().remReceiver(this.getWorld(),
                                                this.getCoords().getX(),
                                                this.getCoords().getY(),
                                                this.getCoords().getZ(),
                                                this.getFreq());
    }
}
