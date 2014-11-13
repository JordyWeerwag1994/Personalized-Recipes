package common.xandayn.personalrecipes.event;

import common.xandayn.personalrecipes.io.FileHandler;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerCraftItemEvent(PlayerEvent.ItemCraftedEvent event){
        System.out.println(event.crafting);
    }

    @SubscribeEvent
    public void onWorldSaveEvent(WorldEvent.Save event) {
        if(!event.world.isRemote && event.world.provider.dimensionId == 0) {
            String worldName = event.world.getSaveHandler().getWorldDirectoryName();
            FileHandler.writeRecipesToNBT(worldName);
        }
    }

    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event){
        String worldName = event.world.getSaveHandler().getWorldDirectoryName();
        if(!event.world.isRemote && event.world.provider.dimensionId == 0) {
            FileHandler.readRecipesFromNBT(worldName);
        }
    }

    @SubscribeEvent
    public void onWorldUnloadEvent(WorldEvent.Unload event){
        if(!event.world.isRemote && event.world.provider.dimensionId == 0) {
            if(Minecraft.getMinecraft().running) {
                RecipeRegistry.INSTANCE.clearRecipes();
            }
        }
    }

}
