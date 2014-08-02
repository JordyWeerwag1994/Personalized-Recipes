package common.xandayn.personalrecipes.common.network.packet.client;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

/**
 * PersonalizedRecipes - ClientRequestAllRecipesFromServer
 *
 * A packet class that is used to send all the registered recipes from the server to the client.
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
public class ClientRequestAllRecipesFromServer implements IMessage {
    NBTTagCompound compound;
    @Override
    public void fromBytes(ByteBuf buf) {
        try{
            compound = new PacketBuffer(buf).readNBTTagCompoundFromBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try{
            NBTTagCompound compound = new NBTTagCompound();
            RecipeRegistry.writeRecipesToNBT(compound);
            new PacketBuffer(buf).writeNBTTagCompoundToBuffer(compound);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<ClientRequestAllRecipesFromServer, IMessage> {

        @Override
        public IMessage onMessage(ClientRequestAllRecipesFromServer message, MessageContext ctx) {
            if(ctx.side == Side.CLIENT) {
                PersonalizedRecipes.CURRENT_SIDE = Side.SERVER;
                RecipeRegistry.removeAllRecipes();
                RecipeRegistry.readRecipesFromNBT(message.compound);
            }
            return null;
        }
    }
}
