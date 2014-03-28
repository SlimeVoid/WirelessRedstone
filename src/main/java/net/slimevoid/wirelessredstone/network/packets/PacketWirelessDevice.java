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
package net.slimevoid.wirelessredstone.network.packets;

import net.minecraft.world.World;
import net.slimevoid.library.network.PacketPayload;
import net.slimevoid.wirelessredstone.api.IWirelessDevice;
import net.slimevoid.wirelessredstone.network.packets.core.PacketIds;

public class PacketWirelessDevice extends PacketWireless {

    public PacketWirelessDevice() {
        super(PacketIds.DEVICE);
    }

    public PacketWirelessDevice(IWirelessDevice device) {
        this();
        this.payload = new PacketPayload(0, 0, 1, 1);
        this.setFreq(device.getFreq());
        this.setState(device.getState());
    }

    public PacketWirelessDevice(String freq, boolean state) {
        this();
        this.payload = new PacketPayload(0, 0, 1, 1);
        this.setFreq(freq);
        this.setState(state);
    }

    @Override
    public boolean targetExists(World world) {
        return true;
    }
}
