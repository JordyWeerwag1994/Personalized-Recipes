package common.xandayn.personalrecipes.io;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.*;

/**
 * PersonalizedRecipes - NBTHandler
 *
 * A class used to save all Personalized Recipes' recipes to an NBT file.
 *
 * @license
 *   Copyright (C) 2014  xandayn
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author xandayn
 */
public class NBTHandler {

    public static void readRecipesFromNBT(){
        File nbtFile = new File(PersonalizedRecipes.WORKING_DIRECTORY.concat(File.separator).concat("recipes.dat"));
        if(!nbtFile.exists()) return;

        try {
            FileInputStream inputStream = new FileInputStream(nbtFile);
            NBTTagCompound readTag = CompressedStreamTools.readCompressed(inputStream);
            RecipeRegistry.readRecipesFromNBT(readTag);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeRecipesToNBT(){
        File nbtFilePath = new File(PersonalizedRecipes.WORKING_DIRECTORY.concat(File.separator));
        File nbtFile = new File(nbtFilePath.getAbsolutePath().concat(File.separator).concat("recipes.dat"));
        try {
            if(!nbtFile.exists()){
                if(!nbtFilePath.exists()) {
                    System.out.println(nbtFilePath.getAbsolutePath());
                    if (!nbtFilePath.mkdir()) {
                        return;
                    }
                }
                if(!nbtFile.createNewFile()) {
                    return;
                }
            }
            NBTTagCompound tagCompound = new NBTTagCompound();
            FileOutputStream stream = new FileOutputStream(nbtFile);
            RecipeRegistry.writeRecipesToNBT(tagCompound);
            CompressedStreamTools.writeCompressed(tagCompound, stream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
