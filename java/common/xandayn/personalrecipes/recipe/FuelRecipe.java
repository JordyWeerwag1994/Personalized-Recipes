package common.xandayn.personalrecipes.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * PersonalizedRecipes - FuelRecipe
 *
 * A class that contains information about custom Fuel Recipes.
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
public class FuelRecipe implements IRecipe {

    private ItemStack burnItem;
    private int burnTime;
    public FuelRecipe(ItemStack burnItem, int amountCanBurn){
        this.burnItem = burnItem;
        this.burnTime = amountCanBurn * 200;
        System.out.println(amountCanBurn);
    }

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return burnItem;
    }

    public int getBurnTime(){
        return burnTime;
    }

    public boolean isItemInRecipe(ItemStack itemStack){
        return itemStack.isItemEqual(burnItem);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FuelRecipe && ((FuelRecipe)obj).getRecipeOutput().isItemEqual(this.burnItem);
    }

    @Override
    public String toString() {
        return burnItem.getDisplayName() + " " + burnTime;
    }
}
