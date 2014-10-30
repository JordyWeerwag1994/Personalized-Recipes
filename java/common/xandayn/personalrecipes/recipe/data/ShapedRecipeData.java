package common.xandayn.personalrecipes.recipe.data;

/**
 *
 */
public class ShapedRecipeData extends RecipeData {

    /**
     * The width of the recipe (1-3)
     */
    private final int width;

    /**
     * The height of the recipe (1-3)
     */
    private final int height;

    /**
     * @param recipeType The alias associated with a CustomRecipeHandler that can parse this recipe.
     * @param width The width of the recipe.
     * @param height The height of the recipe.
     */
    public ShapedRecipeData(String recipeType, int width, int height) {
        super(recipeType);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
