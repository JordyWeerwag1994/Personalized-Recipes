package common.xandayn.personalrecipes.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
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
public class Util {

    private static ArrayList<ItemStack> items;
    private static boolean initialized = false;

    public static ArrayList<ItemStack> getAllItemsAndBlocks(){
        if(!initialized) {
            initialized = true;
            items = new ArrayList<>();
            for (Object o : Item.itemRegistry) {
                Item i = (Item) o;
                if(FMLCommonHandler.instance().getSide().isClient())
                    i.getSubItems(i, i.getCreativeTab(), items);
                else
                    items.add(new ItemStack(i));
            }
        }
        return items;
    }

    public static Item getItemByUnlocalizedName(String unlocalizedName){
        ArrayList<ItemStack> items = getAllItemsAndBlocks();
        for(ItemStack item : items) {
            if(item.getItem().getUnlocalizedName().equals(unlocalizedName))
                return item.getItem();
        }
        return null;
    }

    public static void writeItemStackToNBT(NBTTagCompound tag, ItemStack item) {
        if(item != null) {
            tag.setString("name", item.getItem().getUnlocalizedName());
            tag.setInteger("damage", item.getItemDamage());
            tag.setInteger("count", item.stackSize);
            if(item.hasTagCompound())
                tag.setTag("data", item.getTagCompound());
        } else {
            tag.setString("name", "null");
        }
    }

    public static ItemStack readItemStackFromNBT(NBTTagCompound tag){
        String name = tag.getString("name");
        if(!name.equals("null")) {
            int damage = tag.getInteger("damage");
            int count = tag.getInteger("count");
            NBTTagCompound data = tag.hasKey("data") ? tag.getCompoundTag("data") : null;
            ItemStack toReturn = new ItemStack(getItemByUnlocalizedName(name), count, damage);
            toReturn.setTagCompound(data);
            return toReturn;
        }
        return null;
    }
}
