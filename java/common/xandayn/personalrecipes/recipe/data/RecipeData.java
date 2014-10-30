package common.xandayn.personalrecipes.recipe.data;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * A class defining information required to make a recipe, extend if extra information is needed.
 */
public class RecipeData {

    /**
     * @see RecipeData#getRecipeType()
     */
    private final String recipeType;
    /**
     * An array containing all the required item inputs, leave null if not needed
     */
    public ItemStack[] itemInputs = null;
    /**
     * An array containing all the required item outputs, leave null if not needed
     */
    public ItemStack[] itemOutputs = null;
    /**
     * An array containing all the required fluid inputs, leave null if not needed
     */
    public FluidStack[] fluidInputs = null;
    /**
     * An array containing all the required fluid outputs, leave null if not needed
     */
    public FluidStack[] fluidOutputs = null;

    public RecipeData(String recipeType){
        this.recipeType = recipeType;
    }

    /**
     * @return The alias associated with a CustomRecipeHandler that can parse this recipe.
     *
     * @see common.xandayn.personalrecipes.recipe.CustomRecipeHandler
     */
    public final String getRecipeType() {
        return recipeType;
    }

}
