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
package wirelessredstone.addon.powerconfig.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.powerconfig.core.PowerConfigurator;
import wirelessredstone.addon.powerconfig.network.packets.PacketPowerConfigCommands;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.tileentity.ContainerRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PowerConfigCommonProxy implements ICommonProxy {

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRedstoneWireless(world.getBlockTileEntity(x,  y,  z));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity instanceof TileEntityRedstoneWireless) {
			PowerConfigurator.guiPowerC.assTileEntity((TileEntityRedstoneWireless) tileentity);
			return PowerConfigurator.guiPowerC;
		}
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return ".";
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {

	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
		// TODO :: Activate GUI
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		return null;
	}

	@Override
	public void init() {
		PacketPowerConfigCommands.registerCommands();
	}

	@Override
	public World getWorld(NetHandler handler) {
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
	}

	@Override
	public void initPacketHandlers() {
		/////////////////////
		// Server Executor //
		/////////////////////
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void addOverrides() {
	}

	@Override
	public void doSomething(String command, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}
}
