package common.xandayn.personalrecipes;

import common.xandayn.personalrecipes.command.RecipeCommand;
import common.xandayn.personalrecipes.common.GuiHandler;
import common.xandayn.personalrecipes.lib.References;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * The class that defines the Personalized Recipes mod to Forge.
 */
@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION, dependencies = References.MOD_DEPENDENCIES)
public class PersonalizedRecipes {

    /**
     * The instance of PersonalizedRecipes created by Forge.
     */
    @Instance(References.MOD_ID)
    public static PersonalizedRecipes INSTANCE;

    @EventHandler
    public void serverStart(FMLServerStartingEvent event){
        event.registerServerCommand(new RecipeCommand());
    }

    @EventHandler
    public void preInitialization(FMLPreInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

}
