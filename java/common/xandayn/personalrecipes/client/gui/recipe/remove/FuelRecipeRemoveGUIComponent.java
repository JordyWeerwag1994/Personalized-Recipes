package common.xandayn.personalrecipes.client.gui.recipe.remove;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.FuelRecipeData;
import common.xandayn.personalrecipes.recipe.handler.FuelRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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

public class FuelRecipeRemoveGUIComponent extends RecipeGUIComponent {

    private GUISlot slot;
    private GuiButton lastPage, nextPage;
    private GUITextField textField;
    private FuelRecipeHandler handler;
    private int selected;
    private int pageCount;

    public FuelRecipeRemoveGUIComponent(FuelRecipeHandler handler) {
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/fuel_recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
        this.handler = handler;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        components.add(textField = new GUITextField(guiLeft + 11, guiTop + 49, 50, 6, null));
        components.add(slot = new GUISlot(guiLeft + 11, guiTop + 11, 1, gui));
        buttonList.add(new GuiButton(0, guiLeft + 35, guiTop + 10, 40, 18, "Remove"));
        buttonList.add(new GuiButton(1, guiLeft + 80, guiTop + 10, 40, 18, "Back"));
        buttonList.add(lastPage = new GuiButton(2, guiLeft - 10, guiTop + 38, 10, 10, "<"));
        buttonList.add(nextPage = new GuiButton(3, guiLeft + xSize, guiTop + 38, 10, 10, ">"));
        textField.setEnabled(false);
        selected = 0;
        pageCount = handler.getRecipeCount();
        updateRecipe();
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        nextPage.enabled = selected != (pageCount - 1);
        lastPage.enabled = selected != 0;
    }

    private void updateRecipe() {
        FuelRecipeData data = handler.getRecipes().get(selected);
        slot.setItem(data.itemInputs.get(0));
        textField.setText(String.valueOf(data.burnTime));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                handler.deleteRecipe(selected);
                gui.returnToSelectScreen();
                break;
            case 1:
                gui.returnToSelectScreen();
                break;
            case 2:
                selected--;
                if(selected < 0) {
                    selected = 0;
                }
                updateRecipe();
                break;
            case 3:
                selected++;
                if(selected > pageCount) {
                    selected = pageCount;
                }
                updateRecipe();
                break;
        }
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        super.renderForeground(mouseX, mouseY);
        Rendering.drawString("Burn Time", guiLeft + 11, guiTop + 38, 0xFFFFFFFF);
        GL11.glPushMatrix();
        GL11.glScalef(0.9f, 0.9f, 0.9f);
        Rendering.drawString("200 Burn", guiLeft + 89, guiTop + 54, 0xFFFFFFFF);
        Rendering.drawString("Time is 1", guiLeft + 89, guiTop + 64, 0xFFFFFFFF);
        Rendering.drawString("item smelted.", guiLeft + 89, guiTop + 74, 0xFFFFFFFF);
        GL11.glPopMatrix();
    }
}
