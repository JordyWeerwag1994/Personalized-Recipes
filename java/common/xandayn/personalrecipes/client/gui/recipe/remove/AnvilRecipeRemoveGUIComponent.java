package common.xandayn.personalrecipes.client.gui.recipe.remove;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_server.ServerRemoveOldRecipe;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import common.xandayn.personalrecipes.recipe.data.AnvilRecipeData;
import common.xandayn.personalrecipes.recipe.handler.AnvilRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AnvilRecipeRemoveGUIComponent extends RecipeGUIComponent {

    AnvilRecipeHandler handler;
    private GUITextField recipeCost;
    private GUISlot input1, input2, output;
    private GuiButton nextPage, lastPage;
    private int selected;
    private int pageCount;

    public AnvilRecipeRemoveGUIComponent(AnvilRecipeHandler handler) {
        this.handler = handler;
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/anvil_component.png");
        this.xSize = 128;
        this.ySize = 86;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        components.add(recipeCost = new GUITextField(guiLeft + 98, guiTop + 65, 18, 2, "1"));
        components.add(input1 = new GUIItemListDialogSlot(guiLeft + 13, guiTop + 35, guiLeft, guiTop, 64, gui));
        components.add(input2 = new GUIItemListDialogSlot(guiLeft + 49, guiTop + 35, guiLeft, guiTop, 64, gui));
        components.add(output = new GUIItemListDialogSlot(guiLeft + 99, guiTop + 35, guiLeft, guiTop, 64, gui));
        recipeCost.setAllowed(GUITextField.NUMERIC_ONLY);
        buttonList.add(new GuiButton(0, guiLeft + 12, guiTop + 7, 36, 20, "Delete"));
        buttonList.add(new GuiButton(1, guiLeft + 80, guiTop + 7, 36, 20, "Back"));
        buttonList.add(lastPage = new GuiButton(2, guiLeft - 10, guiTop + 38, 10, 10, "<"));
        buttonList.add(nextPage = new GuiButton(3, guiLeft + xSize, guiTop + 38, 10, 10, ">"));
        recipeCost.setEnabled(false);
        selected = 0;
        pageCount = handler.getRecipeCount();
        updateRecipe();
    }

    private void updateRecipe() {
        AnvilRecipeData data = handler.getRecipes().get(selected);
        input1.setItem(data.itemInputs.get(0));
        input2.setItem(data.itemInputs.get(1));
        output.setItem(data.itemOutputs.get(0));
        recipeCost.setText(String.valueOf(data.recipeCost));
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        nextPage.enabled = selected != (pageCount - 1);
        lastPage.enabled = selected != 0;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                if(FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
                    NetworkHandler.NETWORK.sendToServer(new ServerRemoveOldRecipe(RecipeRegistry.INSTANCE.getAliasIntID(handler.getID()), selected));
                } else {
                    handler.deleteRecipe(selected);
                }
                gui.returnToSelectScreen();
                break;
            case 1:
                gui.returnToSelectScreen();
                break;
            case 2:
                selected--;
                if(selected < 0) {
                    selected = 0;
                }
                updateRecipe();
                break;
            case 3:
                selected++;
                if(selected > pageCount) {
                    selected = pageCount;
                }
                updateRecipe();
                break;
        }
    }
}
