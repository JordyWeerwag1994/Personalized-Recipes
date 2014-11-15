package common.xandayn.personalrecipes.common.packet.to_client;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class ClientReceiveAllRecipesFromServer implements IMessage {

    private NBTTagCompound recipeData;

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        try {
            recipeData = new PacketBuffer(byteBuf).readNBTTagCompoundFromBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        try {
            recipeData = new NBTTagCompound();
            RecipeRegistry.INSTANCE.writeAllRecipesToNBT(recipeData);
            new PacketBuffer(byteBuf).writeNBTTagCompoundToBuffer(recipeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<ClientReceiveAllRecipesFromServer, IMessage> {

        @Override
        public IMessage onMessage(ClientReceiveAllRecipesFromServer message, MessageContext ctx) {
            if(ctx.side.isClient()) {
                RecipeRegistry.INSTANCE.readAllRecipesFromNBT(message.recipeData);
            }
            return null;
        }
    }

}
