package common.xandayn.personalrecipes.recipe.handler;

import common.xandayn.personalrecipes.client.gui.recipe.add.AnvilRecipeAddGUIComponent;
import common.xandayn.personalrecipes.client.gui.recipe.remove.AnvilRecipeRemoveGUIComponent;
import common.xandayn.personalrecipes.recipe.CustomRecipeHandler;
import common.xandayn.personalrecipes.recipe.data.AnvilRecipeData;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import common.xandayn.personalrecipes.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class AnvilRecipeHandler extends CustomRecipeHandler {

    ArrayList<AnvilRecipeData> recipes;

    public AnvilRecipeHandler() {
        super(new AnvilRecipeAddGUIComponent());
        removeGuiComponent = new AnvilRecipeRemoveGUIComponent(this);
        recipes = new ArrayList<>();
    }

    public void checkRecipeFromAnvil(AnvilUpdateEvent recipeInfo) {
        ItemStack l = recipeInfo.left;
        ItemStack r = recipeInfo.right;
        if(l == null || r == null) return;
        for(AnvilRecipeData data : recipes) {
            if((ItemStack.areItemStacksEqual(l, data.itemInputs.get(0)) && ItemStack.areItemStacksEqual(r, data.itemInputs.get(1))) ||
                    (ItemStack.areItemStacksEqual(l, data.itemInputs.get(1)) && ItemStack.areItemStacksEqual(r, data.itemInputs.get(0)))) {
                recipeInfo.output = data.itemOutputs.get(0).copy();
                recipeInfo.cost = data.recipeCost;
                return;
            }
        }

    }

    @Override
    public String getID() {
        return "Anvil";
    }

    @Override
    public void registerRecipe(RecipeData recipeData) {
        recipes.add((AnvilRecipeData)recipeData);
    }

    @Override
    public void deleteRecipe(int position) {
        recipes.remove(position);
    }

    @Override
    public ArrayList<AnvilRecipeData> getRecipes() {
        return recipes;
    }

    @Override
    public int getRecipeCount() {
        return recipes.size();
    }

    @Override
    public void writeRecipesToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound anvil = new NBTTagCompound();
        anvil.setInteger("count", getRecipeCount());
        for(int i = 0; i < getRecipeCount(); i++) {
            AnvilRecipeData data = recipes.get(i);
            NBTTagCompound iterator = new NBTTagCompound();
            NBTTagCompound inputs = new NBTTagCompound();
            NBTTagCompound output = new NBTTagCompound();
            for(int j = 0; j < 2; j++) {
                NBTTagCompound input = new NBTTagCompound();
                Util.writeItemStackToNBT(input, data.itemInputs.get(j));
                inputs.setTag(String.valueOf(j), input);
            }
            Util.writeItemStackToNBT(output, data.itemOutputs.get(0));
            iterator.setInteger("recipeCost", data.recipeCost);
            iterator.setTag("inputs", inputs);
            iterator.setTag("output", output);
            anvil.setTag(String.valueOf(i), iterator);
        }
        tagCompound.setTag("Anvil", anvil);
    }

    @Override
    public void readRecipesFromNBT(NBTTagCompound tagCompound) {
        super.readRecipesFromNBT(tagCompound);
        if(tagCompound.hasKey("Anvil")) {
            NBTTagCompound anvil = tagCompound.getCompoundTag("Anvil");
            int count = anvil.getInteger("count");
            for(int i = 0; i < count; i++) {
                NBTTagCompound iterator = anvil.getCompoundTag(String.valueOf(i));
                AnvilRecipeData data = new AnvilRecipeData();
                NBTTagCompound inputsTag = iterator.getCompoundTag("inputs");
                NBTTagCompound outputTag = iterator.getCompoundTag("output");
                data.itemOutputs = new ArrayList<>(Arrays.asList(Util.readItemStackFromNBT(outputTag)));
                data.itemInputs = new ArrayList<>();
                data.recipeCost = iterator.getInteger("recipeCost");
                for(int j = 0; j < 2; j++) {
                    NBTTagCompound input = inputsTag.getCompoundTag(String.valueOf(j));
                    data.itemInputs.add(Util.readItemStackFromNBT(input));
                }
                registerRecipe(data);
            }
        }
    }

    @Override
    public void registerARecipeFromNBT(NBTTagCompound tagCompound) {
        AnvilRecipeData data = new AnvilRecipeData();
        data.readFromNBT(tagCompound);
        registerRecipe(data);
    }

    @Override
    public void clear() {
        super.clear();
        recipes.clear();
    }
}
