package common.xandayn.personalrecipes.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Util {

    public static ArrayList<ItemStack> getAllItemsAndBlocks(){
        ArrayList<ItemStack> allItems = new ArrayList<>();
        for(Object o : Item.itemRegistry){
            Item i = (Item)o;
            i.getSubItems(i, i.getCreativeTab(), allItems);
        }
        return allItems;
    }

}
