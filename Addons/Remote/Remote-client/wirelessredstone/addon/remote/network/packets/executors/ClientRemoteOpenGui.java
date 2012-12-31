package wirelessredstone.addon.remote.network.packets.executors;

import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.client.network.packets.executor.ClientGuiDevicePacketExecutor;

public class ClientRemoteOpenGui extends ClientGuiDevicePacketExecutor {

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}

}
