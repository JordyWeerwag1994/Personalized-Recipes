package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.add.FuelRecipeGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.remove.FuelRecipeRemoveGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.FuelRecipeData;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.util.Util;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class FuelRecipeHandler extends CustomRecipeHandler<FuelRecipeData> implements IFuelHandler {

    private ArrayList<FuelRecipeData> recipes = new ArrayList<>();

    public FuelRecipeHandler() {
        super(new FuelRecipeGUIComponent());
        GameRegistry.registerFuelHandler(this);
        removeGuiComponent = new FuelRecipeRemoveGUIComponent(this);
    }

    @Override
    public String getID() {
        return "Fuel";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        addRecipe((FuelRecipeData)recipeData);
    }

    private void addRecipe(FuelRecipeData data){
        recipes.add(data);
    }

    @Override
    public void deleteRecipe(int position) {
        recipes.remove(position);
    }

    @Override
    public ArrayList<FuelRecipeData> getRecipes() {
        return recipes;
    }

    @Override
    public int getRecipeCount() {
        return recipes.size();
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound fuel = new NBTTagCompound();
        fuel.setInteger("count", getRecipeCount());
        for(int i = 0; i < getRecipeCount(); i++) {
            NBTTagCompound iterator = new NBTTagCompound();
            FuelRecipeData data = recipes.get(i);
            NBTTagCompound input = new NBTTagCompound();
            Util.writeItemStackToNBT(input, data.itemInputs.get(0));
            iterator.setInteger("burnTime", data.burnTime);
            iterator.setTag("input", input);
            fuel.setTag(String.valueOf(i), iterator);
        }
        tagCompound.setTag("Fuel", fuel);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        if(tagCompound.hasKey("Fuel")){
            NBTTagCompound fuel = tagCompound.getCompoundTag("Fuel");
            int count = fuel.getInteger("count");
            for(int i = 0; i < count; i++) {
                NBTTagCompound iterator = fuel.getCompoundTag(String.valueOf(i));
                int burnTime = iterator.getInteger("burnTime");
                NBTTagCompound input = iterator.getCompoundTag("input");
                ItemStack item = Util.readItemStackFromNBT(input);
                FuelRecipeData data = new FuelRecipeData();
                data.burnTime = burnTime;
                data.itemInputs = new ArrayList<>(Arrays.asList(item));
                addRecipe(data);
            }
        }
    }

    @Override
    public void clear() {
        recipes.clear();
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        for(FuelRecipeData data : recipes){
            if(data.itemInputs.get(0).getItem().equals(fuel.getItem()) && (data.itemInputs.get(0).getItemDamage() == fuel.getItemDamage() || fuel.isItemStackDamageable())){
                return data.burnTime;
            }
        }
        return 0;
    }
}
