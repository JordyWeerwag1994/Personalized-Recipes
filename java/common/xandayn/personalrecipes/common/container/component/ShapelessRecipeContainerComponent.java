package common.xandayn.personalrecipes.common.container.component;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * PersonalizedRecipes - ShapelessRecipeContainerComponent
 *
 * A class that defines a shapeless component for the RecipeCreatorGui.
 *
 * @license
 *   Copyright (C) 2014  xandayn
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author xandayn
 */
public class ShapelessRecipeContainerComponent implements IRecipeContainerComponent {

    private Slot[] cSlots;
    private Slot[] pSlots;


    @Override
    public void initializeSlots(IInventory invSlots, InventoryPlayer playerSlots) {
        cSlots = new Slot[getCraftingSlotCount()];
        pSlots = new Slot[36];

        for(int i = 0; i < getCraftingSlotCount()-1; i++){
            int row = i / 3;
            int column = i % 3;
            cSlots[i] = new Slot(invSlots, i, 30 + (column*18), 17 + (row * 18));
        }
        cSlots[getCraftingSlotCount()-1] = new Slot(invSlots, 9, 124, 35);

        for (int i = 0; i < 9; i++) {
            pSlots[i] = new Slot(playerSlots, i, (8 + 18 * i), 142);
        }
        for (int i = 0; i < 27; i++) {
            pSlots[i+9] = new Slot(playerSlots, i + 9, (8 + 18 * (i % 9)), 84 + 18 * (i / 9));
        }

    }

    @Override
    public Slot[] getCraftingInventorySlots() {
        return cSlots;
    }

    @Override
    public Slot[] getPlayerInventorySlots() {
        return pSlots;
    }

    @Override
    public ResourceLocation getGuiBackground() {
        return new ResourceLocation("textures/gui/container/crafting_table.png");
    }

    @Override
    public int getGuiXSize() {
        return 176;
    }

    @Override
    public int getGuiYSize() {
        return 166;
    }

    @Override
    public String getInventoryName() {
        return "Shapeless Recipe Creator";
    }

    @Override
    public GuiButton getConfirmationButton(int guiLeft, int guiTop) {
        return new GuiButton(0, guiLeft + 107, guiTop + 59, 50, 20, "Confirm");
    }

    @Override
    public boolean isSlotOutput(int slot) {
        return slot == 9;
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public String getCraftingAlias() {
        return "shapeless";
    }

    @Override
    public IRecipe compileRecipe(ItemStack[] data, ArrayList<Integer> disabledSlots) {
        ItemStack output = data[9];
        ArrayList<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < data.length - 1; i++){
            if(data[i] != null){
                items.add(data[i].copy());
            }
        }
        return new ShapelessRecipes(output, items);
    }

    @Override
    public boolean allowSlotDisabling() {
        return false;
    }

    @Override
    public void addRecipeToCraftingWindow(IRecipe recipe, ItemStack[] table) {
        ShapelessRecipes sRecipe = (ShapelessRecipes)recipe;
        int i = 0;
        for(Object r : sRecipe.recipeItems){
            ItemStack item = (ItemStack)r;
            table[i] = item;
            i++;
        }
        table[9] = sRecipe.getRecipeOutput();
    }
}
