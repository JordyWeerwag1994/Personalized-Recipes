package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.ShapelessRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

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
    }

    @Override
    public String getID() {
        return "Shapeless";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        ShapelessRecipes recipe = new ShapelessRecipes(recipeData.itemOutputs.get(0),recipeData.itemInputs);
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
}
