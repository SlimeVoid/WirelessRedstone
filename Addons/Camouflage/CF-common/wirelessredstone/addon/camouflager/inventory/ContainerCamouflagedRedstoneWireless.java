package wirelessredstone.addon.camouflager.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import wirelessredstone.addon.camouflager.inventory.slot.SlotBlock;
import wirelessredstone.tileentity.ContainerRedstoneWireless;

public class ContainerCamouflagedRedstoneWireless extends
		ContainerRedstoneWireless {

	InventoryPlayer	playerInventory;

	public ContainerCamouflagedRedstoneWireless(InventoryPlayer playerInventory, TileEntity tileentity) {
		super(tileentity);
		this.playerInventory = playerInventory;
		this.bindLocalInventory();
		this.bindPlayerInventory(	0,
									140);
	}

	protected void bindLocalInventory() {
		for (int row = 0; row < 6; row++) {
			int slotIndex = row;
			this.addSlotToContainer(new SlotBlock(this.redstoneWireless, slotIndex, (8 + row * 18 + 10), 58 + 10));
		}
	}

	protected void bindPlayerInventory(int playerColOffset, int playerRowOffset) {
		// Player inventory
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				int slotIndex = column + (row * 9);
				this.addSlotToContainer(new Slot(this.playerInventory, slotIndex, (8 + column * 18 + playerColOffset), (row * 18 + playerRowOffset)));
			}
		}

		// Hotbar inventory
		for (int row = 0; row < 9; ++row) {
			int slotIndex = row;
			this.addSlotToContainer(new Slot(this.playerInventory, slotIndex, (8 + row * 18 + playerColOffset), 58 + playerRowOffset));
		}
	}
}
