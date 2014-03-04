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
package wirelessredstone.ether;

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
    public int          i;
    /**
     * World Y coordinate.
     */
    public int          j;
    /**
     * World Z coordinate.
     */
    public int          k;
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
     * @param i
     *            world X coordinate
     * @param j
     *            world Y coordinate
     * @param k
     *            world Z coordinate
     */
    public RedstoneEtherNode(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
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
        if (arg0.i == i) {
            if (arg0.j == j) {
                if (arg0.k == k) return 0;
                else return k - arg0.k;
            } else return j - arg0.j;
        } else return i - arg0.i;
    }

    @Override
    public boolean equals(Object node) {
        if (node instanceof RedstoneEtherNode) return (((RedstoneEtherNode) node).i == i
                                                       && ((RedstoneEtherNode) node).j == j && ((RedstoneEtherNode) node).k == k);
        else return false;
    }

    @Override
    public String toString() {
        return "[" + freq + "] - (" + i + "," + j + "," + k + ") - " + state;
    }
}