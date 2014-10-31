package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public abstract class RecipeGUIComponent implements IRecipeGUIComponent {

    protected int guiLeft = 0, guiTop = 0;
    protected int xSize, ySize;
    protected int width;
    protected int height;
    protected RecipeHandlerGUI gui;

    protected ArrayList<GUISlot> slots;

    @Override
    public void initGUI(RecipeHandlerGUI gui) {
        this.slots = new ArrayList<>();
        this.gui = gui;
        this.guiLeft = (gui.width - this.xSize) / 2;
        this.guiTop = (gui.height - this.ySize) / 2;
    }

    @Override
    public void renderBackground(float delta, int mouseX, int mouseY) { }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        for(GUISlot slot : slots) {
            slot.render(guiLeft, guiTop, mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        for(GUISlot slot : slots){
            if(slot.contains(mouseX - guiLeft, mouseY - guiTop)) {
                //TODO: Add in a dialog to set the item.
                if(slot.isEmpty()){
                    slot.setItem(new ItemStack(Items.gold_ingot, 10));
                } else {
                    slot.setItem(null);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) { }

    @Override
    public void actionPerformed(GuiButton button) { }

    @Override
    public void updateScreen() { }
}
