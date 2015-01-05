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
package net.slimevoid.wirelessredstone.client.presentation.gui;

import net.minecraft.client.gui.GuiButton;
import net.slimevoid.wirelessredstone.api.IGuiRedstoneWirelessInventoryOverride;
import net.slimevoid.wirelessredstone.api.IGuiRedstoneWirelessOverride;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.inventory.ContainerRedstoneWireless;
import net.slimevoid.wirelessredstone.network.handlers.RedstoneEtherPacketHandler;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author ali4z & Eurymachus
 */
public abstract class GuiRedstoneWirelessInventory extends
        GuiRedstoneWirelessContainer {
    /**
     * Associated TileEntity
     */
    protected TileEntityRedstoneWireless inventory;

    /**
     * Constructor.<br>
     * Sets default width and height.
     */
    public GuiRedstoneWirelessInventory(ContainerRedstoneWireless container) {
        super(container);
        this.inventory = (TileEntityRedstoneWireless) container.redstoneWireless;
        this.xSize = 177;
        this.ySize = 166;
    }

    @Override
    protected void drawForegroundObjects(int i, int k) {
        drawFrequencyLabelAndBox(32);
        drawFrequencyAndBox(-35);
    }

    /**
     * Draws the frequency at a distance at Y from the centred value
     * 
     * @param y
     */
    protected void drawFrequency(int y) {
        fontRendererObj.drawString(this.getFreq() + "",
                                   (xSize / 2)
                                           - (fontRendererObj.getStringWidth(this.getFreq()
                                                                             + "") / 2),
                                   (ySize / 2) + y,
                                   0x404040);
    }

    /**
     * Draws the frequency with a border at a distance at Y from the centred
     * value
     * 
     * Y the Y position of the text
     */
    protected void drawFrequencyAndBox(int y) {
        drawStringBorder((xSize / 2)
                                 - (fontRendererObj.getStringWidth(getFreq()
                                                                   + "") / 2),
                         (ySize / 2) + y,
                         (xSize / 2)
                                 + (fontRendererObj.getStringWidth(getFreq()
                                                                   + "") / 2));
        drawFrequency(y);
    }

    protected void drawFrequencyLabel(int y) {
        fontRendererObj.drawString("Frequency",
                                   (xSize / 2)
                                           - (fontRendererObj.getStringWidth("Frequency") / 2),
                                   y,
                                   0x404040);
    }

    protected void drawFrequencyLabelAndBox(int y) {
        drawStringBorder((xSize / 2)
                                 - (fontRendererObj.getStringWidth("Frequency") / 2),
                         y,
                         (xSize / 2)
                                 + (fontRendererObj.getStringWidth("Frequency") / 2));
        drawFrequencyLabel(y);
    }

    /**
     * Associates a TileEntity to the GUI.
     * 
     * @param tileentity
     *            TileEntity to be associated
     */
    public void assTileEntity(TileEntityRedstoneWireless tileentity) {
        inventory = tileentity;
    }

    /**
     * Compares the Gui inventory with an external inventory
     * 
     * Used to check whether a player is accessing a particular inventory
     * 
     * @param tileentity
     *            the Inventory to compare
     * @return true if x, y, z of inventories are the same or false if not.
     */
    public boolean compareInventory(TileEntityRedstoneWireless tileentity) {
        if (this.inventory != null) {
            if (this.inventory.getPos().equals(tileentity.getPos())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void addControls() {
        buttonList.add(new GuiButtonWireless(0, (width / 2) + 10, (height / 2) - 20, 20, 20, "+"));
        buttonList.add(new GuiButtonWireless(1, (width / 2) - 30, (height / 2) - 20, 20, 20, "-"));
        buttonList.add(new GuiButtonWireless(2, (width / 2) + 32, (height / 2) - 20, 20, 20, "+10"));
        buttonList.add(new GuiButtonWireless(3, (width / 2) - 52, (height / 2) - 20, 20, 20, "-10"));
        buttonList.add(new GuiButtonWireless(4, (width / 2) + 54, (height / 2) - 20, 26, 20, "+100"));
        buttonList.add(new GuiButtonWireless(5, (width / 2) - 80, (height / 2) - 20, 26, 20, "-100"));
        buttonList.add(new GuiButtonWireless(6, (width / 2) + 48, (height / 2) - 42, 32, 20, "+1000"));
        buttonList.add(new GuiButtonWireless(7, (width / 2) - 80, (height / 2) - 42, 32, 20, "-1000"));

        buttonList.add(new GuiButtonWirelessExit(100, (((width - xSize) / 2)
                                                       + xSize - 13 - 1), (((height - ySize) / 2) + 1)));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        try {
            Object a = getFreq();
            Object b = getFreq();
            int freq, oldFreq;
            try {
                freq = Integer.parseInt(a.toString());
                oldFreq = Integer.parseInt(b.toString());
            } catch (NumberFormatException e) {
                return;
            }

            switch (guibutton.id) {
            case 0:
                freq++;
                break;
            case 1:
                freq--;
                break;
            case 2:
                freq += 10;
                break;
            case 3:
                freq -= 10;
                break;
            case 4:
                freq += 100;
                break;
            case 5:
                freq -= 100;
                break;
            case 6:
                freq += 1000;
                break;
            case 7:
                freq -= 1000;
                break;
            case 100:
                close();
                break;
            }
            if (freq > 9999) freq -= 10000;
            if (freq < 0) freq += 10000;

            boolean prematureExit = false;
            for (IGuiRedstoneWirelessOverride override : overrides) {
                if (((IGuiRedstoneWirelessInventoryOverride) override).beforeFrequencyChange(inventory,
                                                                                             oldFreq,
                                                                                             freq)) prematureExit = true;
            }
            if (prematureExit) return;

            RedstoneEtherPacketHandler.sendEtherPacketToServer(PacketRedstoneWirelessCommands.wirelessCommands.changeFreq.toString(),
                                                               inventory.getBlockCoord(0),
                                                               inventory.getBlockCoord(1),
                                                               inventory.getBlockCoord(2),
                                                               (freq - oldFreq),
                                                               false);

            if (oldFreq != freq) setFreq(Integer.toString(freq));

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("GuiRedstoneWirelessInventory").writeStackTrace(e);
        }
    }

    /**
     * TileEntity name.
     * 
     * @return Name.
     */
    @Override
    protected String getGuiName() {
        return inventory.getName();
    }

    /**
     * Fetches the frequency from the TileEntity
     * 
     * @return Frequency.
     */
    protected Object getFreq() {
        return inventory.getFreq();
    }

    /**
     * Sets the frequency in the TileEntity.
     * 
     * @param freq
     *            frequency.
     */
    public void setFreq(Object freq) {
        inventory.setFreq(freq);
    }
}
