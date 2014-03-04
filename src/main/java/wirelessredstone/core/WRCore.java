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
package wirelessredstone.core;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wirelessredstone.api.IBlockRedstoneWirelessOverride;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.block.BlockRedstoneWirelessR;
import wirelessredstone.block.BlockRedstoneWirelessT;
import wirelessredstone.core.lib.BlockLib;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.NetworkEvent;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * WirelessRedstone class
 * 
 * To allow abstraction from the BaseMod code
 * 
 * @author Eurymachus & Ali4z
 * 
 */
public class WRCore {
    /**
     * Wireless Receiver Block
     */
    public static Block        blockWirelessR;
    /**
     * Wireless Transmitter Block
     */
    public static Block        blockWirelessT;

    /**
     * Wireless Receiver Block ID
     */
    public static int          rxID;
    /**
     * Wireless Transmitter Block ID
     */
    public static int          txID;

    /**
     * Wireless Transmitter Item texture.
     */
    public static int          spriteTItem;
    /**
     * Wireless Receiver Item texture.
     */
    public static int          spriteRItem;
    /**
     * Wireless Redstone Ether maximum nodes
     */
    public static int          maxEtherFrequencies = 10000;
    /**
     * Wireless Redstone load state
     */
    public static boolean      isLoaded            = false;

    /**
     * Wireless Redstone Creative Tab
     */
    public static CreativeTabs wirelessRedstone;

    /**
     * Fires off all the canons.<br>
     * Loads configurations and initializes objects. Loads ModLoader related
     * stuff.
     */
    public static boolean initialize() {

        // loadConfig();

        WirelessRedstone.proxy.init();

        PacketRedstoneWirelessCommands.registerCommands();

        // PacketWirelessDeviceCommands.registerCommands();

        WirelessRedstone.proxy.initPacketHandlers();

        initBlocks();

        WirelessRedstone.proxy.addOverrides();

        registerBlocks();

        WirelessRedstone.proxy.registerRenderInformation();

        WirelessRedstone.proxy.registerTileEntitySpecialRenderer(TileEntityRedstoneWireless.class);

        addRecipes();

        NetworkEvent.registerNetworkEvents();

        return true;

    }

