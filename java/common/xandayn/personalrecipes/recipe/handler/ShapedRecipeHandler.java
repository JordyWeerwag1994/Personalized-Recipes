package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.ShapedRecipeData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

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
public class ShapedRecipeHandler extends CustomRecipeHandler{

    ArrayList<ShapedRecipes> recipes;
    /**
     * @param guiComponent The RecipeGUIComponent to associate with this class.
     */
    public ShapedRecipeHandler(RecipeGUIComponent guiComponent) {
        super(guiComponent);
        recipes = new ArrayList<>();
    }

    @Override
    public String getID() {
        return "shaped";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        ShapedRecipeData realData = (ShapedRecipeData)recipeData;
        addRecipe(new ShapedRecipes(realData.getWidth(), realData.getHeight(), realData.itemInputs, realData.itemOutputs[0]));
    }

    @Override
    public void deleteRecipe(int position) {
        removeRecipe(recipes.get(position));
    }

    @Override
    public int getRecipeCount() {
        return recipes.size();
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
