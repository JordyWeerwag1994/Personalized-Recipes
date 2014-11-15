package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.add.ShapedRecipeGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.remove.ShapedRecipeRemoveGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.ShapedRecipeData;
import common.xandayn.personalrecipes.util.Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
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
public class ShapedRecipeHandler extends CustomRecipeHandler<ShapedRecipes> {

    ArrayList<ShapedRecipes> recipes = new ArrayList<>();
    public ShapedRecipeHandler() {
        super(new ShapedRecipeGUIComponent());
        this.removeGuiComponent = new ShapedRecipeRemoveGUIComponent(this);
    }

    @Override
    public String getID() {
        return "Shaped";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        ShapedRecipeData realData = (ShapedRecipeData)recipeData;
        addRecipe(new ShapedRecipes(realData.getWidth(), realData.getHeight(), realData.itemInputs.toArray(new ItemStack[realData.itemInputs.size()]), realData.itemOutputs.get(0)));
    }

    @Override
    public void deleteRecipe(int position) {
        removeRecipe(recipes.get(position));
    }

    @Override
    public ArrayList<ShapedRecipes> getRecipes() {
        return recipes;
    }

    @Override
    public int getRecipeCount() {
        return recipes.size();
    }

    @Override
    public void writeRecipesToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound shapeless = new NBTTagCompound();
        shapeless.setInteger("count",  recipes.size());
        for(int i = 0; i < recipes.size(); i++){
            ShapedRecipes recipe = recipes.get(i);
            NBTTagCompound iterator = new NBTTagCompound();
            NBTTagCompound inputs = new NBTTagCompound();
            NBTTagCompound output = new NBTTagCompound();
            iterator.setInteger("inputCount", recipe.getRecipeSize());
            iterator.setInteger("width", recipe.recipeWidth);
            iterator.setInteger("height", recipe.recipeHeight);
            for(int j = 0; j < recipe.getRecipeSize(); j++){
                NBTTagCompound input = new NBTTagCompound();
                Util.writeItemStackToNBT(input, recipe.recipeItems[j]);
                inputs.setTag(String.valueOf(j), input);
            }
            Util.writeItemStackToNBT(output, recipe.getRecipeOutput());
            iterator.setTag("inputs", inputs);
            iterator.setTag("output", output);
            shapeless.setTag(String.valueOf(i), iterator);
        }
        tagCompound.setTag("Shaped", shapeless);
    }

    @Override
    public void readRecipesFromNBT(NBTTagCompound tagCompound) {
        super.readRecipesFromNBT(tagCompound);
        if(tagCompound.hasKey("Shaped")) {
            NBTTagCompound shapeless = tagCompound.getCompoundTag("Shaped");
            int count = shapeless.getInteger("count");
            for (int i = 0; i < count; i++) {
                NBTTagCompound iterator = shapeless.getCompoundTag(String.valueOf(i));
                NBTTagCompound inputTags = iterator.getCompoundTag("inputs");
                NBTTagCompound outputTag = iterator.getCompoundTag("output");
                int inputCount = iterator.getInteger("inputCount");
                int width = iterator.getInteger("width");
                int height = iterator.getInteger("height");
                ArrayList<ItemStack> inputs = new ArrayList<>(inputCount);
                for (int j = 0; j < inputCount; j++) {
                    inputs.add(Util.readItemStackFromNBT(inputTags.getCompoundTag(String.valueOf(j))));
                }
                ItemStack output = Util.readItemStackFromNBT(outputTag);
                addRecipe(new ShapedRecipes(width, height, inputs.toArray(new ItemStack[inputCount]), output));
            }
        }
    }

    @Override
    public void registerARecipeFromNBT(NBTTagCompound tagCompound) {
        ShapedRecipeData data = new ShapedRecipeData();
        data.readFromNBT(tagCompound);
        registerRecipe(data);
    }

    @Override
    public void clear() {
        super.clear();
        int count = recipes.size();
        for(int i = 0; i < count; i++){
            deleteRecipe(0);
        }
        recipes.clear();
    }

    /**
     * A utility function that handles recipe registration.
     * @param recipe The ShapedRecipes to register.
     */
    private void addRecipe(ShapedRecipes recipe){
        GameRegistry.addRecipe(recipe);
        recipes.add(recipe);
    }

    /**
     * A utility function that handles recipe removal.
     * @param recipe The recipe to remove.
     */
    private void removeRecipe(ShapedRecipes recipe){
        if (CraftingManager.getInstance().getRecipeList().remove(recipe)){
            recipes.remove(recipe);
        } else {
            System.err.println("Unable to remove recipe for " + recipe.getRecipeOutput().getDisplayName());
        }
    }
}
