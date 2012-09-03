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
	
	public void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity);

	public String getMinecraftDir();

	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);
	
	public void addOverrides();

	public void activateGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity);

	World getWorld();

	EntityPlayer getPlayer();

	public void init();
}
