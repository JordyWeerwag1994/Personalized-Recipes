package common.xandayn.personalrecipes.client.gui.recipe.add;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIInventoryComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.FuelRecipeData;
import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

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
public class FuelRecipeGUIComponent extends RecipeGUIComponent {

    private GUIItemListDialogSlot slot;
    private GuiButton save;
    private GuiButton cancel;
    private GUITextField textField;
    private GUIInventoryComponent inventoryComponent;

    public FuelRecipeGUIComponent(){
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/fuel_recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        components.add(inventoryComponent = new GUIInventoryComponent(guiLeft - ((GUIInventoryComponent._TEXTURE_WIDTH / 2) - (xSize / 2)), guiTop + ySize, player, gui));
        components.add(textField = new GUITextField(guiLeft + 11, guiTop + 49, 50, 6, null));
        components.add(slot = new GUIItemListDialogSlot(guiLeft + 11, guiTop + 11, guiLeft, guiTop, 1, gui));
        buttonList.add(save = new GuiButton(0, guiLeft + 43, guiTop + 10, 32, 18, "Save"));
        buttonList.add(cancel = new GuiButton(1, guiLeft + 88, guiTop + 10, 32, 18, "Back"));
        textField.setAllowed(GUITextField.NUMERIC_ONLY);
        save.enabled = false;
        cancel.enabled = false;
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if(!slot.isDialogOpen()) {
            super.update(mouseX, mouseY);
            cancel.enabled = true;
            save.enabled = !slot.isEmpty() && textField.getText() != null && Integer.parseInt(textField.getText()) > 0;
        }
        else
            slot.update(mouseX, mouseY);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(!slot.isDialogOpen()){
            switch (button.id){
                case 0:
                    FuelRecipeData data = new FuelRecipeData();
                    data.burnTime = Integer.parseInt(textField.getText());
                    data.itemInputs = new ArrayList<>(Arrays.asList(slot.getItem()));
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
    public boolean keyTyped(char value, int keyCode) {
        if(!slot.isDialogOpen())
            return textField.keyTyped(value, keyCode);
        else
            return slot.keyTyped(value, keyCode);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
//        if(!slot.isDialogOpen())
//            super.mousePressed(mouseX, mouseY, mouseButton);
//        else
//            slot.mousePressed(mouseX, mouseY, mouseButton);
        if(!slot.isDialogOpen()) {
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
            slot.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) {
        if(!slot.isDialogOpen())
            super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        else
            slot.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(!slot.isDialogOpen())
            super.mouseReleased(mouseX, mouseY, mouseButton);
        else
            slot.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(!slot.isDialogOpen()) {
            super.renderForeground(mouseX, mouseY);
            Rendering.drawString("Burn Time", guiLeft + 11, guiTop + 38, 0xFFFFFFFF);
            GL11.glPushMatrix();
            GL11.glScalef(0.9f, 0.9f, 0.9f);
            Rendering.drawString("200 Burn", guiLeft + 89, guiTop + 54, 0xFFFFFFFF);
            Rendering.drawString("Time is 1", guiLeft + 89, guiTop + 64, 0xFFFFFFFF);
            Rendering.drawString("item smelted.", guiLeft + 89, guiTop + 74, 0xFFFFFFFF);
            GL11.glPopMatrix();
        } else
            slot.renderForeground(mouseX, mouseY);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(!slot.isDialogOpen())
            super.renderBackground(mouseX, mouseY);
        else {
            slot.renderBackground(mouseX, mouseY);
        }
    }

}
