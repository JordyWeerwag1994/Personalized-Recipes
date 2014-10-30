package common.xandayn.personalrecipes.common;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new RecipeHandlerGUI.RH_Container();
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new RecipeHandlerGUI(player);
    }
}
