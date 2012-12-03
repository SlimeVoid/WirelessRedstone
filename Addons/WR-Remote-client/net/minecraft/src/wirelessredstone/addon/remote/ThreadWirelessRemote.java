/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class ThreadWirelessRemote implements Runnable {
	protected World world;
	protected EntityPlayer entityplayer;
	protected String freq;
	protected String command;
	public static int tc = 0;

	public ThreadWirelessRemote(EntityPlayer entityplayer, String command) {
		this.world = entityplayer.worldObj;
		this.entityplayer = entityplayer;
		this.command = command;
	}

	@Override
	public void run() {
		tc++;
		WirelessRemote.remotePulsing = true;
		long pulseDuration;
		if (command.equals("pulse")) {
			pulseDuration = WirelessRemote.pulseTime;
			WirelessRemote.activateRemote(world, entityplayer);
		} else
			pulseDuration = 500;

		if (pulseDuration > 0) {
			try {
				Thread.sleep(pulseDuration);
			} catch (InterruptedException e) {
				LoggerRedstoneWireless.getInstance("WirelessRedstone.Remote")
						.writeStackTrace(e);
			}
		}
		WirelessRemote.remotePulsing = false;
		tc--;
	}

	public static void pulse(EntityPlayer entityplayer, String command) {
		if (tc < WirelessRemote.maxPulseThreads) {
			Thread thr = new Thread(new ThreadWirelessRemote(entityplayer,
					command));
			thr.setName("WirelessRemoteThread");
			thr.start();
		}
	}
}
