package common.xandayn.personalrecipes.recipe.data;

import net.minecraft.nbt.NBTTagCompound;

public class AnvilRecipeData extends RecipeData {
    public int recipeCost;

    public AnvilRecipeData() {
        super("Anvil");
    }

    @Override
    public void storeInNBT(NBTTagCompound tag) {
        super.storeInNBT(tag);
        tag.setInteger("recipeCost", recipeCost);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        recipeCost = tag.getInteger("recipeCost");
    }
}
