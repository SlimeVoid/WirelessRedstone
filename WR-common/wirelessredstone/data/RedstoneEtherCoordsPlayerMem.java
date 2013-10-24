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
package wirelessredstone.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Memory storage singleton<br>
 * Used for fetching Player's coordinates based on name.
 * 
 * @author ali4z
 * 
 */
public class RedstoneEtherCoordsPlayerMem {
	private static RedstoneEtherCoordsPlayerMem		instance;
	private Map<String, PlayerEtherCoordsMemNode>	coords;
	private World									world;

	private RedstoneEtherCoordsPlayerMem(World world) {
		coords = new HashMap<String, PlayerEtherCoordsMemNode>();
		this.world = world;
	}

	/**
	 * Get the singleton instance.
	 * 
	 * @param world
	 *            World object
	 * @return Instance
	 */
	public static RedstoneEtherCoordsPlayerMem getInstance(World world) {
		if (instance == null || instance.world.hashCode() != world.hashCode()) instance = new RedstoneEtherCoordsPlayerMem(world);

		return instance;
	}

	/**
	 * Add a player coordinate to the memory.
	 * 
	 * @param entityplayer
	 *            The player.
	 * @param newcoords
	 *            Coordinates.
	 */
	public void addMem(EntityPlayer entityplayer, WirelessCoordinates newcoords) {
		PlayerEtherCoordsMemNode memnode = new PlayerEtherCoordsMemNode(entityplayer, newcoords);
		coords.put(	entityplayer.username,
					memnode);
	}

	/**
	 * Remove a player coordinate from the memory.
	 * 
	 * @param username
	 *            The player's username.
	 */
	public void remMem(String username) {
		coords.remove(username);
	}

	/**
	 * Alias for addMem()
	 * 
	 * @param entityplayer
	 *            The player.
	 * @param newcoords
	 *            Coordinates.
	 */
	public void setCoords(EntityPlayer entityplayer, WirelessCoordinates newcoords) {
		addMem(	entityplayer,
				newcoords);
	}

	/**
	 * Fetch coordinates for a given player.
	 * 
	 * @param entityplayer
	 *            The player
	 * @return coordinates
	 */
	public WirelessCoordinates getCoords(EntityPlayer entityplayer) {
		PlayerEtherCoordsMemNode node = coords.get(entityplayer.username);
		if (node == null) {
			addMem(	entityplayer,
					null);
			return null;
		} else {
			return node.coords;
		}
	}

	/**
	 * A memory node. Contains the player object and coordinates.
	 * 
	 * @author ali4z
	 * 
	 */
	public class PlayerEtherCoordsMemNode {
		EntityPlayer		entityplayer;
		WirelessCoordinates	coords;

		public PlayerEtherCoordsMemNode(EntityPlayer entityplayer, WirelessCoordinates coords) {
			this.entityplayer = entityplayer;
			this.coords = coords;
		}
	}
}