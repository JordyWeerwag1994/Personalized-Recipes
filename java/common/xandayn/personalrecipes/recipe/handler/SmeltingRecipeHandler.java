package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.SmeltingRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.SmeltingRecipeData;
import common.xandayn.personalrecipes.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
public class SmeltingRecipeHandler extends CustomRecipeHandler<SmeltingRecipeData> {

    private ArrayList<SmeltingRecipeData> data = new ArrayList<>();
    private HashMap<ItemStack, Float> experienceList;

    public SmeltingRecipeHandler() {
        super(new SmeltingRecipeGUIComponent());
        getExperienceList();
    }

    @SuppressWarnings("unchecked")
    private void getExperienceList(){
        try {
            Field eList = FurnaceRecipes.class.getDeclaredField("experienceList");
            eList.setAccessible(true);
            experienceList = (HashMap<ItemStack, Float>)eList.get(FurnaceRecipes.smelting());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getID() {
        return "Smelting";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        FurnaceRecipes.smelting().func_151394_a(recipeData.itemInputs.get(0), recipeData.itemOutputs.get(0), ((SmeltingRecipeData)recipeData).smeltingEXP);
        data.add((SmeltingRecipeData)recipeData);
    }

    @Override
    public void deleteRecipe(int position) {
        SmeltingRecipeData removed = data.remove(position);
        ItemStack removedItem = (ItemStack)FurnaceRecipes.smelting().getSmeltingList().remove(removed.itemInputs.get(0));
        experienceList.remove(removedItem);
    }

    @Override
    public ArrayList<SmeltingRecipeData> getRecipes() {
        return null;
    }

    @Override
    public int getRecipeCount() {
        return data.size();
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound innerTag = new NBTTagCompound();
        innerTag.setInteger("count", data.size());
        for(int i = 0; i < data.size(); i++){
            SmeltingRecipeData curData = data.get(i);
            NBTTagCompound state = new NBTTagCompound();
            NBTTagCompound input = new NBTTagCompound();
            NBTTagCompound output = new NBTTagCompound();
            Util.writeItemStackToNBT(input, curData.itemInputs.get(0));
            Util.writeItemStackToNBT(output, curData.itemOutputs.get(0));
            state.setTag("input", input);
            state.setTag("output", output);
            state.setFloat("value", curData.smeltingEXP);
            innerTag.setTag(String.valueOf(i), state);
        }
        tagCompound.setTag("Smelting", innerTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        if(tagCompound.hasKey("Smelting")) {
            NBTTagCompound smelting = tagCompound.getCompoundTag("Smelting");
            int count = smelting.getInteger("count");
            for (int i = 0; i < count; i++) {
                NBTTagCompound state = smelting.getCompoundTag(String.valueOf(i));
                NBTTagCompound inputTag = state.getCompoundTag("input");
                NBTTagCompound outputTag = state.getCompoundTag("output");
                final ItemStack input = Util.readItemStackFromNBT(inputTag);
                final ItemStack output = Util.readItemStackFromNBT(outputTag);
                float value = state.getFloat("value");
                SmeltingRecipeData sData = new SmeltingRecipeData();
                sData.smeltingEXP = value;
                sData.itemInputs = new ArrayList<>(Arrays.asList(input));
                sData.itemOutputs = new ArrayList<>(Arrays.asList(output));
                registerRecipe(sData);
            }
        }
    }

    @Override
    public void clear() {
        for(int i = 0; i < data.size(); i++){
            deleteRecipe(i);
        }
    }
}
