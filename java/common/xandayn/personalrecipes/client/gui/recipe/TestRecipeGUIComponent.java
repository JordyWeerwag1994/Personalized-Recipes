package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUISearchableSlidingList;
import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TestRecipeGUIComponent extends RecipeGUIComponent {

    GUISearchableSlidingList list;

    public TestRecipeGUIComponent(){
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/handler_selector.png");
        xSize = 172;
        ySize = 128;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui) {
        super.initGUI(gui);
        String[] names = new String[Item.itemRegistry.getKeys().size()];
        int i = 0;
        for(Object o : Item.itemRegistry){
            names[i] = new ItemStack((Item)o).getDisplayName();
            i++;
        }
        list = new GUISearchableSlidingList(guiLeft + 20, guiTop + 26, guiLeft + 110, guiTop + 37, 44, names);
        buttonList.add(new GuiButton(0, guiLeft + 110, guiTop + 71, 39, 20, "Back"));

    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        list.update(mouseX, mouseY);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) {
        super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        list.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        gui.returnToSelectScreen();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        list.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        list.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyTyped(char value, int keyCode) {
        return list.keyTyped(value, keyCode);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        super.renderBackground(mouseX, mouseY);
        list.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        super.renderForeground(mouseX, mouseY);
        list.renderForeground(mouseX, mouseY);
        if(list.getSelected() != null){
            Rendering.drawString("Selected: " + list.getSelected(), guiLeft + 32, guiTop + 108, 0xFFDDDDDD);
        }
    }
}
