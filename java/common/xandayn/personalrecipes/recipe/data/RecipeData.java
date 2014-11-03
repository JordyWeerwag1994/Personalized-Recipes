package common.xandayn.personalrecipes.recipe.data;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * A class defining information required to make a recipe, extend if extra information is needed.
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
public class RecipeData {

    /**
     * @see RecipeData#getRecipeType()
     */
    private final String recipeType;
    /**
     * An array containing all the required item inputs, leave null if not needed
     */
    public ArrayList<ItemStack> itemInputs = null;
    /**
     * An array containing all the required item outputs, leave null if not needed
     */
    public ArrayList<ItemStack> itemOutputs = null;
    /**
     * An array containing all the required fluid inputs, leave null if not needed
     */
    public ArrayList<FluidStack> fluidInputs = null;
    /**
     * An array containing all the required fluid outputs, leave null if not needed
     */
    public ArrayList<FluidStack> fluidOutputs = null;

    public RecipeData(String recipeType){
        this.recipeType = recipeType;
    }

    /**
     * A function that causes this recipe to register itself.
     */
    public final void register() { RecipeRegistry.INSTANCE.registerRecipe(RecipeRegistry.INSTANCE.getAliasIntID(recipeType), this); }

    /**
     * @return The alias associated with a CustomRecipeHandler that can parse this recipe.
     *
     * @see common.xandayn.personalrecipes.recipe.CustomRecipeHandler
     */
    public final String getRecipeType() {
        return recipeType;
    }

    @Override
    public String toString() {
        return "[".concat(recipeType).concat("]");
    }
}
