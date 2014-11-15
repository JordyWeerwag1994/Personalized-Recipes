package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.add.ShapelessRecipeGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.remove.ShapelessRecipeRemoveGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.util.Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

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
public class ShapelessRecipeHandler extends CustomRecipeHandler<ShapelessRecipes> {

    private ArrayList<ShapelessRecipes> recipeList = new ArrayList<>();

    public ShapelessRecipeHandler() {
        super(new ShapelessRecipeGUIComponent());
        removeGuiComponent = new ShapelessRecipeRemoveGUIComponent(this);
    }

    @Override
    public String getID() {
        return "Shapeless";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        ShapelessRecipes recipe = new ShapelessRecipes(recipeData.itemOutputs.get(0), recipeData.itemInputs);
        addRecipe(recipe);
    }

    private void addRecipe(ShapelessRecipes recipe){
        recipeList.add(recipe);
        GameRegistry.addRecipe(recipe);
    }

    @Override
    public void deleteRecipe(int position) {
        ShapelessRecipes recipe = recipeList.get(position);
        if (CraftingManager.getInstance().getRecipeList().remove(recipe)){
            recipeList.remove(recipe);
        } else {
            System.err.println("Unable to remove recipe for " + recipe.getRecipeOutput().getDisplayName());
        }
    }

    @Override
    public ArrayList<ShapelessRecipes> getRecipes(){
        return recipeList;
    }

    @Override
    public int getRecipeCount() {
        return recipeList.size();
    }

    @Override
    public void writeRecipesToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound shapeless = new NBTTagCompound();
        shapeless.setInteger("count",  recipeList.size());
        for(int i = 0; i < recipeList.size(); i++){
            ShapelessRecipes recipe = recipeList.get(i);
            NBTTagCompound iterator = new NBTTagCompound();
            NBTTagCompound inputs = new NBTTagCompound();
            NBTTagCompound output = new NBTTagCompound();
            iterator.setInteger("inputCount", recipe.getRecipeSize());
            for(int j = 0; j < recipe.getRecipeSize(); j++){
                NBTTagCompound input = new NBTTagCompound();
                Util.writeItemStackToNBT(input, (ItemStack)recipe.recipeItems.get(j));
                inputs.setTag(String.valueOf(j), input);
            }
            Util.writeItemStackToNBT(output, recipe.getRecipeOutput());
            iterator.setTag("inputs", inputs);
            iterator.setTag("output", output);
            shapeless.setTag(String.valueOf(i), iterator);
        }
        tagCompound.setTag("Shapeless", shapeless);
    }

    @Override
    public void readRecipesFromNBT(NBTTagCompound tagCompound) {
        super.readRecipesFromNBT(tagCompound);
        if(tagCompound.hasKey("Shapeless")) {
            NBTTagCompound shapeless = tagCompound.getCompoundTag("Shapeless");
            int count = shapeless.getInteger("count");
            for (int i = 0; i < count; i++) {
                NBTTagCompound iterator = shapeless.getCompoundTag(String.valueOf(i));
                NBTTagCompound inputTags = iterator.getCompoundTag("inputs");
                NBTTagCompound outputTag = iterator.getCompoundTag("output");
                int inputCount = iterator.getInteger("inputCount");
                ArrayList<ItemStack> inputs = new ArrayList<>(inputCount);
                for (int j = 0; j < inputCount; j++) {
                    inputs.add(Util.readItemStackFromNBT(inputTags.getCompoundTag(String.valueOf(j))));
                }
                ItemStack output = Util.readItemStackFromNBT(outputTag);
                addRecipe(new ShapelessRecipes(output, inputs));
            }
        }
    }

    @Override
    public void registerARecipeFromNBT(NBTTagCompound tagCompound) {
        RecipeData data = new RecipeData("Shapeless");
        data.readFromNBT(tagCompound);
        registerRecipe(data);
    }

    @Override
    public void clear() {
        super.clear();
        int count = recipeList.size();
        for(int i = 0; i < count; i++){
            deleteRecipe(0);
        }
        recipeList.clear();
    }
}
