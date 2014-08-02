package common.xandayn.personalrecipes.common.network.packet.client;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * PersonalizedRecipes - ClientRemoveRecipeFromServer
 *
 * A packet class that is used to send commands to remove recipes from the client, sent from server to client.
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
public class ClientRemoveRecipeFromServer implements IMessage {

    private int position = 0;
    private boolean removeAll = false;

    public ClientRemoveRecipeFromServer(){}

    public ClientRemoveRecipeFromServer(int position){
        this.position = position;
    }

    public ClientRemoveRecipeFromServer(boolean removeAll){
        this.removeAll = removeAll;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.setInt(0, position);
        buf.setBoolean(1, removeAll);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        position = buf.getInt(0);
        removeAll = buf.getBoolean(1);
    }

    public static class Handler implements IMessageHandler<ClientRemoveRecipeFromServer, IMessage>{

        @Override
        public IMessage onMessage(ClientRemoveRecipeFromServer message, MessageContext ctx) {
            if(ctx.side.isClient()){
                if(!message.removeAll)
                    RecipeRegistry.removeRegisteredRecipe(message.position);
                else {
                    System.out.println("Test");
                    RecipeRegistry.removeAllRecipes();
                }
            }
            return null;
        }
    }

}
