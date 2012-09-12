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
package wirelessredstone.block;

import net.minecraft.src.ItemBlock;

/**
 * I forgot why this is here. Some nonsense about block ID's over 255.
 * 
 * @author ali4z
 * 
 */
public abstract class BlockItemRedstoneWireless extends ItemBlock {

	public BlockItemRedstoneWireless(int par1) {
		super(par1);
		setIconIndex(getRedstoneWirelessItemIndex());
	}

	protected abstract int getRedstoneWirelessItemIndex();
}
