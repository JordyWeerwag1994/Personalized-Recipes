package common.xandayn.personalrecipes.recipe.handlers;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonalizedRecipes - FurnaceRecipeHandler
 *
 * A handler class that handles registering/removing Shapeless to/from Minecraft.
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
public class ShapelessRecipeHandler implements ICustomRecipeHandler {

    @Override
    public void registerRecipe(IRecipe recipeData) {
        if(recipeData != null && recipeData instanceof ShapelessRecipes){
            PersonalizedRecipes.INSTANCE.getCraftingRecipeList().add(recipeData);
        }
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        if(recipe != null && recipe instanceof ShapelessRecipes){
            PersonalizedRecipes.INSTANCE.getCraftingRecipeList().remove(recipe);
        }
    }

    @Override
    public void writeToNBT(IRecipe toWrite, NBTTagCompound tag) {
        if(toWrite instanceof ShapelessRecipes){
            ShapelessRecipes recipe = (ShapelessRecipes)toWrite;
            tag.setInteger("itemCount", recipe.recipeItems.size());
            NBTTagCompound outputTag = new NBTTagCompound();
            toWrite.getRecipeOutput().writeToNBT(outputTag);
            tag.setTag("outputTag", outputTag);
            for(int i = 0; i < recipe.recipeItems.size(); i++){
                NBTTagCompound inputTag = new NBTTagCompound();
                if(recipe.recipeItems.get(i) != null) {
                    ((ItemStack) recipe.recipeItems.get(i)).writeToNBT(inputTag);
                    tag.setTag("inputTag_" + i, inputTag);
                }
            }
        }
    }

    @Override
    public IRecipe readFromNBT(NBTTagCompound tag) {
        int itemCount = tag.getInteger("itemCount");
        ItemStack outputTag = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("outputTag"));
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < itemCount; i++){
            items.add(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("inputTag_"+i)));
        }
        return new ShapelessRecipes(outputTag, items);
    }
}
