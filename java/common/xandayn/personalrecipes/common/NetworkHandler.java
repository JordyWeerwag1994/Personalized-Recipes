package common.xandayn.personalrecipes.common;

import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveAllRecipesFromServer;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveNewRecipeFromServer;
import common.xandayn.personalrecipes.common.packet.to_client.ClientReceiveRecipeRemovalFromServer;
import common.xandayn.personalrecipes.common.packet.to_server.ServerReceiveNewRecipe;
import common.xandayn.personalrecipes.common.packet.to_server.ServerRemoveOldRecipe;
import common.xandayn.personalrecipes.util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class NetworkHandler {
    public static SimpleNetworkWrapper NETWORK;

    public static void initialize() {
        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_CHANNEL_ID);
        NETWORK.registerMessage(ServerReceiveNewRecipe.Handler.class, ServerReceiveNewRecipe.class, 0, Side.SERVER);
        NETWORK.registerMessage(ServerRemoveOldRecipe.Handler.class, ServerRemoveOldRecipe.class, 1, Side.SERVER);
        NETWORK.registerMessage(ClientReceiveAllRecipesFromServer.Handler.class, ClientReceiveAllRecipesFromServer.class, 2, Side.CLIENT);
        NETWORK.registerMessage(ClientReceiveNewRecipeFromServer.Handler.class, ClientReceiveNewRecipeFromServer.class, 3, Side.CLIENT);
        NETWORK.registerMessage(ClientReceiveRecipeRemovalFromServer.Handler.class, ClientReceiveRecipeRemovalFromServer.class, 4, Side.CLIENT);
    }
}
