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
package wirelessredstone.network.packets.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class PacketTileEntity extends PacketUpdate {

	public PacketTileEntity() {
		super(PacketIds.TILE);
	}

	public TileEntity getTileEntity(World world) {
		return world.getBlockTileEntity(
				this.xPosition,
				this.yPosition,
				this.zPosition);
	}

	@Override
	public boolean targetExists(World world) {
		if (world.blockExists(this.xPosition, this.yPosition, this.zPosition)) {
			return true;
		}
		return false;
	}
}
