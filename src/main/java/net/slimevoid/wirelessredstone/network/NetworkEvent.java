package net.slimevoid.wirelessredstone.network;

import net.minecraftforge.common.MinecraftForge;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class NetworkEvent {

    public static void registerNetworkEvents() {
        MinecraftForge.EVENT_BUS.register(new NetworkEvent());
    }

    @SubscribeEvent
    public void onNetworkEvent(ClientConnectedToServerEvent event) {
        WirelessRedstone.proxy.login(event.handler,
                                     event.manager);
    }

}
