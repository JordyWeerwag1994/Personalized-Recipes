package common.xandayn.personalrecipes.recipe;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.common.container.component.IRecipeContainerComponent;
import common.xandayn.personalrecipes.common.container.component.ShapedRecipeContainerComponent;
import common.xandayn.personalrecipes.common.container.component.ShapelessRecipeContainerComponent;
import common.xandayn.personalrecipes.common.container.component.SmeltingRecipeContainerComponent;
import common.xandayn.personalrecipes.recipe.handlers.FurnaceRecipeHandler;
import common.xandayn.personalrecipes.recipe.handlers.ShapedRecipeHandler;
import common.xandayn.personalrecipes.recipe.handlers.ICustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.handlers.ShapelessRecipeHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * PersonalizedRecipes - RecipeRegistry
 *
 * A class for registering and handling Recipe Handlers.
 *
 * @license
 *   Copyright (C) 2014  xandayn
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author xandayn
 */
public class RecipeRegistry {
    private static HashMap<String, Class<? extends ICustomRecipeHandler>> _RECIPE_HANDLER_CLASSES = new HashMap<>();
    private static HashMap<String, ICustomRecipeHandler> _HANDLER_INSTANCES = new HashMap<>();
    private static HashMap<Integer, IRecipeContainerComponent> _RECIPE_CONTAINER_COMPONENT = new HashMap<>();
    private static HashMap<String, Integer> _RECIPE_ALIAS_IDS = new HashMap<>();
    private static ArrayList<IRecipe> _RECIPES_TO_WRITE = new ArrayList<>();
    private static ArrayList<String> _RECIPE_ALIASES = new ArrayList<>();
    private static ArrayList<IRecipe> _SERVER_RECIPES = new ArrayList<>();
    private static ArrayList<String> _SERVER_RECIPE_ALIASES = new ArrayList<>();

    static {
        registerRecipeHandler("shaped", ShapedRecipeHandler.class, new ShapedRecipeContainerComponent());
        registerRecipeHandler("shapeless", ShapelessRecipeHandler.class, new ShapelessRecipeContainerComponent());
        registerRecipeHandler("smelting", FurnaceRecipeHandler.class, new SmeltingRecipeContainerComponent());
    }

