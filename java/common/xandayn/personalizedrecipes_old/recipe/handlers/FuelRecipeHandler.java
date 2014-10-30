package common.xandayn.personalizedrecipes_old.recipe.handlers;

import common.xandayn.personalizedrecipes_old.recipe.FuelRecipe;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * PersonalizedRecipes - FuelRecipeHandler
 *
 * A handler class that handles registering/removing FuelRecipes to/from Minecraft.
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
public class FuelRecipeHandler implements IFuelHandler, ICustomRecipeHandler {

    private ArrayList<FuelRecipe> _recipes = new ArrayList<>();

    public FuelRecipeHandler(){
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        for(FuelRecipe r : _recipes){
            if(r.isItemInRecipe(fuel)){
                return r.getBurnTime();
            }
        }
        return 0;
    }

    @Override
    public void registerRecipe(IRecipe recipeData) {
        if(recipeData instanceof FuelRecipe){
            _recipes.add((FuelRecipe)recipeData);
        }
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        if(recipe instanceof FuelRecipe){
            if(_recipes.contains(recipe))
                _recipes.remove(recipe);
        }
    }

    @Override
    public void writeToNBT(IRecipe toWrite, NBTTagCompound tag) {
        if(toWrite instanceof FuelRecipe){
            FuelRecipe recipe = (FuelRecipe)toWrite;
            tag.setInteger("burnTime", recipe.getBurnTime());
            NBTTagCompound burnItem = new NBTTagCompound();
            recipe.getRecipeOutput().writeToNBT(burnItem);
            tag.setTag("burnItem", burnItem);
        }
    }

    @Override
    public IRecipe readFromNBT(NBTTagCompound tag) {
        int burnTime = tag.getInteger("burnTime") / 200;
        ItemStack burnItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("burnItem"));
        return new FuelRecipe(burnItem, burnTime);
    }
}
