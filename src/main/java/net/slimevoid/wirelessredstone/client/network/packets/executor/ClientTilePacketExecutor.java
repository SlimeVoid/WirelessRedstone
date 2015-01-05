package net.slimevoid.wirelessredstone.client.network.packets.executor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.slimevoid.library.IPacketExecutor;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.wirelessredstone.client.presentation.gui.GuiRedstoneWirelessInventory;
import net.slimevoid.wirelessredstone.network.packets.PacketWirelessTile;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ClientTilePacketExecutor implements IPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketWirelessTile) {
            PacketWirelessTile wireless = (PacketWirelessTile) packet;
            TileEntity tileentity = wireless.getTarget(world);
            if (tileentity != null
                && tileentity instanceof TileEntityRedstoneWireless) {
                TileEntityRedstoneWireless tileentityredstonewireless = (TileEntityRedstoneWireless) tileentity;
                tileentityredstonewireless.handleData((PacketWirelessTile) packet);

                GuiScreen screen = FMLClientHandler.instance().getClient().currentScreen;
                if (screen != null
                    && screen instanceof GuiRedstoneWirelessInventory
                    && ((GuiRedstoneWirelessInventory) screen).compareInventory(tileentityredstonewireless)) {
                    ((GuiRedstoneWirelessInventory) screen).refreshGui();
                }
            }

        }
    }

}
