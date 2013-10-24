package wirelessredstone.addon.powerconfig.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.powerconfig.network.packets.PacketPowerConfigCommands;
import wirelessredstone.addon.powerconfig.network.packets.PacketPowerConfigSettings;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class PacketPowerConfigSettingsExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		TileEntity tileentity = packet.getTarget(world);
		if (tileentity != null
			&& tileentity instanceof TileEntityRedstoneWirelessR) {
			TileEntityRedstoneWirelessR tR = (TileEntityRedstoneWirelessR) tileentity;
			if (packet.getCommand().equals(PacketPowerConfigCommands.powerConfigCommands.setDirection.toString())) {
				tR.flipPowerDirection(packet.side);
			} else {
				tR.flipIndirectPower(packet.side);
			}
		}
	}

}
