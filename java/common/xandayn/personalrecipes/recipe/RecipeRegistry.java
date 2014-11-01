package common.xandayn.personalrecipes.recipe;

import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.ShapedRecipeGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.TestRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.handler.ShapedRecipeHandler;

import java.util.HashMap;
import java.util.Set;

/**
 * The class that handles registering and accessing CustomRecipeHandlers.
 *
 * @see CustomRecipeHandler
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
public class RecipeRegistry {

    /**
     * The backing HashMap for recipe registration.
     */
    private static HashMap<String, CustomRecipeHandler> _RECIPE_HANDLERS;

    /**
     * A HashMap containing a String value, with an Integer key, where key = value.hashCode(),
     * this gives a way to convert an integer value to a CustomRecipeHandler.
     *
     * Potential Bug: If the hash of one string is equal to another this will break, but that
     * is very unlikely.
     */
    private static HashMap<Integer, String> _HANDLER_HASHES;

    static {
        _RECIPE_HANDLERS = new HashMap<>();
        _HANDLER_HASHES = new HashMap<>();

        registerRecipeHandler("Shaped", new ShapedRecipeHandler(new ShapedRecipeGUIComponent()));
        registerRecipeHandler("Shapeless", new ShapedRecipeHandler(new TestRecipeGUIComponent())); //TODO: create an IRecipeGUIComponent and use it instead of null
        registerRecipeHandler("Furnace", new ShapedRecipeHandler(new TestRecipeGUIComponent())); //TODO: create an IRecipeGUIComponent and use it instead of null
    }

    /**
     * A function used to register CustomRecipeHandlers for use within the mod. CustomRecipeHandlers may be registered
     * multiple times under different aliases, however, aliases must be unique, and never repeated. Registration should occur during
     * FMLPreInitialization.
     *
     * @param alias The name to associate the recipeHandler with, should be 10 or less characters.
     * @param recipeHandler A class extending CustomRecipeHandler, used when registering a recipe for the alias supplied.
     *
     * @throws java.lang.RuntimeException If parameter alias is already registered.
     *
     * @see CustomRecipeHandler
     * @see cpw.mods.fml.common.event.FMLPreInitializationEvent
     */
    public static void registerRecipeHandler(String alias, CustomRecipeHandler recipeHandler) {
        if(!_RECIPE_HANDLERS.containsKey(alias)){
            _RECIPE_HANDLERS.put(alias, recipeHandler);
            _HANDLER_HASHES.put(alias.hashCode(), alias);
            return;
        }
        throw new RuntimeException("Cannot register alias: \"" + alias + "\", alias is already registered.");
    }

    /**
     * A way to convert a String alias to an int ID.
     * @param alias The alias you wish to generate an ID for.
     * @return The ID of the alias supplied.
     *
     * @see String#hashCode()
     */
    public static int getAliasIntID(String alias){
        return alias.hashCode();
    }

    /**
     * A method used to register recipes.
     *
     * @param handlerID The integer ID for the handler, returned by RecipeRegistry.getAliasIntID(String)
     * @param data An object containing information required to create the recipe.
     *
     * @see common.xandayn.personalrecipes.recipe.RecipeRegistry#getAliasIntID(String)
     * @see common.xandayn.personalrecipes.recipe.data.RecipeData
     */
    public static void registerRecipe(int handlerID, RecipeData data){
        _RECIPE_HANDLERS.get(_HANDLER_HASHES.get(handlerID)).registerRecipe(data);
    }

    /**
     * A method used to remove registered recipes
     *
     * @param handlerID The integer ID for the handler, returned by RecipeRegistry.getAliasIntID(String)
     * @param recipeID An Integer that represents the recipe you wish to remove, varies per implementation of CustomRecipeHandler
     *
     * @see common.xandayn.personalrecipes.recipe.RecipeRegistry#getAliasIntID(String)
     * @see common.xandayn.personalrecipes.recipe.CustomRecipeHandler
     */
    public static void removeRecipe(int handlerID, int recipeID){
        _RECIPE_HANDLERS.get(_HANDLER_HASHES.get(handlerID)).deleteRecipe(recipeID);
    }

    public static int registeredRecipeHandlerCount(){
        return _RECIPE_HANDLERS.keySet().size();
    }

    /**
     * A method that checks to see if a specified alias has been registered.
     * @param alias The alias to check against.
     * @return True if alias has already been registered, false otherwise.
     */
    public static boolean isAliasUnique(String alias){
        for(String s : _RECIPE_HANDLERS.keySet()){
            System.out.println(s);
        }
        return !_RECIPE_HANDLERS.containsKey(alias);
    }

    public static Set<String> getRegisteredAliases(){
        return _RECIPE_HANDLERS.keySet();
    }

    public static RecipeGUIComponent getRecipeGUIComponent(int aliasIntID) {
        return _RECIPE_HANDLERS.get(_HANDLER_HASHES.get(aliasIntID)).getGUIComponent();
    }
}
