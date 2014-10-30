package common.xandayn.personalizedrecipes_old.recipe.handlers;

import common.xandayn.personalizedrecipes_old.PersonalizedRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;

/**
 * PersonalizedRecipes - ShapedRecipeHandler
 *
 * A handler class that handles registering/removing ShapedRecipes to/from Minecraft.
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
public class ShapedRecipeHandler implements ICustomRecipeHandler{

    @Override
    public void registerRecipe(IRecipe recipeData) {
        if(recipeData instanceof ShapedRecipes)
            PersonalizedRecipes.INSTANCE.getCraftingRecipeList().add(recipeData);
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        if(recipe != null && recipe instanceof ShapedRecipes){
            PersonalizedRecipes.INSTANCE.getCraftingRecipeList().remove(recipe);
        }
    }

    @Override
    public void writeToNBT(IRecipe toWrite, NBTTagCompound tag) {
        if(toWrite instanceof ShapedRecipes){
            ShapedRecipes recipe = (ShapedRecipes)toWrite;
            ItemStack outPut = recipe.getRecipeOutput();
            ItemStack[] recipeData = recipe.recipeItems;
            NBTTagCompound outputItemTag = new NBTTagCompound();
            outPut.writeToNBT(outputItemTag);
            tag.setTag("outputItem", outputItemTag);
            tag.setInteger("dataSize", recipeData.length);
            tag.setInteger("recipeWidth", recipe.recipeWidth);
            tag.setInteger("recipeHeight", recipe.recipeHeight);
            for(int i = 0; i < recipeData.length; i++){
                NBTTagCompound itemTag = new NBTTagCompound();
                if(recipeData[i] != null)
                    recipeData[i].writeToNBT(itemTag);
                else
                    itemTag.setString("tag", "null");
                tag.setTag("item_" + i, itemTag);
            }
        }
    }

    @Override
    public IRecipe readFromNBT(NBTTagCompound tag) {
        int dataSize = tag.getInteger("dataSize");
        int recipeWidth = tag.getInteger("recipeWidth");
        int recipeHeight = tag.getInteger("recipeHeight");
        ItemStack outPut = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("outputItem"));
        ItemStack[] recipe = new ItemStack[dataSize];
        for(int i = 0; i < dataSize; i++){
            if(!tag.getCompoundTag("item_"+i).hasKey("tag")) {
                NBTTagCompound itemTag = tag.getCompoundTag("item_" + i);
                recipe[i] = ItemStack.loadItemStackFromNBT(itemTag);
            }else{
                recipe[i] = null;
            }
        }
        return new ShapedRecipes(recipeWidth, recipeHeight, recipe, outPut);
    }
}
