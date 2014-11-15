package common.xandayn.personalrecipes.client.gui.recipe.remove;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.common.NetworkHandler;
import common.xandayn.personalrecipes.common.packet.to_server.ServerRemoveOldRecipe;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import common.xandayn.personalrecipes.recipe.handler.ShapedRecipeHandler;
import common.xandayn.personalrecipes.util.References;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;


public class ShapedRecipeRemoveGUIComponent extends RecipeGUIComponent {

    private GuiButton nextPage, lastPage;
    private GUISlot[] inputs;
    private GUISlot output;
    private ShapedRecipeHandler handler;
    private int selected = 0;
    private int pageCount;

    public ShapedRecipeRemoveGUIComponent(ShapedRecipeHandler handler){
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
        ShapedRecipes data = handler.getRecipes().get(selected);
        int width = data.recipeWidth;
        int height = data.recipeHeight;
        int item = 0;
        for(int i = 0; i < 9; i++) {
            int x = i % 3;
            int y = i / 3;
            inputs[i].setItem(null);
            if(x <= width - 1 && y <= height - 1) {
                inputs[i].setActive(true);
                inputs[i].setItem(data.recipeItems[item].copy());
                item++;
            } else {
                inputs[i].setActive(false);
            }
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
                if(FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
                    NetworkHandler.NETWORK.sendToServer(new ServerRemoveOldRecipe(RecipeRegistry.INSTANCE.getAliasIntID(handler.getID()), selected));
                } else {
                    handler.deleteRecipe(selected);
                }
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
