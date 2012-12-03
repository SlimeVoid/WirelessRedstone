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
package wirelessredstone.addon.remote.core;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;

public class mod_WirelessRemote extends BaseMod {
	public static BaseMod instance;

	public mod_WirelessRemote() {
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_WirelessRedstone")) {
			if (!WirelessRemote.isLoaded) {
				WirelessRemote.isLoaded = WirelessRemote.initialize();
			}
		}
	}

	@Override
	public boolean onTickInGame(float var1, Minecraft var2) {
		if (!WirelessRemote.isLoaded) {
			return true;
		} else {
			WirelessRemote.tick(var2);
			return true;
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstone";
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
