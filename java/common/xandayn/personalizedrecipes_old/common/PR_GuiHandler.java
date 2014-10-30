package common.xandayn.personalizedrecipes_old.common;

import common.xandayn.personalizedrecipes_old.client.gui.RecipeCreatorGui;
import common.xandayn.personalizedrecipes_old.common.container.RecipeCreatorContainer;
import common.xandayn.personalizedrecipes_old.recipe.RecipeRegistry;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * PersonalizedRecipes - PR_GuiHandler
 *
 * A class that handles all GUIs for the PersonalizedRecipes mod.
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
public class PR_GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new RecipeCreatorContainer(player.inventory, RecipeRegistry.getComponentFromAlias(ID));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new RecipeCreatorGui(player, new RecipeCreatorContainer(player.inventory, RecipeRegistry.getComponentFromAlias(ID)), x == 1);
    }
}
