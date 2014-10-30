package common.xandayn.personalizedrecipes_old.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * PersonalizedRecipes - FurnaceRecipe
 *
 * A class that contains information about custom Furnace Recipes.
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
public class FurnaceRecipe implements IRecipe {

    private ItemStack recipeOutput, recipeInput;
    private float smeltingEXP;

    public FurnaceRecipe(ItemStack recipeOutput, ItemStack recipeInput, float smeltingEXP){
        this.recipeOutput = recipeOutput;
        this.recipeInput = recipeInput;
        this.smeltingEXP = smeltingEXP;
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

    public float getSmeltingEXP() {
        return smeltingEXP;
    }

    public ItemStack getRecipeInput() {
        return recipeInput;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    public boolean isRecipeEqual(ItemStack otherInput, ItemStack otherOutput){
        return otherOutput.isItemEqual(recipeOutput) && otherInput.isItemEqual(recipeInput);
    }
}
