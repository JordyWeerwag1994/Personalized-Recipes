package common.xandayn.personalrecipes.common.packet.to_server;

import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveNewRecipeFromServer;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import common.xandayn.personalrecipes.recipe.data.RecipeData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class ServerReceiveNewRecipe implements IMessage{

    private NBTTagCompound data;

    public ServerReceiveNewRecipe(){}

    public ServerReceiveNewRecipe(NBTTagCompound data){
        this.data = data;
    }


    @Override
    public void fromBytes(ByteBuf byteBuf) {
        try {
            data = new PacketBuffer(byteBuf).readNBTTagCompoundFromBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        try {
            new PacketBuffer(byteBuf).writeNBTTagCompoundToBuffer(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<ServerReceiveNewRecipe, IMessage> {

        @Override
        public IMessage onMessage(ServerReceiveNewRecipe message, MessageContext ctx) {
            if(ctx.side.isServer()) {
                RecipeRegistry.INSTANCE.registerRecipeFromNBT(message.data);
                NetworkHandler.NETWORK.sendToAll(new ClientReceiveNewRecipeFromServer(message.data));
            }
            return null;
        }
    }

}
