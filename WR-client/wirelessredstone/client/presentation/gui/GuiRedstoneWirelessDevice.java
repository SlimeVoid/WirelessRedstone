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
package wirelessredstone.client.presentation.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.api.IGuiRedstoneWirelessDeviceOverride;
import wirelessredstone.api.IGuiRedstoneWirelessOverride;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.client.network.ClientPacketHandler;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.inventory.ContainerRedstoneDevice;
import wirelessredstone.network.packets.PacketWirelessDevice;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author Eurymachus
 */
public abstract class GuiRedstoneWirelessDevice extends
        GuiRedstoneWirelessContainer {

    IWirelessDevice wirelessDevice;

    /**
     * Constructor.<br>
     * Sets default width and height.
     */
    public GuiRedstoneWirelessDevice(World world, EntityPlayer entityplayer, ContainerRedstoneDevice device) {
        super(device);
        this.wirelessDevice = device.getDevice();
        this.entityplayer = entityplayer;
        this.world = world;
        this.xSize = 177;
        this.ySize = 166;
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
                if (((IGuiRedstoneWirelessDeviceOverride) override).beforeFrequencyChange(this.world,
                                                                                          this.wirelessDevice,
                                                                                          oldFreq,
                                                                                          freq)) prematureExit = true;
            }
            if (prematureExit) return;

            notifyServer(freq - oldFreq);

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("GuiRedstoneWirelessDevice").writeStackTrace(e);
        }
    }

    protected void notifyServer(Object freq) {
        PacketWirelessDevice packet = new PacketWirelessDevice(this.wirelessDevice);
        packet.setCommand(this.getCommand());
        packet.setFreq(freq);
        ClientPacketHandler.sendPacket(packet.getPacket());
    }

    protected abstract String getCommand();

    /**
     * Draws the frequency at a distance at Y from the centred value
     * 
     * @param y
     */
    protected void drawFrequency(int y) {
        fontRenderer.drawString(this.getFreq() + "",
                                (xSize / 2)
                                        - (fontRenderer.getStringWidth(this.getFreq()
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
                                 - (fontRenderer.getStringWidth(getFreq() + "") / 2),
                         (ySize / 2) + y,
                         (xSize / 2)
                                 + (fontRenderer.getStringWidth(getFreq() + "") / 2));
        drawFrequency(y);
    }

    protected void drawFrequencyLabel(int y) {
        fontRenderer.drawString("Frequency",
                                (xSize / 2)
                                        - (fontRenderer.getStringWidth("Frequency") / 2),
                                y,
                                0x404040);
    }

    protected void drawFrequencyLabelAndBox(int y) {
        drawStringBorder((xSize / 2)
                                 - (fontRenderer.getStringWidth("Frequency") / 2),
                         y,
                         (xSize / 2)
                                 + (fontRenderer.getStringWidth("Frequency") / 2));
        drawFrequencyLabel(y);
    }

    @Override
    protected void drawForegroundObjects(int i, int j) {
        drawFrequencyLabelAndBox(32);
        drawFrequencyAndBox(-35);
    }

    /**
     * WirelessDeviceData name.
     * 
     * @return Name.
     */
    @Override
    protected String getGuiName() {
        return this.wirelessDevice.getInvName();
    }

    protected Object getFreq() {
        return this.wirelessDevice.getFreq();
    }

    public void setFreq(Object freq) {
        this.wirelessDevice.setFreq(freq);
    }
}
