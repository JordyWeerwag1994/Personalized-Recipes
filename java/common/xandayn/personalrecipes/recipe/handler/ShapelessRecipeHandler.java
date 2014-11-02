package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.ShapelessRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.ArrayList;

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
