package common.xandayn.personalrecipes.client.gui.recipe.add;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIInventoryComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
public class ShapelessRecipeGUIComponent extends RecipeGUIComponent {

    private GUIItemListDialogSlot dialogSlot, outputSlot;
    private GuiButton save;
    private GUIInventoryComponent inventoryComponent;

    public ShapelessRecipeGUIComponent(){
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        buttonList.add(save = new GuiButton(0, guiLeft + 87, guiTop + 16, 26, 16, "Save"));
        buttonList.add(new GuiButton(1, guiLeft + 87, guiTop + 54, 26, 16, "Back"));
        save.enabled = false;
        components.add(inventoryComponent = new GUIInventoryComponent(guiLeft - ((GUIInventoryComponent._TEXTURE_WIDTH / 2) - (xSize / 2)), guiTop + ySize, player, gui));
        for(int i = 0; i < 9; i++){
            int x = i % 3;
            int y = i / 3;
            int bufferX = x * 2;
            int bufferY = y * 2;
            components.add(new GUIItemListDialogSlot(20 + (16 * x) + bufferX + guiLeft, 17 + (16 * y) + bufferY + guiTop, guiLeft, guiTop, 1, gui));
        }
        components.add(outputSlot = new GUIItemListDialogSlot(92 + guiLeft, 35 + guiTop, guiLeft, guiTop, 64, gui));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(dialogSlot == null) {
            switch (button.id) {
                case 0:
                    RecipeData data = new RecipeData("Shapeless");
                    GUIItemListDialogSlot output = (GUIItemListDialogSlot)components.remove(components.size() - 1);
                    data.itemInputs = new ArrayList<>();
                    data.itemOutputs = new ArrayList<>();
                    for(GUIComponent comp : components){
                        if(comp instanceof GUIItemListDialogSlot){
                            GUIItemListDialogSlot slot = (GUIItemListDialogSlot)comp;
                            if(!slot.isEmpty())
                                data.itemInputs.add(slot.getItem());
                        }
                    }
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
            save.enabled = !outputSlot.isEmpty();
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
            return super.keyTyped(value, keyCode);
        else
            return dialogSlot.keyTyped(value, keyCode);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(dialogSlot == null) {
            if(!inventoryComponent.hasSelection())
                super.mousePressed(mouseX, mouseY, mouseButton);
            else {
                for(GuiButton button : buttonList) {
                    if(button.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)){
                        button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                        actionPerformed(button);
                        break;
                    }
                }
                for(GUIComponent component : components){
                    if(component instanceof GUIItemListDialogSlot) {
                        if(component.contains(mouseX, mouseY)) {
                            ItemStack item = null;
                            if(mouseButton == 0) {
                                item = inventoryComponent.getSelectedItem();
                            }
                            ((GUIItemListDialogSlot) component).setItem(item);
                        }
                    } else {
                        component.mousePressed(mouseX, mouseY, mouseButton);
                    }
                }
            }
        } else
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
