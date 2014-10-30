package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.IRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.recipe.data.ShapedRecipeData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;

public class ShapedRecipeHandler extends CustomRecipeHandler{

    ArrayList<ShapedRecipes> recipes;
    /**
     * @param guiComponent The IRecipeGUIComponent to associate with this class.
     */
    public ShapedRecipeHandler(IRecipeGUIComponent guiComponent) {
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
