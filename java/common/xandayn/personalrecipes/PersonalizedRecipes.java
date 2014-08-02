package common.xandayn.personalrecipes;

import common.xandayn.personalrecipes.commads.RecipeCommand;
import common.xandayn.personalrecipes.common.PR_GuiHandler;
import common.xandayn.personalrecipes.common.network.NetworkHandler;
import common.xandayn.personalrecipes.event.PR_EventHandler;
import common.xandayn.personalrecipes.io.Config;
import common.xandayn.personalrecipes.io.NBTHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.CraftingManager;

import java.io.File;
import java.util.List;

/**
 * PersonalizedRecipes - PersonalizedRecipes
 *
 * The main class for the PersonalizedRecipes mod.
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
@Mod(modid = PersonalizedRecipes.MOD_ID, name = PersonalizedRecipes.MOD_NAME, version = PersonalizedRecipes.MOD_VERSION, dependencies = PersonalizedRecipes.MOD_DEPENDENCIES)
public class PersonalizedRecipes {

    public static final String MOD_ID = "xan_personalizedRecipes";
    public static final String CHANNEL_ID = "xan_prChannel";
    public static final String MOD_NAME = "Personalized Recipes";
    public static final String MOD_VERSION = "1.7.10-0.2.133";
    public static final String MOD_DEPENDENCIES = "after:*";
    public static boolean ALLOW_NON_OP_COMMANDS;

    public static String WORKING_DIRECTORY;

    public static Side CURRENT_SIDE = Side.CLIENT;

    @Instance(PersonalizedRecipes.MOD_ID)
    public static PersonalizedRecipes INSTANCE;

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event){
        event.registerServerCommand(new RecipeCommand());
    }

    @EventHandler
    public void serverSave(FMLServerStoppingEvent event){
        NBTHandler.writeRecipesToNBT();
    }

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent event){
        FMLCommonHandler.instance().bus().register(new PR_EventHandler());
        Config.initialize(event.getSuggestedConfigurationFile());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new PR_GuiHandler());
        WORKING_DIRECTORY = FMLCommonHandler.instance().getSide().isClient() ? Minecraft.getMinecraft().mcDataDir.getAbsolutePath().concat(File.separator).concat(MOD_NAME).concat(File.separator) : FMLCommonHandler.instance().getMinecraftServerInstance().getFile("").getAbsolutePath().concat(File.separator).concat(MOD_NAME).concat(File.separator);

    }

    @EventHandler
    public void initialize(FMLInitializationEvent event){
        NetworkHandler.initialize();
        NBTHandler.readRecipesFromNBT();
    }

    public List getCraftingRecipeList(){
        return CraftingManager.getInstance().getRecipeList();
    }

}
