package wirelessredstone.addon.remote.tickhandler;

import java.util.EnumSet;

import org.lwjgl.input.Mouse;

import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.presentation.gui.GuiRedstoneWirelessRemote;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {
	
	public static boolean mouseDown, wasMouseDown, remotePulsing;
	private static int ticksInGui;
	
	public static void processRemote(World world, EntityPlayer entityplayer,
			GuiScreen gui, MovingObjectPosition mop) {
		if (WirelessRemoteDevice.remoteTransmitter != null && !mouseDown && !remotePulsing) {
			//ThreadWirelessRemote.pulse(entityplayer, "hold");
			WirelessRemoteDevice.deactivatePlayerWirelessRemote(world, entityplayer);
		}

		if (mouseClicked()
				&& WirelessRemoteDevice.remoteTransmitter == null
				&& entityplayer.inventory.getCurrentItem() != null
				&& entityplayer.inventory.getCurrentItem().getItem() == WRemoteCore.itemRemote
				&& gui != null && gui instanceof GuiRedstoneWirelessRemote
				&& !entityplayer.isSneaking() && ticksInGui > 0)
			WirelessRemoteDevice.activatePlayerWirelessRemote(world, entityplayer);
	}

	public static boolean mouseClicked() {
		return mouseDown && !wasMouseDown;
	}

	public static void checkMouseClicks() {
		wasMouseDown = mouseDown;
		mouseDown = Mouse.isButtonDown(1);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		checkMouseClicks();
		processRemote(mc.theWorld, mc.thePlayer, mc.currentScreen,
				mc.objectMouseOver);
		if (mc.currentScreen == null)
			ticksInGui = 0;
		else
			++ticksInGui;
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Client Tickhandler - Wireless Redstone - Remote";
	}

}
