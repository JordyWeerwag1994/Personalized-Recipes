package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.recipe.data.SmeltingRecipeData;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

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
public class SmeltingRecipeGUIComponent extends RecipeGUIComponent {

    private GuiButton save;
    private GUIItemListDialogSlot dialogSlot, input, output;
    private GUITextField expCount;

    public SmeltingRecipeGUIComponent(){
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/smelting_recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, boolean remove) {
        super.initGUI(gui, remove);
        buttonList.add(save = new GuiButton(0, guiLeft + 72, guiTop + 11, 42, 18, "Save"));
        buttonList.add(new GuiButton(1, guiLeft + 72, guiTop + 55, 42, 18, "Back"));
        components.add(input = new GUIItemListDialogSlot(guiLeft + 25, guiTop + 16, guiLeft, guiTop, 1));
        components.add(output = new GUIItemListDialogSlot(guiLeft + 85, guiTop + 34, guiLeft, guiTop, 64));
        components.add(expCount = new GUITextField(guiLeft + 50, guiTop + 36, 24, 3, null));
        expCount.setAllowed(GUITextField.NUMERIC_ONLY);
        save.enabled = false;

    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(dialogSlot == null) {
            switch (button.id) {
                case 0:
                    SmeltingRecipeData data = new SmeltingRecipeData();
                    data.smeltingEXP = expCount.getText() == null ? 0.0f : (float)Integer.parseInt(expCount.getText()) / 100.0f;
                    data.itemInputs = new ArrayList<>();
                    data.itemOutputs = new ArrayList<>();
                    data.itemInputs.add(input.getItem());
                    data.itemOutputs.add(output.getItem());
                    data.register();
                    gui.returnToSelectScreen();
                    break;
                case 1:
                    gui.returnToSelectScreen();
                    break;
            }
        }
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if(dialogSlot == null) {
            super.update(mouseX, mouseY);
            save.enabled = !output.isEmpty() && !input.isEmpty();
            if(expCount.getText() != null && Integer.parseInt(expCount.getText()) > 100) expCount.setText("100");
            for (GUIComponent component : components) {
                if (component instanceof GUIItemListDialogSlot) {
                    GUIItemListDialogSlot slot = (GUIItemListDialogSlot) component;
                    if (slot.isDialogOpen()){
                        dialogSlot = slot;
                        break;
                    }

                }
            }
        } else {
            dialogSlot.update(mouseX, mouseY);
            if(!dialogSlot.isDialogOpen()) dialogSlot = null;
        }
    }
    @Override
    public boolean keyTyped(char value, int keyCode) {
        if(dialogSlot == null)
            return expCount.keyTyped(value, keyCode) || super.keyTyped(value, keyCode);
        else
            return dialogSlot.keyTyped(value, keyCode);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(dialogSlot == null)
            super.mousePressed(mouseX, mouseY, mouseButton);
        else
            dialogSlot.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) {
        if(dialogSlot == null)
            super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        else
            dialogSlot.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(dialogSlot == null)
            super.mouseReleased(mouseX, mouseY, mouseButton);
        else
            dialogSlot.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(dialogSlot == null)
            super.renderForeground(mouseX, mouseY);
        else
            dialogSlot.renderForeground(mouseX, mouseY);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(dialogSlot == null)
            super.renderBackground(mouseX, mouseY);
        else
            dialogSlot.renderBackground(mouseX, mouseY);
    }
}
