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
package net.slimevoid.wirelessredstone.ether;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Wireless ether node.
 * 
 * @author ali4z
 */
public class RedstoneEtherNode implements Comparable<RedstoneEtherNode> {
    public EntityPlayer player;
    /**
     * World X coordinate.
     */
    public int          x;
    /**
     * World Y coordinate.
     */
    public int          y;
    /**
     * World Z coordinate.
     */
    public int          z;
    /**
     * State
     */
    public boolean      state;
    /**
     * Frequency
     */
    public Object       freq;
    /**
     * Unixtimestamp, milliseconds, added.
     */
    public long         time;

    /**
     * Constructor.
     * 
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     */
    public RedstoneEtherNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        state = false;
        freq = "0";
        time = System.currentTimeMillis();
    }

    /**
     * Constructor.
     * 
     * @param playerName
     *            Player Name
     * 
     *            public RedstoneEtherNode(EntityPlayer player) { this.player =
     *            player; state = false; freq = "0"; time =
     *            System.currentTimeMillis(); }
     */

    @Override
    public int compareTo(RedstoneEtherNode arg0) {
        if (arg0.x == x) {
            if (arg0.y == y) {
                if (arg0.z == z) return 0;
                else return z - arg0.z;
            } else return y - arg0.y;
        } else return x - arg0.x;
    }

    @Override
    public boolean equals(Object node) {
        if (node instanceof RedstoneEtherNode) return (((RedstoneEtherNode) node).x == x
                                                       && ((RedstoneEtherNode) node).y == y && ((RedstoneEtherNode) node).z == z);
        else return false;
    }

    @Override
    public String toString() {
        return "[" + freq + "] - (" + x + "," + y + "," + z + ") - " + state;
    }
}