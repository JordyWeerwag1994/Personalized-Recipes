package common.xandayn.personalizedrecipes_old.common.container;

import common.xandayn.personalizedrecipes_old.common.container.component.IRecipeContainerComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * PersonalizedRecipes - RecipeCreatorContainer
 *
 * A class that contains the recipe GUI container, it contains an IInventory used to help design recipes.
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
public class RecipeCreatorContainer extends Container implements IInventory {

    IRecipeContainerComponent containerComponent;
    public ItemStack[] recipeData;
    InventoryPlayer playerInv;

    public RecipeCreatorContainer(InventoryPlayer playerInv, IRecipeContainerComponent containerComponent){
        this.containerComponent = containerComponent;
        recipeData = new ItemStack[containerComponent.getCraftingSlotCount()];
        this.playerInv = playerInv;
        containerComponent.initializeSlots(this, this.playerInv);

        for(Slot s : containerComponent.getPlayerInventorySlots()){
            addSlotToContainer(s);
        }

        for(Slot s : containerComponent.getCraftingInventorySlots()){
            addSlotToContainer(s);
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return recipeData.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return recipeData[var1] == null ? null : recipeData[var1].copy();
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        if(var1 >= 600) {
            int actualSlot = var1 - 600;
            recipeData[actualSlot] = var2;
        }
    }

    @Override
    public String getInventoryName() {
        return containerComponent.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return true;
    }

    public IRecipeContainerComponent getRecipeComponent() {
        return containerComponent;
    }

    public ItemStack[] getInventoryArray() {
        ItemStack[] copyData = new ItemStack[recipeData.length];
        for(int i = 0; i < copyData.length; i++){
            System.out.println(recipeData[i] == null ? "null" : recipeData[i].stackSize);
            copyData[i] = recipeData[i] == null ? null : recipeData[i].copy();
        }
        return copyData;
    }
}
