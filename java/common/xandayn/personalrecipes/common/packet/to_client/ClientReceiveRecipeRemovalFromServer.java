package common.xandayn.personalrecipes.common.packet.to_client;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ClientReceiveRecipeRemovalFromServer implements IMessage {

    private int handlerID = 0, position = 0;

    public ClientReceiveRecipeRemovalFromServer() {}

    public ClientReceiveRecipeRemovalFromServer(int handlerID, int position) {
        this.handlerID = handlerID;
        this.position = position;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        handlerID = byteBuf.readInt();
        position = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(handlerID);
        byteBuf.writeInt(position);
    }

    public static class Handler implements IMessageHandler<ClientReceiveRecipeRemovalFromServer, IMessage> {

        @Override
        public IMessage onMessage(ClientReceiveRecipeRemovalFromServer message, MessageContext ctx) {
            if(ctx.side.isClient()) {
                RecipeRegistry.INSTANCE.removeRecipe(message.handlerID, message.position);
            }
            return null;
        }
    }

}
