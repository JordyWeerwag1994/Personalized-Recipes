package common.xandayn.personalrecipes.client.gui;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.common.container.RecipeCreatorContainer;
import common.xandayn.personalrecipes.common.network.NetworkHandler;
import common.xandayn.personalrecipes.common.network.packet.server.ServerReceiveRecipePacket;
import common.xandayn.personalrecipes.common.network.packet.server.ServerRemoveRecipePacket;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;


/**
 * PersonalizedRecipes - RecipeCreatorGui
 *
 * A class for creating and removing recipes in a GUI.
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
public class RecipeCreatorGui extends GuiContainer{

    private ResourceLocation background;
    private EntityPlayer player;
    private RecipeCreatorContainer container;
    private ArrayList<Integer> disabledSlots = new ArrayList<>();
    private boolean removeMode;

    //Remove mode variables
    private int recipePos = 0;
    private GuiButton previousPage;
    private GuiButton nextPage;
    private ArrayList<Integer> positionData;
    private ArrayList<IRecipe> recipeData;
    private boolean closeScreen = false;

    public RecipeCreatorGui(EntityPlayer playerInv, RecipeCreatorContainer container, boolean removeMode) {
        super(container);
        this.player = playerInv;
        this.container = container;
        this.removeMode = removeMode;
        background = container.getRecipeComponent().getGuiBackground();
        xSize = container.getRecipeComponent().getGuiXSize();
        ySize = container.getRecipeComponent().getGuiYSize();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(nextPage != null && previousPage != null) {
            nextPage.enabled = recipePos != recipeData.size() - 1;
            previousPage.enabled = recipePos != 0;
        }
        if(closeScreen){
            player.closeScreen();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        GuiButton button = container.getRecipeComponent().getConfirmationButton(guiLeft, guiTop);
        if(removeMode){
            positionData = RecipeRegistry.getListOfType(container.getRecipeComponent().getCraftingAlias(), true);
            recipeData = RecipeRegistry.getListOfType(container.getRecipeComponent().getCraftingAlias(), false);
            if(recipeData.size() > 0){
                updateRecipe();
            }else{
                player.addChatMessage(new ChatComponentText("There are no registered " + container.getRecipeComponent().getCraftingAlias() + " recipes!"));
                closeScreen = true;
                return;
            }
            button.displayString = "Remove";
            buttonList.add(button);
            int height =  guiTop + ySize / 2 - 9;previousPage = new GuiButton(1, guiLeft - 18, height, 18, 18, "<");
            nextPage = new GuiButton(2, guiLeft + xSize, height, 18, 18, ">");
            previousPage.enabled = false;
            buttonList.add(previousPage);
            buttonList.add(nextPage);
        }else{
            buttonList.add(button);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);

        if(!removeMode) {
            int slot = getSelectedSlotShifted(par1 - guiLeft, par2 - guiTop);
            if (slot != -1) {

                ItemStack playerStack = Minecraft.getMinecraft().thePlayer.inventory.getItemStack();

                ItemStack toUse = null;

                if (playerStack != null) {
                    if (container.getRecipeComponent().allowSlotDisabling() && disabledSlots.contains(slot - 600))
                        return;
                    if (!container.getRecipeComponent().isSlotOutput(slot - 600)) {
                        toUse = playerStack.copy();
                        toUse.stackSize = 1;
                    } else {
                        toUse = playerStack.copy();
                    }
                } else if (container.getRecipeComponent().allowSlotDisabling() && !container.getRecipeComponent().isSlotOutput(slot - 600) && container.getStackInSlot(slot - 600) == null) {
                    if (disabledSlots.contains(slot - 600))
                        disabledSlots.remove(new Integer(slot - 600));
                    else
                        disabledSlots.add(slot - 600);
                }
                container.setInventorySlotContents(slot, toUse);
            }
        }
    }

    private int getSelectedSlotShifted(int xPos, int yPos){
        Slot[] s = container.getRecipeComponent().getCraftingInventorySlots();
        for(int i = 0; i < s.length; i++){
            if(xPos >= s[i].xDisplayPosition && xPos < s[i].xDisplayPosition + 18 && yPos >= s[i].yDisplayPosition && yPos < s[i].yDisplayPosition + 18)
                return i + 600;
        }

        return -1;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(!removeMode) {
            if (button.id == 0) {
                //We've confirmed the selection as a recipe.
                ItemStack[] recipeData = container.getInventoryArray();
                boolean noOutput = false;
                for (int i = 0; i < recipeData.length; i++) {
                    if (container.getRecipeComponent().isSlotOutput(i) && recipeData[i] == null) {
                        noOutput = true;
                        break;
                    }
                }
                if (!noOutput) {
                    IRecipe recipe = container.getRecipeComponent().compileRecipe(recipeData, disabledSlots);
                    RecipeRegistry.registerRecipe(container.getRecipeComponent().getCraftingAlias(), recipe);
                    if (PersonalizedRecipes.CURRENT_SIDE.isServer())
                        NetworkHandler.NETWORK.sendToServer(new ServerReceiveRecipePacket(recipe, container.getRecipeComponent().getCraftingAlias(), Minecraft.getMinecraft().thePlayer.getDisplayName()));
                    player.closeScreen();
                } else {
                    player.addChatMessage(new ChatComponentText("No output item(s) selected."));
                }
            }
        }else{
            switch (button.id){
                case 0: //Remove button
                    if (PersonalizedRecipes.CURRENT_SIDE.isServer()){
                        NetworkHandler.NETWORK.sendToServer(new ServerRemoveRecipePacket(positionData.get(recipePos)));
                    } else {
                        RecipeRegistry.removeRegisteredRecipe(positionData.get(recipePos));
                    }
                    player.closeScreen();
                    break;
                case 1: //Previous Page
                    recipePos--;
                    updateRecipe();
                    break;
                case 2: //Next Page
                    recipePos++;
                    updateRecipe();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(container.getRecipeComponent().getInventoryName(), 5, 5, 0x404040);
        if (container.getRecipeComponent().allowSlotDisabling()) {
            Slot[] s = container.getRecipeComponent().getCraftingInventorySlots();
            for (int i = 0; i < disabledSlots.size(); i++) {
                drawRect(s[disabledSlots.get(i)].xDisplayPosition, s[disabledSlots.get(i)].yDisplayPosition, s[disabledSlots.get(i)].xDisplayPosition + 17, s[disabledSlots.get(i)].yDisplayPosition + 17, 0x5D191919);
            }
        }
    }

    private void updateRecipe(){
        container.getRecipeComponent().addRecipeToCraftingWindow(recipeData.get(recipePos), container.recipeData);
    }

}
