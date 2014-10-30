package common.xandayn.personalizedrecipes_old.recipe.handlers;

import common.xandayn.personalizedrecipes_old.recipe.FurnaceRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * PersonalizedRecipes - FurnaceRecipeHandler
 *
 * A handler class that handles registering/removing FurnaceRecipes to/from Minecraft.
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
public class FurnaceRecipeHandler implements ICustomRecipeHandler {

    @Override
    public void registerRecipe(IRecipe recipeData) {
        if(recipeData instanceof FurnaceRecipe){
            FurnaceRecipe recipe = (FurnaceRecipe)recipeData;
            GameRegistry.addSmelting(recipe.getRecipeInput(), recipe.getRecipeOutput(), recipe.getSmeltingEXP());
        }
    }

    @Override
    public void removeRecipe(IRecipe recipeData) {
        if(recipeData instanceof FurnaceRecipe){
            FurnaceRecipe recipe = (FurnaceRecipe)recipeData;
            HashMap fRecipes = (HashMap)FurnaceRecipes.smelting().getSmeltingList();

            Iterator iterator = fRecipes.entrySet().iterator();
            Entry entry = null;
            boolean found = false;
            while (iterator.hasNext()){
                entry = (Entry)iterator.next();
                if(recipe.isRecipeEqual((ItemStack)entry.getKey(), (ItemStack)entry.getValue())){
                    found = true;
                    break;
                }
            }
            if(found){
                fRecipes.remove(entry.getKey());
            }

        }
    }

    @Override
    public void writeToNBT(IRecipe toWrite, NBTTagCompound tag) {
        if(toWrite instanceof FurnaceRecipe) {
            tag.setFloat("smeltingEXP", 0.1f);
            FurnaceRecipe recipe = (FurnaceRecipe)toWrite;
            NBTTagCompound inputTag = new NBTTagCompound();
            recipe.getRecipeInput().writeToNBT(inputTag);
            tag.setTag("inputTag", inputTag);
            NBTTagCompound outputTag = new NBTTagCompound();
            recipe.getRecipeOutput().writeToNBT(outputTag);
            tag.setTag("outputTag", outputTag);
        }
    }

    @Override
    public IRecipe readFromNBT(NBTTagCompound tag) {
        float smeltingEXP = tag.getFloat("smeltingEXP");
        ItemStack inputTag = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("inputTag"));
        ItemStack outputTag = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("outputTag"));
        return new FurnaceRecipe(outputTag, inputTag, smeltingEXP);
    }
}
