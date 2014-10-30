package common.xandayn.personalrecipes.recipe;

import common.xandayn.personalrecipes.client.gui.IRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.RecipeData;


/**
 * An abstract class defining what is required to make a custom recipe handler.
 * This class is extended and implemented for each custom recipe type.
 */
public abstract class CustomRecipeHandler {

    private final IRecipeGUIComponent guiComponent;

    /**
     * @param guiComponent The IRecipeGUIComponent to associate with this class.
     */
    public CustomRecipeHandler(IRecipeGUIComponent guiComponent){
        this.guiComponent = guiComponent;
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

    /**
     * @return The number of recipes registered with the current Handler.
     */
    public abstract int getRecipeCount();

    /**
     * @return True if non-custom recipes can be removed.
     */
    //TODO: Implement "vanilla" (non-custom) recipe removal and allow overwriting of this function
    public final boolean allowVanillaRecipeRemoval() {
        return false;
    }

    /**
     * @return The IRecipeGUIComponent associated with this CustomRecipeHandler
     *
     * @see common.xandayn.personalrecipes.client.gui.IRecipeGUIComponent
     */
    public final IRecipeGUIComponent getGUIComponent() {
        return guiComponent;
    }
}
