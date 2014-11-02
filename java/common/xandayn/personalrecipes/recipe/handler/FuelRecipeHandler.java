package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.FuelRecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.FuelRecipeData;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class FuelRecipeHandler extends CustomRecipeHandler<FuelRecipeData> implements IFuelHandler {

    private ArrayList<FuelRecipeData> recipes = new ArrayList<>();

    public FuelRecipeHandler() {
        super(new FuelRecipeGUIComponent());
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    public String getID() {
        return "Fuel";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        addRecipe((FuelRecipeData)recipeData);
    }

    private void addRecipe(FuelRecipeData data){
        recipes.add(data);
    }

    @Override
    public void deleteRecipe(int position) {
        recipes.remove(position);
    }

    @Override
    public ArrayList<FuelRecipeData> getRecipes() {
        return recipes;
    }

    @Override
    public int getRecipeCount() {
        return recipes.size();
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        for(FuelRecipeData data : recipes){
            if(ItemStack.areItemStacksEqual(data.itemInputs.get(0), fuel)){
                return data.burnTime;
            }
        }
        return 0;
    }
}
