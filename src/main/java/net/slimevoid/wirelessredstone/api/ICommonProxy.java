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
package net.slimevoid.wirelessredstone.api;

import java.io.File;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
// import net.minecraft.network.INetworkManager;
// import net.minecraft.network.packet.NetHandler;
// import net.minecraft.network.packet.Packet1Login;
// import net.minecraft.tileentity.TileEntity;

/**
 * 
 * Abstraction of the common proxy, which is used for general mod functionality.
 * 
 * @author Eurymachus
 * 
 */
public interface ICommonProxy extends IGuiHandler {

    /**
     * Registers rendering information. Called in the core before registering
     * tileentity special renderers.
     */
    public void registerRenderInformation();

    /**
     * Returns the minecraft directory
     * 
     * @return Directory path of the minecraft installation.
     */
    public String getMinecraftDir();

    /**
     * Registers special renderers for TileEntities.
     * 
     * @param clazz
     *            A TileEntity child class.
     */
    public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);

    /**
     * Adds all overrides.
     */
    public void addOverrides();

    /**
     * Called on initialization.
     */
    public void init();

    /**
     * Initializes packet handlers.<br>
     * - Ether<br>
     * - Gui<br>
     * - Tile<br>
     * For Server and Client.
     */
    public void initPacketHandlers();

    /**
     * Registers a configuration file allows sided properties
     * 
     * @param configFile
     *            usually the suggested Configuration File
     */
    public void registerConfiguration(File configFile);

    /**
     * Called on player/client login
     * 
     * @param handler
     * @param manager
     * @param login
     */
    public void login(INetHandler handler, NetworkManager manager);
}
