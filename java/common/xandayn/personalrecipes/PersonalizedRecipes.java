package common.xandayn.personalrecipes;

import common.xandayn.personalrecipes.command.RecipeCommand;
import common.xandayn.personalrecipes.common.GuiHandler;
import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.event.EventHandler;
import common.xandayn.personalrecipes.io.FileHandler;
import common.xandayn.personalrecipes.plugin.loading.PluginLoader;
import common.xandayn.personalrecipes.recipe.handler.AnvilRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;

/**
 * The class that defines the Personalized Recipes mod to Forge.
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
@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION, dependencies = References.MOD_DEPENDENCIES)
public class PersonalizedRecipes {

    public static AnvilRecipeHandler ANVIL_RECIPE = new AnvilRecipeHandler();

    /**
     * The instance of PersonalizedRecipes created by Forge.
     */
    @Instance(References.MOD_ID)
    public static PersonalizedRecipes INSTANCE;

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event){
        event.registerServerCommand(new RecipeCommand());
    }

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent event){
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        NetworkHandler.initialize();
        FileHandler.initialize();
        PluginLoader.loadPlugins();
    }

}
