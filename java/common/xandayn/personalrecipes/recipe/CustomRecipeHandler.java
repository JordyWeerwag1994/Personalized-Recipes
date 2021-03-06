package common.xandayn.personalrecipes.recipe;

import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;


/**
 * An abstract class defining what is required to make a custom recipe handler.
 * This class is extended and implemented for each custom recipe type.
 *
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
public abstract class CustomRecipeHandler<T> {

    protected RecipeGUIComponent addGuiComponent;
    protected RecipeGUIComponent removeGuiComponent;

    /**
     * @param addGuiComponent The IRecipeGUIComponent to associate with this class.
     */
    public CustomRecipeHandler(RecipeGUIComponent addGuiComponent){
        this(addGuiComponent, null);
    }

    public CustomRecipeHandler(RecipeGUIComponent addGuiComponent, RecipeGUIComponent removeGuiComponent) {
        this.addGuiComponent = addGuiComponent;
        this.removeGuiComponent = removeGuiComponent;
    }

    /**
     * @return The unique String ID associated with this CustomRecipeHandler
     */
    public abstract String getID();

    /**
     * The function called when a recipe wishes to be registered.
     * @param recipeData The object containing all the required information needed to register a recipe.
     *
     * @see common.xandayn.personalrecipes.recipe.data.RecipeData
     */
    public abstract void registerRecipe(RecipeData recipeData);

    /**
     * This function is called when the recipe at the requested position should be deleted.
     * @param position The position of the recipe to remove.
     */
    public abstract void deleteRecipe(int position);

    public abstract ArrayList<T> getRecipes();

    /**
     * @return The number of recipes registered with the current Handler.
     */
    public abstract int getRecipeCount();

    public abstract void writeRecipesToNBT(NBTTagCompound tagCompound);
    public void readRecipesFromNBT(NBTTagCompound tagCompound) {
        System.out.println("READING: " + getID());
    }
    public abstract void registerARecipeFromNBT(NBTTagCompound tagCompound);

    public void clear() {
        System.out.println("CLEARING " + getID());
    }

    /**
     * @return The IRecipeGUIComponent associated with this CustomRecipeHandler
     *
     * @see common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent
     */
    public RecipeGUIComponent getAddGUIComponent() {
        return addGuiComponent;
    }

    public RecipeGUIComponent getRemoveGUIComponent() { return removeGuiComponent; }
}
