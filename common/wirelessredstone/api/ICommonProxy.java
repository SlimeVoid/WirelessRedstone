package wirelessredstone.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.Player;

public interface ICommonProxy extends IGuiHandler {

	public void registerRenderInformation();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z);

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z);

	public String getMinecraftDir();

	void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);
}
