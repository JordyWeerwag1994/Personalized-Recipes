package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class GUISlot extends GUIComponent {
    public static final int _GUI_SLOT_SIZE = 16;

    private ItemStack item = null;
    private boolean active = true;
    private int x, y;
    public int stackLimit;

    public GUISlot(int x, int y, int stackLimit) {
        super(x, y, _GUI_SLOT_SIZE, _GUI_SLOT_SIZE);
        this.x = x;
        this.y = y;
        this.stackLimit = stackLimit;
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(active) {
            if (contains(mouseX, mouseY))
                Rendering.drawColoredRectangle(x, y, _GUI_SLOT_SIZE, _GUI_SLOT_SIZE, 0xC6, 0xC6, 0xC6);
        } else {
            Rendering.drawColoredRectangle(x, y, _GUI_SLOT_SIZE, _GUI_SLOT_SIZE, 0x55, 0x55, 0x55);
        }
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if (item != null)
            Rendering.drawItem(x, y, item);
    }

    public void setItem(ItemStack item){
        if(item != null) {
            if (item.stackSize > stackLimit) {
                ItemStack temp = item.copy();
                temp.stackSize = stackLimit;
                this.item = temp.copy();
            } else {
                this.item = item.copy();
            }
        } else {
            this.item = null;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        if(!active) item = null;
    }

    public ItemStack getItem(){
        return item == null ? null : item.copy();
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEmpty() {
        return item == null;
    }
}
