package common.xandayn.personalrecipes.recipe;

import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.handler.FuelRecipeHandler;
import common.xandayn.personalrecipes.recipe.handler.ShapedRecipeHandler;
import common.xandayn.personalrecipes.recipe.handler.ShapelessRecipeHandler;
import common.xandayn.personalrecipes.recipe.handler.SmeltingRecipeHandler;

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

    public static final RecipeRegistry INSTANCE = new RecipeRegistry();

    /**
     * The backing HashMap for recipe registration.
     */
    private HashMap<String, CustomRecipeHandler> _RECIPE_HANDLERS;

    /**
     * A HashMap containing a String value, with an Integer key, where key = value.hashCode(),
     * this gives a way to convert an integer value to a CustomRecipeHandler.
     *
     * Potential Bug: If the hash of one string is equal to another this will break, but that
     * is very unlikely.
     */
    private HashMap<Integer, String> _HANDLER_HASHES;

    private RecipeRegistry(){
        _RECIPE_HANDLERS = new HashMap<>();
        _HANDLER_HASHES = new HashMap<>();

        registerRecipeHandler(new ShapedRecipeHandler());
        registerRecipeHandler(new ShapelessRecipeHandler());
        registerRecipeHandler(new FuelRecipeHandler());
        registerRecipeHandler(new SmeltingRecipeHandler());
    }

    /**
     * A function used to register CustomRecipeHandlers for use within the mod. CustomRecipeHandlers may be registered
     * multiple times under different aliases, however, aliases must be unique, and never repeated. Registration should occur during
     * FMLPreInitialization if integrated with a mod (not using a plugin).
     *
     * @param recipeHandler A class extending CustomRecipeHandler, used when registering a recipe for the alias supplied.
     *
     * @throws java.lang.RuntimeException If CustomRecipeHandler.getID() is already registered.
     *
     * @see CustomRecipeHandler
     * @see CustomRecipeHandler#getID()
     */
    public void registerRecipeHandler(CustomRecipeHandler recipeHandler) {
        if(!_RECIPE_HANDLERS.containsKey(recipeHandler.getID())){
            _RECIPE_HANDLERS.put(recipeHandler.getID(), recipeHandler);
            _HANDLER_HASHES.put(recipeHandler.getID().hashCode(), recipeHandler.getID());
            return;
        }
        throw new RuntimeException("Cannot register alias: \"" + recipeHandler.getID() + "\", alias is already registered.");
    }

    /**
     * A way to convert a String alias to an int ID.
     * @param alias The alias you wish to generate an ID for.
     * @return The ID of the alias supplied.
     *
     * @see String#hashCode()
     */
    public int getAliasIntID(String alias){
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
    public void registerRecipe(int handlerID, RecipeData data){
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
    public void removeRecipe(int handlerID, int recipeID){
        _RECIPE_HANDLERS.get(_HANDLER_HASHES.get(handlerID)).deleteRecipe(recipeID);
    }

    public int registeredRecipeHandlerCount(){
        return _RECIPE_HANDLERS.keySet().size();
    }

    /**
     * A method that checks to see if a specified alias has been registered.
     * @param alias The alias to check against.
     * @return True if alias has already been registered, false otherwise.
     */
    public boolean isAliasUnique(String alias){
        for(String s : _RECIPE_HANDLERS.keySet()){
            System.out.println(s);
        }
        return !_RECIPE_HANDLERS.containsKey(alias);
    }

    public Set<String> getRegisteredAliases(){
        return _RECIPE_HANDLERS.keySet();
    }

    public RecipeGUIComponent getRecipeGUIComponent(int aliasIntID) {
        return _RECIPE_HANDLERS.get(_HANDLER_HASHES.get(aliasIntID)).getGUIComponent();
    }
}