    /**
     * Initializes Block objects.
     */
    private static void initBlocks() {
        blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F)).setBlockName("wirelessredstone.receiver");
        blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F)).setBlockName("wirelessredstone.transmitter");
    }

    /**
     * Registers the Blocks, block names and TileEntities
     */
    private static void registerBlocks() {
        GameRegistry.registerBlock(blockWirelessR,
                                   BlockLib.WIRELESS_RECEIVER);
        GameRegistry.registerTileEntityWithAlternatives(TileEntityRedstoneWirelessR.class,
                                                        BlockLib.WIRELESS_RECEIVER,
                                                        BlockLib.WIRELESS_RECEIVER_ALT);
        GameRegistry.registerBlock(blockWirelessT,
                                   BlockLib.WIRELESS_TRANSMITTER);
        GameRegistry.registerTileEntityWithAlternatives(TileEntityRedstoneWirelessT.class,
                                                        BlockLib.WIRELESS_TRANSMITTER,
                                                        BlockLib.WIRELESS_TRANSMITTER_ALT);
    }

    /**
     * Registers receipts with ModLoader<br>
     * - Receipt for Receiver.<br>
     * - Receipt for Transmitter.
     */
    private static void addRecipes() {
        GameRegistry.addRecipe(new ItemStack(blockWirelessR, 1),
                               new Object[] {
                                       "IRI",
                                       "RLR",
                                       "IRI",
                                       Character.valueOf('I'),
                                       Items.iron_ingot,
                                       Character.valueOf('R'),
                                       Items.redstone,
                                       Character.valueOf('L'),
                                       Blocks.lever });
        GameRegistry.addRecipe(new ItemStack(blockWirelessT, 1),
                               new Object[] {
                                       "IRI",
                                       "RTR",
                                       "IRI",
                                       Character.valueOf('I'),
                                       Items.iron_ingot,
                                       Character.valueOf('R'),
                                       Items.redstone,
                                       Character.valueOf('T'),
                                       Blocks.redstone_torch });
    }

    /**
     * Adds a Block override to the Receiver.
     * 
     * @param override
     *            Block override
     */
    public static void addOverrideToReceiver(IBlockRedstoneWirelessOverride override) {
        LoggerRedstoneWireless.getInstance("Wireless Redstone").write(true,
                                                                      "Override added to "
                                                                              + LoggerRedstoneWireless.filterClassName(WRCore.blockWirelessR.getClass().toString())
                                                                              + " - "
                                                                              + LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
                                                                      LoggerRedstoneWireless.LogLevel.DEBUG);

        ((BlockRedstoneWireless) WRCore.blockWirelessR).addOverride(override);
    }

    /**
     * Adds a Block override to the Transmitter.
     * 
     * @param override
     *            Block override
     */
    public static void addOverrideToTransmitter(IBlockRedstoneWirelessOverride override) {
        LoggerRedstoneWireless.getInstance("Wireless Redstone").write(true,
                                                                      "Override added to "
                                                                              + LoggerRedstoneWireless.filterClassName(WRCore.blockWirelessT.getClass().toString())
                                                                              + " - "
                                                                              + LoggerRedstoneWireless.filterClassName(override.getClass().toString()),
                                                                      LoggerRedstoneWireless.LogLevel.DEBUG);

        ((BlockRedstoneWireless) WRCore.blockWirelessT).addOverride(override);
    }

    public static boolean mergeItemStack(Container container, ItemStack stackToMerge, int slotStart, int slotEnd, boolean reverseOrder) {
        boolean stackMerged = false;
        int realSlotStart = slotStart;

        if (reverseOrder) {
            realSlotStart = slotEnd - 1;
        }

        Slot slot;
        ItemStack stackInSlot;

        if (stackToMerge.isStackable()) {
            while (stackToMerge.stackSize > 0
                   && (!reverseOrder && realSlotStart < slotEnd || reverseOrder
                                                                   && realSlotStart >= slotStart)) {
                slot = (Slot) container.inventorySlots.get(realSlotStart);
                stackInSlot = slot.getStack();

                if (stackInSlot != null
                    && stackInSlot.getItem().equals(stackToMerge.getItem())
                    && (!stackToMerge.getHasSubtypes() || stackToMerge.getItemDamage() == stackInSlot.getItemDamage())
                    && ItemStack.areItemStackTagsEqual(stackToMerge,
                                                       stackInSlot)) {
                    int totalStackSize = stackInSlot.stackSize
                                         + stackToMerge.stackSize;

                    if (totalStackSize <= stackToMerge.getMaxStackSize()
                        && totalStackSize <= slot.getSlotStackLimit()) {
                        stackToMerge.stackSize = 0;
                        stackInSlot.stackSize = totalStackSize;
                        slot.onSlotChanged();
                        stackMerged = true;
                    } else if (stackInSlot.stackSize < stackToMerge.getMaxStackSize()
                               && stackInSlot.stackSize < slot.getSlotStackLimit()) {
                        stackToMerge.stackSize -= stackToMerge.getMaxStackSize()
                                                  - stackInSlot.stackSize;
                        stackInSlot.stackSize = stackToMerge.getMaxStackSize();
                        slot.onSlotChanged();
                        stackMerged = true;
                    }
                }

                if (reverseOrder) {
                    --realSlotStart;
                } else {
                    ++realSlotStart;
                }
            }
        }

        if (stackToMerge.stackSize > 0) {
            if (reverseOrder) {
                realSlotStart = slotEnd - 1;
            } else {
                realSlotStart = slotStart;
            }

            while (!reverseOrder && realSlotStart < slotEnd || reverseOrder
                   && realSlotStart >= slotStart) {
                slot = (Slot) container.inventorySlots.get(realSlotStart);
                stackInSlot = slot.getStack();

                if (stackInSlot == null) {
                    slot.putStack(stackToMerge.copy());
                    if (stackToMerge.stackSize > slot.getSlotStackLimit()) {
                        slot.getStack().stackSize = slot.getSlotStackLimit();
                        stackToMerge.stackSize -= slot.getSlotStackLimit();
                    } else {
                        stackToMerge.stackSize = 0;
                        stackMerged = true;
                    }
                    slot.onSlotChanged();
                    break;
                }

                if (reverseOrder) {
                    --realSlotStart;
                } else {
                    ++realSlotStart;
                }
            }
        }

        return stackMerged;
    }
}
