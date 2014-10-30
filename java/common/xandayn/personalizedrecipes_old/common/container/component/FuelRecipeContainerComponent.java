package common.xandayn.personalizedrecipes_old.common.container.component;

import common.xandayn.personalizedrecipes_old.recipe.FuelRecipe;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

/**
 * PersonalizedRecipes - FuelRecipeContainerComponent
 *
 * A class that defines a fuel component for the RecipeCreatorGui.
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
public class FuelRecipeContainerComponent implements IRecipeContainerComponent {

    private Slot[] cSlots;
    private Slot[] pSlots;


    @Override
    public void initializeSlots(IInventory invSlots, InventoryPlayer playerSlots) {
        cSlots = new Slot[getCraftingSlotCount()];
        pSlots = new Slot[36];

        cSlots[0] = new Slot(invSlots, 0, 56, 53);
        cSlots[1] = new Slot(invSlots, 1, 116, 34);

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
        return new ResourceLocation("textures/gui/container/furnace.png");
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
        return "Fuel Recipe Creator";
    }

    @Override
    public GuiButton getConfirmationButton(int guiLeft, int guiTop) {
        return new GuiButton(0, guiLeft + 107, guiTop + 59, 50, 20, "Confirm");
    }

    @Override
    public boolean isSlotOutput(int slot) {
        return slot == 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 2;
    }

    @Override
    public String getCraftingAlias() {
        return "fuel";
    }

    @Override
    public IRecipe compileRecipe(ItemStack[] data) {
        return new FuelRecipe(data[0], data[1].stackSize);
    }

    @Override
    public boolean allowMultipleItemsInSlot() {
        return false;
    }

    @Override
    public void addRecipeToCraftingWindow(IRecipe recipe, ItemStack[] table) {
        if(recipe != null && recipe instanceof FuelRecipe) {
            FuelRecipe fRecipe = (FuelRecipe) recipe;
            table[0] = fRecipe.getRecipeOutput();
            table[1] = new ItemStack(Blocks.dirt, fRecipe.getBurnTime() / 200);
        }
    }
}
