package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.client.gui.component.dialog.GUIItemListDialog;
import net.minecraft.client.gui.GuiButton;

/**
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
public class GUIItemListDialogSlot extends GUISlot {

    private GUIItemListDialog dialog;
    public boolean wasOpen = false;

    public GUIItemListDialogSlot(int x, int y, int dialogX, int dialogY, int stackLimit) {
        super(x, y, stackLimit);
        dialog = new GUIItemListDialog(dialogX, dialogY, stackLimit);
    }

    public boolean isDialogOpen(){
        return dialog.isOpen();
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            super.update(mouseX, mouseY);
            if(wasOpen) { //dialog was just closed
                wasOpen = false;
                setItem(dialog.getResult());
            }
        } else
            dialog.update(mouseX, mouseY);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(!isActive()) return;
        if(!isDialogOpen())
            super.actionPerformed(button);
        else
            dialog.actionPerformed(button);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, float timeSincePress) {
        if(!isActive()) return;
        if(!dialog.isOpen())
            super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        else
            dialog.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            if(this.contains(mouseX, mouseY)) {
                if (mouseButton == 0) {
                    wasOpen = true;
                    dialog.reset();
                    dialog.setResult(getItem());
                    dialog.open();
                } else if (mouseButton == 1) {
                    setItem(null);
                }
            }
        } else
            dialog.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            super.mouseReleased(mouseX, mouseY, mouseButton);
        } else {
            dialog.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean keyTyped(char key, int keyCode) {
        if(!isDialogOpen())
            return super.keyTyped(key, keyCode);
        else
            return dialog.keyTyped(key, keyCode);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(!isDialogOpen())
            super.renderBackground(mouseX, mouseY);
        else
            dialog.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(!isDialogOpen())
            super.renderForeground(mouseX, mouseY);
        else
            dialog.renderForeground(mouseX, mouseY);
    }
}
