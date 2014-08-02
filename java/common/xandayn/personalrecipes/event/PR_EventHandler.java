package common.xandayn.personalrecipes.event;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.common.network.NetworkHandler;
import common.xandayn.personalrecipes.common.network.packet.client.ClientRemoveRecipeFromServer;
import common.xandayn.personalrecipes.common.network.packet.client.ClientRequestAllRecipesFromServer;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * PersonalizedRecipes - PR_EventHandler
 *
 * A class that handles the Forge Events needed by the Personalized Recipes mod.
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
public class PR_EventHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        if(FMLCommonHandler.instance().getSide().isServer()) {
            if(event.player instanceof EntityPlayerMP) {
                NetworkHandler.NETWORK.sendTo(new ClientRequestAllRecipesFromServer(), (EntityPlayerMP) event.player);
            }
        }else {
            PersonalizedRecipes.CURRENT_SIDE = Side.CLIENT;
            RecipeRegistry.removeAllRecipes();
            RecipeRegistry.reloadSinglePlayerRecipes();
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event){
        System.out.println("Test " + FMLCommonHandler.instance().getSide());
        NetworkHandler.NETWORK.sendTo(new ClientRemoveRecipeFromServer(true), (EntityPlayerMP)event.player);
        if(FMLCommonHandler.instance().getSide().isClient()) {
            PersonalizedRecipes.CURRENT_SIDE = Side.CLIENT;
            RecipeRegistry.removeAllRecipes();
        }
    }

}
