package common.xandayn.personalrecipes.client.gui.recipe.remove;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.handler.ShapelessRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;

public class ShapelessRecipeRemoveGUIComponent extends RecipeGUIComponent{

    private GuiButton nextPage, lastPage;
    private GUISlot[] inputs;
    private GUISlot output;
    private int selected = 0;
    private int pageCount;
    private ShapelessRecipeHandler handler;

    public ShapelessRecipeRemoveGUIComponent(ShapelessRecipeHandler handler){
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/recipe_component.png");
        this.xSize = 128;
        this.ySize = 86;
        this.handler = handler;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        super.initGUI(gui, player);
        selected = 0;
        this.inputs = new GUISlot[9];
        for(int i = 0; i < inputs.length; i++) {
            int x = i % 3;
            int y = i / 3;
            int bufferX = x * 2;
            int bufferY = y * 2;
            components.add(inputs[i] = new GUISlot(20 + (16 * x) + bufferX + guiLeft, 17 + (16 * y) + bufferY + guiTop, 1, gui));
        }
        components.add(output = new GUISlot(92 + guiLeft, 35 + guiTop, 64, gui));
        buttonList.add(new GuiButton(0, guiLeft + 81, guiTop + 16, 32, 16, "Remove"));
        buttonList.add(lastPage = new GuiButton(1, guiLeft + 5, guiTop + 38, 10, 10, "<"));
        buttonList.add(nextPage = new GuiButton(2, guiLeft + 112, guiTop + 38, 10, 10, ">"));
        buttonList.add(new GuiButton(3, guiLeft + 81, guiTop + 54, 32, 16, "Back"));
        lastPage.enabled = nextPage.enabled = false;
        pageCount = handler.getRecipeCount();
        updateRecipe();
    }

    private void updateRecipe() {
        ShapelessRecipes data = handler.getRecipes().get(selected);
        for(int i = 0; i < data.recipeItems.size(); i++) {
            inputs[i].setItem(null);
            inputs[i].setItem(data.recipeItems.get(i) == null ? null : ((ItemStack)data.recipeItems.get(i)).copy());
        }
        output.setItem(data.getRecipeOutput().copy());
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
