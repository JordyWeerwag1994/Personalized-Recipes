package common.xandayn.personalizedrecipes_old.common.network;

import common.xandayn.personalizedrecipes_old.PersonalizedRecipes;
import common.xandayn.personalizedrecipes_old.common.network.packet.client.ClientReceiveNewRecipeFromServer;
import common.xandayn.personalizedrecipes_old.common.network.packet.client.ClientRemoveRecipeFromServer;
import common.xandayn.personalizedrecipes_old.common.network.packet.client.ClientRequestAllRecipesFromServer;
import common.xandayn.personalizedrecipes_old.common.network.packet.server.ServerReceiveRecipePacket;
import common.xandayn.personalizedrecipes_old.common.network.packet.server.ServerRemoveRecipePacket;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * PersonalizedRecipes - NetworkHandler
 *
 * A class that helps register all packets to both client and server.
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
public class NetworkHandler {

    public static SimpleNetworkWrapper NETWORK;

    public static void initialize(){
        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(PersonalizedRecipes.CHANNEL_ID);
        NETWORK.registerMessage(ServerReceiveRecipePacket.Handler.class, ServerReceiveRecipePacket.class, 0, Side.SERVER);
        NETWORK.registerMessage(ClientRequestAllRecipesFromServer.Handler.class, ClientRequestAllRecipesFromServer.class, 1, Side.CLIENT);
        NETWORK.registerMessage(ClientReceiveNewRecipeFromServer.Handler.class, ClientReceiveNewRecipeFromServer.class, 2, Side.CLIENT);
        NETWORK.registerMessage(ServerRemoveRecipePacket.Handler.class, ServerRemoveRecipePacket.class, 3, Side.SERVER);
        NETWORK.registerMessage(ClientRemoveRecipeFromServer.Handler.class, ClientRemoveRecipeFromServer.class, 4, Side.CLIENT);
    }

}
