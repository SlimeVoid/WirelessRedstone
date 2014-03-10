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
package com.slimevoid.wirelessredstone.client.presentation.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.slimevoid.wirelessredstone.api.IGuiRedstoneWirelessOverride;
import com.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import com.slimevoid.wirelessredstone.inventory.ContainerRedstoneWireless;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author Eurymachus
 */
public abstract class GuiRedstoneWirelessContainer extends GuiContainer {
    /**
     * Current World
     */
    protected World                              world;
    /**
     * Current Player
     */
    protected EntityPlayer                       entityplayer;
    /**
     * Width
     */
    protected int                                xSize;
    /**
     * Height
     */
    protected int                                ySize;
    /**
     * GUI overrides.
     */
    protected List<IGuiRedstoneWirelessOverride> overrides;

    /**
     * Constructor.<br>
     * Sets default width,height and initializes override list object.
     */
    public GuiRedstoneWirelessContainer(ContainerRedstoneWireless container) {
        super(container);
        this.xSize = 177;
        this.ySize = 166;
        this.overrides = new ArrayList<IGuiRedstoneWirelessOverride>();
    }

    /**
     * Adds a GUI override to the GUI screen.
     * 
     * @param override
     *            GUI override.
     */
    public void addOverride(IGuiRedstoneWirelessOverride override) {
        this.overrides.add(override);
    }

    /**
     * Adds a selection of controls to be used in the GUI
     * 
     * Override to replace control set use super.addControls() if adding to
     * control set
     */
    protected abstract void addControls();

    /**
     * Initializes the GUI.<br>
     * Adds buttons.
     */
    @Override
    public void initGui() {
        addControls();
        super.initGui();
    }

    /**
     * Check is Mouse pointer is within the bounds of a given GuiButtonWireless
     * 
     * @param button
     *            A GuiButtonWireless
     * @param i
     *            mouse X coordinate
     * @param j
     *            mouse Y coordinate
     * @return
     */
    private boolean isMouseOverButton(GuiButtonWireless button, int i, int j) {
        if (button != null) return button.inBounds(i,
                                                   j);
        return false;
    }

    /**
     * Fetches the Background Image to be used
     * 
     * @return Background Image
     */
    protected abstract ResourceLocation getBackgroundImage();

    /**
     * Fetches the Gui Name to display at the top of the Gui
     * 
     * @return Gui Name
     */
    protected abstract String getGuiName();

    /**
     * Draw a bordered box around a string.<br>
     * Outer height is always 10, inner 8.
     * 
     * @param x1
     *            screen X coordinate, top left
     * @param y1
     *            screen Y coordinate, top left
     * @param x2
     *            screen X coordinate, bottom right
     */
    protected void drawStringBorder(int x1, int y1, int x2) {
        drawRect(x1 - 3,
                 y1 - 3,
                 x2 + 3,
                 y1 + 10,
                 0xff000000);
        drawRect(x1 - 2,
                 y1 - 2,
                 x2 + 2,
                 y1 + 9,
                 0xffffffff);
    }

    /**
     * Draws the Name of the Gui at the top of the Gui
     */
    protected void drawGuiName() {
        drawStringBorder((xSize / 2)
                                 - (fontRendererObj.getStringWidth(this.getGuiName()) / 2),
                         7,
                         (xSize / 2)
                                 + (fontRendererObj.getStringWidth(this.getGuiName()) / 2));
        fontRendererObj.drawString(getGuiName(),
                                   (xSize / 2)
                                           - (fontRendererObj.getStringWidth(this.getGuiName()) / 2),
                                   7,
                                   0x404040);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.drawGuiName();
        this.drawForegroundObjects(i,
                                   j);
    }

    protected abstract void drawForegroundObjects(int i, int k);

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F,
                       1.0F,
                       1.0F,
                       1.0F);
        mc.renderEngine.bindTexture(this.getBackgroundImage());
        int sizeX = (width - xSize) / 2;
        int sizeY = (height - ySize) / 2;
        drawTexturedModalRect(sizeX,
                              sizeY,
                              0,
                              0,
                              xSize,
                              ySize);
    }

    /**
     * Draws the tooltips of a button when Mouse is hovered
     * 
     * @param i
     *            x Coordinate
     * @param j
     *            y Coordinate
     * @param f
     *            tick partial
     */
    private void drawTooltips(int i, int j, float f) {
        // this.mc.renderEngine.resetBoundTexture();
        for (Object button : this.buttonList) {
            if (button instanceof GuiButtonWireless) {
                GuiButtonWireless guibutton = (GuiButtonWireless) button;
                if (this.isMouseOverButton(guibutton,
                                           i,
                                           j)) {
                    guibutton.drawToolTip(this.mc,
                                          i,
                                          j);
                }
            }
        }
    }

    /**
     * Handles keyboard input.<br>
     * If inventory key is pressed or ESC, close the GUI.
     */
    @Override
    public void handleKeyboardInput() {
        try {
            super.handleKeyboardInput();

            if (Keyboard.getEventKeyState()) {
                int inventoryKey = 0;
                if (this.mc.gameSettings.keyBindInventory.isPressed()) {
                    inventoryKey = this.mc.gameSettings.keyBindInventory.getKeyCode();
                }
                if (Keyboard.getEventKey() == inventoryKey
                    || Keyboard.getEventKey() == 28) {
                    close();
                    return;
                }
            }
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("GuiRedstoneWirelessContainer").writeStackTrace(e);
        }
    }

    /**
     * Closes the GUI.
     */
    public void close() {
        try {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("GuiRedstoneWirelessContainer").writeStackTrace(e);
        }
    }

    /**
     * Always returns false, prevents game from pausing when GUI is open.
     * 
     * @return false
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Refresh the current GUI
     * 
     * Removing and re-adding all listed controls
     */
    public void refreshGui() {
        this.removeControls();
        this.addControls();
    }

    /**
     * Clears the control list
     * 
     * Used in refreshGui()
     */
    private void removeControls() {
        this.buttonList.clear();
    }
}