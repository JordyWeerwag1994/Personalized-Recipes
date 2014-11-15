package common.xandayn.personalrecipes.event;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveAllRecipesFromServer;
import common.xandayn.personalrecipes.io.FileHandler;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;

public class EventHandler {

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event) {
        PersonalizedRecipes.ANVIL_RECIPE.checkRecipeFromAnvil(event);
    }

    @SubscribeEvent
    public void onWorldSaveEvent(WorldEvent.Save event) {
        if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            if (event.world.provider.dimensionId == 0) {
                String worldName = event.world.getSaveHandler().getWorldDirectoryName();
                if(!event.world.isRemote)
                    FileHandler.writeRecipesToNBT(worldName);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void onPlayerJoinServerEvent(PlayerEvent.PlayerLoggedInEvent event) {
        NetworkHandler.NETWORK.sendTo(new ClientReceiveAllRecipesFromServer(), (EntityPlayerMP)event.player);
    }

    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event) {
        if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            if (event.world.provider.dimensionId == 0) {
                String worldName = event.world.getSaveHandler().getWorldDirectoryName();
                if(!event.world.isRemote) {
                    FileHandler.readRecipesFromNBT(worldName);
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnloadEvent(WorldEvent.Unload event) {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            if(Minecraft.getMinecraft().running) {
                System.out.println("CLEAR");
                RecipeRegistry.INSTANCE.clearRecipes();
            }
        }
    }
}