    public static void registerRecipeHandler(String alias, Class<? extends ICustomRecipeHandler> handlerClass, IRecipeContainerComponent component){
        if(!_HANDLER_INSTANCES.containsKey(alias)) {
            try {
                ICustomRecipeHandler instance = handlerClass.newInstance();
                _HANDLER_INSTANCES.put(alias, instance);
                _RECIPE_HANDLER_CLASSES.put(alias, handlerClass);
                _RECIPE_CONTAINER_COMPONENT.put(_RECIPE_CONTAINER_COMPONENT.size(), component);
                _RECIPE_ALIAS_IDS.put(alias, _RECIPE_ALIAS_IDS.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean containsAlias(String alias){
        return _RECIPE_HANDLER_CLASSES.containsKey(alias);
    }

    public static Class<? extends ICustomRecipeHandler> getHandlerClass(String alias) {
        return _RECIPE_HANDLER_CLASSES.get(alias);
    }

    public static void registerRecipe(String alias, IRecipe recipe){
        if(!_RECIPES_TO_WRITE.contains(recipe)) {
            _HANDLER_INSTANCES.get(alias).registerRecipe(recipe);
            if (PersonalizedRecipes.CURRENT_SIDE.isClient()) {
                _RECIPE_ALIASES.add(alias);
                _RECIPES_TO_WRITE.add(recipe);
            } else {
                _SERVER_RECIPE_ALIASES.add(alias);
                _SERVER_RECIPES.add(recipe);
            }
        }
    }

    public static void writeRecipesToNBT(NBTTagCompound tag){
        if(_RECIPES_TO_WRITE.size() > 0) {
            NBTTagCompound recipes = new NBTTagCompound();
            recipes.setInteger("recipeCount", _RECIPES_TO_WRITE.size());
            for (int i = 0; i < _RECIPES_TO_WRITE.size(); i++) {
                NBTTagCompound recipe = new NBTTagCompound();
                recipes.setString(i + "_type", _RECIPE_ALIASES.get(i));
                _HANDLER_INSTANCES.get(_RECIPE_ALIASES.get(i)).writeToNBT(_RECIPES_TO_WRITE.get(i), recipe);
                recipes.setTag(String.valueOf(i), recipe);
            }
            tag.setTag("recipes", recipes);
        }
    }

    public static void writeRecipeToNBT(NBTTagCompound tag, IRecipe recipe, String alias){
        _HANDLER_INSTANCES.get(alias).writeToNBT(recipe, tag);
    }

    public static ArrayList<String> getAliases(){
        return new ArrayList<>(_RECIPE_HANDLER_CLASSES.keySet());
    }

    public static int getIDFromAlias(String alias){
        return _RECIPE_ALIAS_IDS.containsKey(alias) ? _RECIPE_ALIAS_IDS.get(alias) : -1;
    }

    public static IRecipeContainerComponent getComponentFromAlias(int aliasID){
        return _RECIPE_CONTAINER_COMPONENT.get(aliasID);
    }

    public static IRecipe readRecipeFromNBT(String alias, NBTTagCompound tag){
        return _HANDLER_INSTANCES.get(alias).readFromNBT(tag);
    }

    public static void readRecipesFromNBT(NBTTagCompound readTag) {
        NBTTagCompound tag = readTag.getCompoundTag("recipes");
        int recipeCount = tag.getInteger("recipeCount");
        for(int i = 0; i < recipeCount; i++){
            String type = tag.getString(i + "_type");
            NBTTagCompound recipeTag = tag.getCompoundTag(String.valueOf(i));
            IRecipe recipe = _HANDLER_INSTANCES.get(type).readFromNBT(recipeTag);
            registerRecipe(type, recipe);
        }
    }

    public static void removeRegisteredRecipe(int recipePos){
        if(recipePos >= 0 && recipePos < _RECIPES_TO_WRITE.size()) {
            IRecipe removed = _RECIPES_TO_WRITE.remove(recipePos);
            String type = _RECIPE_ALIASES.remove(recipePos);
             _HANDLER_INSTANCES.get(type).removeRecipe(removed);
        }
    }

    public static ArrayList getListOfType(String type, boolean getPositions){
        ArrayList<IRecipe> recipes = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
        if(PersonalizedRecipes.CURRENT_SIDE.isClient()) {
            for (int i = 0; i < _RECIPES_TO_WRITE.size(); i++) {
                if (_RECIPE_ALIASES.get(i).equals(type)) {
                    recipes.add(_RECIPES_TO_WRITE.get(i));
                    integers.add(i);
                }
            }
        }else{
            for (int i = 0; i < _SERVER_RECIPES.size(); i++) {
                if (_SERVER_RECIPE_ALIASES.get(i).equals(type)) {
                    recipes.add(_SERVER_RECIPES.get(i));
                    integers.add(i);
                }
            }
        }
        return getPositions ? integers : recipes;
    }

    @SideOnly(Side.CLIENT)
    public static void removeAllRecipes() {
        for(int i = 0; i < _RECIPES_TO_WRITE.size(); i++){
            _HANDLER_INSTANCES.get(_RECIPE_ALIASES.get(i)).removeRecipe(_RECIPES_TO_WRITE.get(i));
        }
        for(int i = 0; i < _SERVER_RECIPES.size(); i++){
            _HANDLER_INSTANCES.get(_SERVER_RECIPE_ALIASES.get(i)).removeRecipe(_SERVER_RECIPES.get(i));
        }
        _SERVER_RECIPES = new ArrayList<>();
        _SERVER_RECIPE_ALIASES = new ArrayList<>();
    }

    @SideOnly(Side.CLIENT)
    public static void reloadSinglePlayerRecipes(){
        for(int i = 0; i < _RECIPES_TO_WRITE.size(); i++) {
            _HANDLER_INSTANCES.get(_RECIPE_ALIASES.get(i)).registerRecipe(_RECIPES_TO_WRITE.get(i));
        }
    }
}
