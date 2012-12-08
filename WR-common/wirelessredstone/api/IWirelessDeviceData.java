package wirelessredstone.api;

import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import wirelessredstone.data.WirelessCoordinates;

public interface IWirelessDeviceData {

	/**
	 * Set the device's coordinates.
	 * 
	 * @param coords Device Coordinates.
	 */
	public void setCoords(WirelessCoordinates coords);

	/**
	 * Set the device's coordinates.
	 * 
	 * @param x the xPosition.
	 * @param y the yPosition.
	 * @param z the zPosition
	 */
	public void setCoords(int x, int y, int z);
	
	/**
	 * Set the device's owner based on a living entity
	 * 
	 * @param entityliving a living entity.
	 */
	public void setOwnerID(Entity entity);

	/**
	 * Set the device's Owner ID.
	 * 
	 * @param id Owner ID.
	 */
	public void setOwnerID(int ownerid);
	
	/**
	 * Set the device's ID based on a itemstack
	 * 
	 * @param itemstack The itemstack.
	 */
	public void setID(ItemStack itemstack);

	/**
	 * Set the device's ID.
	 * 
	 * @param id Device ID.
	 */
	public void setID(int id);

	/**
	 * Set the device type.
	 * 
	 * @param type Device type.
	 * 		e.g. "item.wirelessredstone.remote"
	 */
	public void setType(String type);

	/**
	 * Set the device's name.
	 * 
	 * @param name Device name.
	 */
	public void setName(String name);

	/**
	 * Set the device's dimension based on world's worldType.
	 * 
	 * @param world The world object.
	 */
	public void setDimension(World world);

	/**
	 * Set the device's frequency.
	 * 
	 * @param freq Device frequency.
	 * @return 
	 */
	public void setFreq(String freq);

	/**
	 * Set the device's state.
	 * 
	 * @param state Device state.
	 * @return 
	 */
	public void setState(boolean state);

	/**
	 * Get the device coordinates.
	 * 
	 * @return Device coordinates.
	 */
	public WirelessCoordinates getCoords();

	/**
	 * Get the owner.
	 * 
	 * @return entity.
	 */
	public Entity getOwner();

	/**
	 * Get the device type.
	 * 
	 * @return Device type.
	 */
	public String getType();

	/**
	 * Get the device ID.
	 * 
	 * @return Device ID.
	 */
	public int getID();

	/**
	 * Get the device name.
	 * 
	 * @return Device name.
	 */
	public String getName();

	/**
	 * Get the device's dimension<br>
	 * 0 for normal world -1 for hell
	 * 
	 * @return Device dimension.
	 */
	public Byte getDimension();

	/**
	 * Get the device's frequency.
	 * 
	 * @return Device frequency.
	 */
	public String getFreq();

	/**
	 * Get the device's state.
	 * 
	 * @return Device state.
	 */
	public boolean getState();
}
