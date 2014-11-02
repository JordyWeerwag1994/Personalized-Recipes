package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.SmeltingRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.SmeltingRecipeData;
import net.minecraft.item.crafting.FurnaceRecipes;

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
public class SmeltingRecipeHandler extends CustomRecipeHandler<SmeltingRecipeData> {

    public SmeltingRecipeHandler() {
        super(new SmeltingRecipeGUIComponent());
    }

    @Override
    public String getID() {
        return "Smelting";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        FurnaceRecipes.smelting().func_151394_a(recipeData.itemInputs.get(0), recipeData.itemOutputs.get(0), ((SmeltingRecipeData)recipeData).smeltingEXP);
    }

    @Override
    public void deleteRecipe(int position) {

    }

    @Override
    public ArrayList<SmeltingRecipeData> getRecipes() {
        return null;
    }

    @Override
    public int getRecipeCount() {
        return 0;
    }
}
