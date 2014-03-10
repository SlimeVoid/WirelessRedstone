package com.slimevoid.wirelessredstone.inventory;

import com.slimevoid.wirelessredstone.api.IWirelessDevice;

public abstract class ContainerRedstoneDevice extends ContainerRedstoneWireless {

    public ContainerRedstoneDevice(IWirelessDevice device) {
        super(device);
    }

    public IWirelessDevice getDevice() {
        return (IWirelessDevice) this.redstoneWireless;
    }

}
