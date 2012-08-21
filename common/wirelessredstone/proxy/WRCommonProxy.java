package wirelessredstone.proxy;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;

public class WRCommonProxy implements ICommonProxy {

	@Override
	public void registerRenderInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMinecraftDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
		// TODO Auto-generated method stub
		
	}

}
