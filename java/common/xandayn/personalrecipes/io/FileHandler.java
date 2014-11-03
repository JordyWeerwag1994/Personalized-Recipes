package common.xandayn.personalrecipes.io;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * The FileHandler handles creation of folders and files, along with some
 * utility functions.
 *
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
public class FileHandler {
    private static File _WORKING_DIRECTORY;
    private static File _PLUGINS_DIRECTORY;
    private static File _DATA_DIRECTORY;
//    private static File _NBT_SAVE_FILE;

    public static void initialize(){
        _WORKING_DIRECTORY = new File(FMLCommonHandler.instance().getSide().isClient() ? Minecraft.getMinecraft().mcDataDir.getAbsoluteFile() : FMLCommonHandler.instance().getMinecraftServerInstance().getFile(""), "Personalized_Recipes");
        _PLUGINS_DIRECTORY = new File(_WORKING_DIRECTORY, "Plugins");
        _DATA_DIRECTORY = new File(_WORKING_DIRECTORY, "Data");
        createDirectory(_PLUGINS_DIRECTORY);
        createDirectory(_DATA_DIRECTORY);
    }

    public static void writeRecipesToNBT(String worldName){
        File nbtSaveDir = new File(_DATA_DIRECTORY, worldName);
        createDirectory(nbtSaveDir);
        File nbtSaveFile = new File(nbtSaveDir, "recipes.dat");
        try {
            if(!nbtSaveFile.exists()) {
                if(!nbtSaveFile.createNewFile()){
                    throw new RuntimeException("Unable to create recipes.dat inside of directory: \"" + _DATA_DIRECTORY.getAbsolutePath() + "\" recipes cannot be to saved.");
                }
            }

            NBTTagCompound tagCompound = new NBTTagCompound();
            FileOutputStream stream = new FileOutputStream(nbtSaveFile);
            RecipeRegistry.INSTANCE.writeAllRecipesToNBT(tagCompound);
            CompressedStreamTools.writeCompressed(tagCompound, stream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readRecipesFromNBT(String worldName) {
        File nbtSaveDir = new File(_DATA_DIRECTORY, worldName);
        if(!nbtSaveDir.exists()) {
            createDirectory(nbtSaveDir);
            return;
        }
        File nbtSaveFile = new File(nbtSaveDir, "recipes.dat");
        if(nbtSaveFile.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(nbtSaveFile);
                NBTTagCompound readTag = CompressedStreamTools.readCompressed(inputStream);
                RecipeRegistry.INSTANCE.readAllRecipesFromNBT(readTag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean createDirectory(File directory){
        return directory.exists() || (createDirectory(directory.getParentFile()) && directory.mkdir());
    }

    public static File getDataDirectory(){
        return _DATA_DIRECTORY;
    }

    public static File getPluginsDirectory(){
        return _PLUGINS_DIRECTORY;
    }

    public static File getBaseWorkingDirectory(){
        return _WORKING_DIRECTORY;
    }

    public static boolean isFileZipOrJar(File f){
        return f.getName().toLowerCase().endsWith(".zip") || f.getName().toLowerCase().endsWith(".jar");
    }
}
