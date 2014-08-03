package common.xandayn.personalrecipes.common.container.component;


import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * PersonalizedRecipes - IRecipeContainerComponent
 *
 * An interface that defines components for the RecipeCreatorGui.
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
public interface IRecipeContainerComponent {

    public void initializeSlots(IInventory invSlots, InventoryPlayer playerSlots);
    public Slot[] getCraftingInventorySlots();
    public Slot[] getPlayerInventorySlots();
    public ResourceLocation getGuiBackground();
    public int getGuiXSize();
    public int getGuiYSize();
    public String getInventoryName();
    GuiButton getConfirmationButton(int guiLeft, int guiTop);
    boolean isSlotOutput(int slot);
    int getCraftingSlotCount();
    public String getCraftingAlias();
    public IRecipe compileRecipe(ItemStack[] data);
    public boolean allowMultipleItemsInSlot();
    public void addRecipeToCraftingWindow(IRecipe recipe, ItemStack[] table);

}
