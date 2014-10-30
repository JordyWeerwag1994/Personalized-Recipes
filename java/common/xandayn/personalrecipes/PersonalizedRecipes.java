package common.xandayn.personalrecipes;

import common.xandayn.personalrecipes.lib.References;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION, dependencies = References.MOD_DEPENDENCIES)
public class PersonalizedRecipes {

    @Instance(References.MOD_ID)
    public static PersonalizedRecipes INSTANCE;

    @EventHandler
    public void initialize(FMLPreInitializationEvent event){

    }

}
