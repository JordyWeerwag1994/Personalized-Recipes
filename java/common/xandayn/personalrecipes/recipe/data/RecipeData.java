package common.xandayn.personalrecipes.recipe.data;

import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveNewRecipeFromServer;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveRecipeRemovalFromServer;
import common.xandayn.personalrecipes.common.packet.to_server.ServerReceiveNewRecipe;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import common.xandayn.personalrecipes.util.Util;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * A class defining information required to make a recipe, extend if extra information is needed.
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class RecipeData {

    /**
     * @see RecipeData#getRecipeType()
     */
    private final String recipeType;
    /**
     * An array containing all the required item inputs, leave null if not needed
     */
    public ArrayList<ItemStack> itemInputs = null;
    /**
     * An array containing all the required item outputs, leave null if not needed
     */
    public ArrayList<ItemStack> itemOutputs = null;
    /**
     * An array containing all the required fluid inputs, leave null if not needed
     */
    public ArrayList<FluidStack> fluidInputs = null;
    /**
     * An array containing all the required fluid outputs, leave null if not needed
     */
    public ArrayList<FluidStack> fluidOutputs = null;

    public RecipeData(String recipeType){
        this.recipeType = recipeType;
    }

    /**
     * A function that causes this recipe to register itself on both the client and the server.
     */
    @SideOnly(Side.CLIENT)
    public final void register() {
        if(FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
            NBTTagCompound tag = new NBTTagCompound();
            storeInNBT(tag);
            NetworkHandler.NETWORK.sendToServer(new ServerReceiveNewRecipe(tag));
        } else {
            RecipeRegistry.INSTANCE.registerRecipe(RecipeRegistry.INSTANCE.getAliasIntID(recipeType), this);
        }
    }

    public void storeInNBT(NBTTagCompound tag) {
        int iic = getArrayListCount(itemInputs);
        int ioc = getArrayListCount(itemOutputs);
        int fic = getArrayListCount(fluidInputs);
        int foc = getArrayListCount(fluidInputs);
        NBTTagCompound iit = new NBTTagCompound();
        iit.setInteger("count", iic);
        for(int i = 0; i < iic; i++) {
            NBTTagCompound input = new NBTTagCompound();
            Util.writeItemStackToNBT(input, itemInputs.get(i));
            iit.setTag(String.valueOf(i), input);
        }
        NBTTagCompound iot = new NBTTagCompound();
        iot.setInteger("count", ioc);
        for(int i = 0; i < ioc; i++) {
            NBTTagCompound output = new NBTTagCompound();
            Util.writeItemStackToNBT(output, itemOutputs.get(i));
            iot.setTag(String.valueOf(i), output);
        }
        NBTTagCompound fit = new NBTTagCompound();
        fit.setInteger("count", fic);
        for(int i = 0; i < fic; i++) {
            NBTTagCompound input = new NBTTagCompound();
            fluidInputs.get(i).writeToNBT(input);
            fit.setTag(String.valueOf(i), input);
        }
        NBTTagCompound fot = new NBTTagCompound();
        fot.setInteger("count", foc);
        for(int i = 0; i < foc; i++) {
            NBTTagCompound input = new NBTTagCompound();
            fluidOutputs.get(i).writeToNBT(input);
            fot.setTag(String.valueOf(i), input);
        }
        tag.setString("recipeType", getRecipeType());
        tag.setTag("itemInputs", iit);
        tag.setTag("itemOutputs", iot);
        tag.setTag("fluidInputs", fit);
        tag.setTag("fluidOutputs", fot);
    }

    public void readFromNBT(NBTTagCompound tag) {
        NBTTagCompound iit = tag.getCompoundTag("itemInputs");
        int iic = iit.getInteger("count");
        if(iic > 0) {
            itemInputs = new ArrayList<>();
            for(int i = 0; i < iic; i++) {
                NBTTagCompound input = iit.getCompoundTag(String.valueOf(i));
                itemInputs.add(Util.readItemStackFromNBT(input));
            }
        }
        NBTTagCompound iot = tag.getCompoundTag("itemOutputs");
        int ioc = iot.getInteger("count");
        if(ioc > 0) {
            itemOutputs = new ArrayList<>();
            for(int i = 0; i < ioc; i++) {
                NBTTagCompound output = iot.getCompoundTag(String.valueOf(i));
                itemOutputs.add(Util.readItemStackFromNBT(output));
            }
        }
        NBTTagCompound fit = tag.getCompoundTag("fluidInputs");
        int fic = fit.getInteger("count");
        if(fic > 0) {
            fluidInputs = new ArrayList<>();
            for(int i = 0; i < fic; i++) {
                NBTTagCompound input = fit.getCompoundTag(String.valueOf(i));
                fluidInputs.add(FluidStack.loadFluidStackFromNBT(input));
            }
        }
        NBTTagCompound fot = tag.getCompoundTag("fluidOutputs");
        int foc = fot.getInteger("count");
        if(foc > 0) {
            fluidOutputs = new ArrayList<>();
            for(int i = 0; i < foc; i++) {
                NBTTagCompound output = fit.getCompoundTag(String.valueOf(i));
                fluidOutputs.add(FluidStack.loadFluidStackFromNBT(output));
            }
        }
    }

    private int getArrayListCount(ArrayList toCount) {
        return toCount == null ? 0 : toCount.size();
    }

    /**
     * @return The alias associated with a CustomRecipeHandler that can parse this recipe.
     *
     * @see common.xandayn.personalrecipes.recipe.CustomRecipeHandler
     */
    public final String getRecipeType() {
        return recipeType;
    }

    @Override
    public String toString() {
        return "[".concat(recipeType).concat("]");
    }
}
