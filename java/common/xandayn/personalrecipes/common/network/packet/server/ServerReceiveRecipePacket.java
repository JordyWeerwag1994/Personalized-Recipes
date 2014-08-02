package common.xandayn.personalrecipes.common.network.packet.server;

import common.xandayn.personalrecipes.common.network.NetworkHandler;
import common.xandayn.personalrecipes.common.network.packet.client.ClientReceiveNewRecipeFromServer;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

/**
 * PersonalizedRecipes - ServerReceiveRecipePacket
 *
 * A packet class that is used to receive new recipes from clients, on the server side.
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
public class ServerReceiveRecipePacket implements IMessage {

    private String originPlayerName;
    private IRecipe recipe;
    private String type;

    public ServerReceiveRecipePacket(){}

    public ServerReceiveRecipePacket(IRecipe recipe, String type, String origin){
        originPlayerName = origin;
        this.recipe = recipe;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            NBTTagCompound tag = new PacketBuffer(buf).readNBTTagCompoundFromBuffer();
            if(tag != null){
                originPlayerName = tag.getString("originPlayerName");
                type = tag.getString("type");
                recipe = RecipeRegistry.readRecipeFromNBT(type, tag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(recipe != null && type != null){
            try {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("originPlayerName", originPlayerName);
                tag.setString("type", type);
                RecipeRegistry.writeRecipeToNBT(tag, recipe, type);
                new PacketBuffer(buf).writeNBTTagCompoundToBuffer(tag);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class Handler implements IMessageHandler<ServerReceiveRecipePacket, IMessage> {

        @Override
        public IMessage onMessage(ServerReceiveRecipePacket message, MessageContext ctx) {
            if(ctx.side == Side.SERVER){
                RecipeRegistry.registerRecipe(message.type, message.recipe);
                NetworkHandler.NETWORK.sendToAll(new ClientReceiveNewRecipeFromServer(message.originPlayerName, message.recipe, message.type));
            }
            return null;
        }
    }

}
