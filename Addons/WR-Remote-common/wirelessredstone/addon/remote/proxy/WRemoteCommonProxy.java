package wirelessredstone.addon.remote.proxy;

import java.util.HashMap;
import java.util.TreeMap;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetHandler;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.addon.remote.api.IRemoteCommonProxy;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDevice;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.handlers.ServerGuiPacketHandler;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class WRemoteCommonProxy implements IRemoteCommonProxy {

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
	public void openGUI(World world, EntityPlayer entityplayer, TileEntity tileentity) {
		if (!world.isRemote) {
			if (tileentity instanceof TileEntityRedstoneWireless) {
				ServerGuiPacketHandler.sendGuiPacketTo(
						(EntityPlayerMP) entityplayer,
						(TileEntityRedstoneWireless) tileentity);
			}
		}
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntity tileentity) {
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
		WirelessRemoteDevice.remoteTransmitters = new HashMap();
		WirelessRemoteDevice.remoteWirelessCoords = new TreeMap();
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
	public void activateRemote(World world, EntityLiving entityliving) {
		WirelessRemoteDevice.activateWirelessRemote(world, entityliving);
	}

	@Override
	public boolean deactivateRemote(World world, EntityLiving entityliving) {
		return WirelessRemoteDevice.deactivateWirelessRemote(world, entityliving);
	}

	@Override
	public void addOverrides() {
		RedstoneEther.getInstance().addOverride(new RedstoneEtherOverrideRemote());
	}

	@Override
	public boolean isRemoteOn(World world, EntityPlayer entityplayer, String freq) {
		if (WirelessRemoteDevice.remoteTransmitters.containsKey(entityplayer)) {
			IWirelessDevice remote = WirelessRemoteDevice.remoteTransmitters.get(entityplayer);
			return remote.getFreq() == freq;
		}
		return false;
	}
}
