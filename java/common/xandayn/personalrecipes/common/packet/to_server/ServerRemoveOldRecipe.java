package common.xandayn.personalrecipes.common.packet.to_server;

import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveRecipeRemovalFromServer;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ServerRemoveOldRecipe implements IMessage {

    private int handlerID = 0;
    private int recipeID = 0;

    public ServerRemoveOldRecipe() {}

    public ServerRemoveOldRecipe(int handlerID, int recipeID){
        this.handlerID = handlerID;
        this.recipeID = recipeID;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        handlerID = byteBuf.readInt();
        recipeID = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(handlerID);
        byteBuf.writeInt(recipeID);
    }

    public static class Handler implements IMessageHandler<ServerRemoveOldRecipe, IMessage> {

        @Override
        public IMessage onMessage(ServerRemoveOldRecipe message, MessageContext ctx) {
            if(ctx.side.isServer()) {
                RecipeRegistry.INSTANCE.removeRecipe(message.handlerID, message.recipeID);
                NetworkHandler.NETWORK.sendToAll(new ClientReceiveRecipeRemovalFromServer(message.handlerID, message.recipeID));
            }
            return null;
        }
    }
}
