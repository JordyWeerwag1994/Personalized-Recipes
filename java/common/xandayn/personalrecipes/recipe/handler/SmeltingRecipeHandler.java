package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.SmeltingRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.SmeltingRecipeData;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;

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
