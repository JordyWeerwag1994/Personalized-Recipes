package common.xandayn.personalrecipes.common.network.packet.server;

import common.xandayn.personalrecipes.common.network.NetworkHandler;
import common.xandayn.personalrecipes.common.network.packet.client.ClientRemoveRecipeFromServer;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * PersonalizedRecipes - ServerRemoveRecipePacket
 *
 * A packet class that is used to remove a selected recipe from the server, sent from client, to server.
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
public class ServerRemoveRecipePacket implements IMessage{

    private int position;

    public ServerRemoveRecipePacket(){}

    public ServerRemoveRecipePacket(int position){
        this.position = position;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.setInt(0, position);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.position = buf.getInt(0);
    }

    public static class Handler implements IMessageHandler<ServerRemoveRecipePacket, IMessage> {

        @Override
        public IMessage onMessage(ServerRemoveRecipePacket message, MessageContext ctx) {
            if(ctx.side.isServer()){
                RecipeRegistry.removeRegisteredRecipe(message.position);
                NetworkHandler.NETWORK.sendToAll(new ClientRemoveRecipeFromServer(message.position));
            }
            return null;
        }
    }

}
