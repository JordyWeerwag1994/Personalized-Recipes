package common.xandayn.personalrecipes.common.packet.to_client;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class ClientReceiveNewRecipeFromServer implements IMessage{

    private NBTTagCompound data;

    public ClientReceiveNewRecipeFromServer() {}

    public ClientReceiveNewRecipeFromServer(NBTTagCompound data) {
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

    public static class Handler implements IMessageHandler<ClientReceiveNewRecipeFromServer, IMessage> {

        @Override
        public IMessage onMessage(ClientReceiveNewRecipeFromServer message, MessageContext ctx) {
            if(ctx.side.isClient()) {
                RecipeRegistry.INSTANCE.registerRecipeFromNBT(message.data);
            }
            return null;
        }
    }

}
