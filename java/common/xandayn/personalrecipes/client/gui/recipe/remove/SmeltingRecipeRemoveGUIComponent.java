package common.xandayn.personalrecipes.client.gui.recipe.remove;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.SmeltingRecipeData;
import common.xandayn.personalrecipes.recipe.handler.SmeltingRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SmeltingRecipeRemoveGUIComponent extends RecipeGUIComponent {

    private GuiButton nextPage, lastPage;
    private GUISlot input;
    private GUISlot output;
    private GUITextField expCount;
    private int selected = 0;
    private int pageCount;
    private SmeltingRecipeHandler handler;

    public SmeltingRecipeRemoveGUIComponent(SmeltingRecipeHandler handler) {
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/smelting_recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
        this.handler = handler;
    }

    private void updateRecipe() {
        SmeltingRecipeData data = handler.getRecipes().get(selected);
        expCount.setText(Integer.toString((int)(data.smeltingEXP * 100)));
        input.setItem(data.itemInputs.get(0).copy());
        output.setItem(data.itemOutputs.get(0).copy());
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        nextPage.enabled = selected != (pageCount - 1);
        lastPage.enabled = selected != 0;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        selected = 0;
        components.add(input = new GUISlot(guiLeft + 25, guiTop + 16, 1, gui));
        components.add(output = new GUISlot(guiLeft + 85, guiTop + 34, 64, gui));
        components.add(expCount = new GUITextField(guiLeft + 50, guiTop + 36, 24, 3, null));
        buttonList.add(new GuiButton(0, guiLeft + 72, guiTop + 11, 42, 18, "Remove"));
        buttonList.add(new GuiButton(3, guiLeft + 72, guiTop + 55, 42, 18, "Back"));
        buttonList.add(lastPage = new GuiButton(1, guiLeft + 5, guiTop + 38, 10, 10, "<"));
        buttonList.add(nextPage = new GuiButton(2, guiLeft + 112, guiTop + 38, 10, 10, ">"));
        expCount.setEnabled(false);
        lastPage.enabled = nextPage.enabled = false;
        pageCount = handler.getRecipeCount();
        updateRecipe();
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                handler.deleteRecipe(selected);
                gui.returnToSelectScreen();
                break;
            case 1:
                selected--;
                if(selected < 0) {
                    selected = 0;
                }
                updateRecipe();
                break;
            case 2:
                selected++;
                if(selected > pageCount) {
                    selected = pageCount;
                }
                updateRecipe();
                break;
            case 3:
                gui.returnToSelectScreen();
                break;
        }
    }
}
