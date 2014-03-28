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
package net.slimevoid.wirelessredstone.client.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.wirelessredstone.api.IActivateGuiOverride;
import net.slimevoid.wirelessredstone.api.IGuiRedstoneWirelessOverride;
import net.slimevoid.wirelessredstone.client.network.packets.executor.ClientEtherPacketRXAddExecutor;
import net.slimevoid.wirelessredstone.client.network.packets.executor.ClientEtherPacketTXAddExecutor;
import net.slimevoid.wirelessredstone.client.network.packets.executor.ClientTilePacketExecutor;
import net.slimevoid.wirelessredstone.client.overrides.ActivateGuiTileEntityOverride;
import net.slimevoid.wirelessredstone.client.overrides.RedstoneEtherOverrideSMP;
import net.slimevoid.wirelessredstone.client.overrides.TileEntityRedstoneWirelessOverrideSMP;
import net.slimevoid.wirelessredstone.client.presentation.BlockRedstoneWirelessRenderer;
import net.slimevoid.wirelessredstone.client.presentation.TileEntityRedstoneWirelessRenderer;
import net.slimevoid.wirelessredstone.client.presentation.gui.GuiRedstoneWirelessT;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.ether.RedstoneEther;
import net.slimevoid.wirelessredstone.inventory.ContainerRedstoneWireless;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneEther;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import net.slimevoid.wirelessredstone.network.packets.core.PacketIds;
import net.slimevoid.wirelessredstone.proxy.WRCommonProxy;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * WRClientProxy class
 * 
 * Executes client specific code
 * 
 * @author Eurymachus
 * 
 */
public class WRClientProxy extends WRCommonProxy {

    /**
     * Wireless Receiver GUI
     */
    // public static GuiRedstoneWirelessInventory guiWirelessR;
    /**
     * Wireless Transmitter GUI
     */
    // public static GuiRedstoneWirelessInventory guiWirelessT;

    private static List<IActivateGuiOverride> overrides;

    @Override
    public void init() {
        initGUIs();
        super.init();
    }

    /**
     * Initializes GUI objects.
     */
    public static void initGUIs() {
        // guiWirelessR = new GuiRedstoneWirelessR();
        // guiWirelessT = new GuiRedstoneWirelessT();
    }

    /**
     * Adds a GUI override to the Receiver.
     * 
     * @param override
     *            GUI override
     */
    public static void addGuiOverrideToReceiver(IGuiRedstoneWirelessOverride override) {
        LoggerRedstoneWireless.getInstance("WRClientProxy").write(true,
                                                                  "Override added to "
                                                                          // +
                                                                          // LoggerRedstoneWireless.filterClassName(guiWirelessR.getClass().toString())
                                                                          + " - "
                                                                          + LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
                                                                  LoggerRedstoneWireless.LogLevel.DEBUG);
        // guiWirelessR.addOverride(override);
    }

    /**
     * Adds a GUI override to the Transmitter.
     * 
     * @param override
     *            GUI override
     */
    public static void addGuiOverrideToTransmitter(IGuiRedstoneWirelessOverride override) {
        LoggerRedstoneWireless.getInstance("WRClientProxy").write(true,
                                                                  "Override added to "
                                                                          // +
                                                                          // LoggerRedstoneWireless.filterClassName(guiWirelessT.getClass().toString())
                                                                          + " - "
                                                                          + LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
                                                                  LoggerRedstoneWireless.LogLevel.DEBUG);
        // guiWirelessT.addOverride(override);
    }

    @Override
    public void registerRenderInformation() {
        RenderingRegistry.registerBlockHandler(BlockRedstoneWirelessRenderer.renderID,
                                               new BlockRedstoneWirelessRenderer());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiLib.GUIID_INVENTORY) {
            TileEntity tileentity = world.getTileEntity(x,
                                                        y,
                                                        z);
            if (tileentity != null) {
                if (tileentity instanceof TileEntityRedstoneWirelessT) {
                    // guiWirelessT.assTileEntity((TileEntityRedstoneWirelessT)
                    // tileentity);
                    return new GuiRedstoneWirelessT(new ContainerRedstoneWireless(((TileEntityRedstoneWirelessT) tileentity)));
                }
                if (tileentity instanceof TileEntityRedstoneWirelessR) {
                    // guiWirelessR.assTileEntity((TileEntityRedstoneWirelessR)
                    // tileentity);
                    // return guiWirelessR;
                    return new GuiRedstoneWirelessT(new ContainerRedstoneWireless(((TileEntityRedstoneWirelessR) tileentity)));
                }
            }
        }
        return null;
    }

    @Override
    public String getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir.getPath();
    }

    @Override
    public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
        ClientRegistry.bindTileEntitySpecialRenderer(clazz,
                                                     new TileEntityRedstoneWirelessRenderer());
    }

    @Override
    public void addOverrides() {
        overrides = new ArrayList();

        RedstoneEtherOverrideSMP etherOverride = new RedstoneEtherOverrideSMP();
        RedstoneEther.getInstance().addOverride(etherOverride);

        // GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new
        // GuiRedstoneWirelessInventoryOverrideSMP();
        // addGuiOverrideToReceiver(GUIOverride);
        // addGuiOverrideToTransmitter(GUIOverride);

        // BlockRedstoneWirelessOverrideSMP blockOverride = new
        // BlockRedstoneWirelessOverrideSMP();
        // WRCore.addOverrideToReceiver(blockOverride);
        // WRCore.addOverrideToTransmitter(blockOverride);

        TileEntityRedstoneWirelessOverrideSMP tileOverride = new TileEntityRedstoneWirelessOverrideSMP();
        TileEntityRedstoneWireless.addOverride(tileOverride);

        // RedstoneEtherOverrideSMP etherOverrideSMP = new
        // RedstoneEtherOverrideSMP();
        // RedstoneEther.getInstance().addOverride(etherOverrideSMP);

        IActivateGuiOverride openGuiOverride = new ActivateGuiTileEntityOverride();
        addOverride(openGuiOverride);

        // BaseModOverrideSMP baseModOverride = new BaseModOverrideSMP();
        // this.addOverride(baseModOverride);
    }

    /**
     * Adds a Base override to the The Mod.
     * 
     * @param override
     *            Mod override
     */
    public static void addOverride(IActivateGuiOverride override) {
        overrides.add(override);
    }

    @Override
    public void login(INetHandler handler, NetworkManager manager) {
        World world = FMLClientHandler.instance().getWorldClient();
        if (world != null) {
            PacketHelper.sendToServer(((new PacketRedstoneEther(PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString()))));
        }
    }

    @Override
    public void initPacketHandlers() {
        super.initPacketHandlers();
        // ///////////////////
        // Client Handlers //
        // ///////////////////

        // Ether Client Packet Executors
        WirelessRedstone.handler.getPacketHandler(PacketIds.ETHER).registerClientExecutor(PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
                                                                                          new ClientEtherPacketTXAddExecutor());
        WirelessRedstone.handler.getPacketHandler(PacketIds.ETHER).registerClientExecutor(PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
                                                                                          new ClientEtherPacketRXAddExecutor());
        WirelessRedstone.handler.getPacketHandler(PacketIds.TILE).registerClientExecutor(PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
                                                                                         new ClientTilePacketExecutor());
    }
}
