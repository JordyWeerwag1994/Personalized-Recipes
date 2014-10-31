package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ShapedRecipeGUIComponent extends RecipeGUIComponent {

    private static final int MAX_SLOTS_ENABLED_XY = 3;

    private ResourceLocation background;
    private int enabledSlotsX = 3;
    private int enabledSlotsY = 3;

    private GuiButton xMinus, xPlus, yMinus, yPlus;

    public ShapedRecipeGUIComponent(){
        background = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/component/recipe_component.png");
        xSize = 128;
        ySize = 86;
    }

    @Override
    public void initGUI(RecipeHandlerGUI gui) {
        super.initGUI(gui);
        this.gui.registerGuiButton(new GuiButton(0, guiLeft + 87, guiTop + 16, 26, 16, "Save"));
        this.gui.registerGuiButton(xMinus = new GuiButton(1, guiLeft + 37, guiTop + 5, 8, 8, "-"));
        this.gui.registerGuiButton(xPlus = new GuiButton(2, guiLeft + 47, guiTop + 5, 8, 8, "+"));
        this.gui.registerGuiButton(yMinus = new GuiButton(3, guiLeft + 8, guiTop + 34, 8, 8, "-"));
        this.gui.registerGuiButton(yPlus = new GuiButton(4, guiLeft + 8, guiTop + 44, 8, 8, "+"));
        this.gui.registerGuiButton(new GuiButton(5, guiLeft + 87, guiTop + 54, 26, 16, "Back"));
        for(int i = 0; i < 9; i++){
            int x = i % 3;
            int y = i / 3;
            int bufferX = x * 2;
            int bufferY = y * 2;
            slots.add(new GUISlot(20 + (16 * x) + bufferX, 17 + (16 * y) + bufferY, 1));
        }
        slots.add(new GUISlot(92, 35, 64));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id){
            case 0:
                gui.returnToSelectScreen();
                break;
            case 1:
                enabledSlotsX--;
                break;
            case 2:
                enabledSlotsX++;
                break;
            case 3:
                enabledSlotsY--;
                break;
            case 4:
                enabledSlotsY++;
                break;
            case 5:
                gui.returnToSelectScreen();
                break;
        }
    }

    @Override
    public void updateScreen() {
        if(enabledSlotsX == 1) {
            xMinus.enabled = false;
        } else if(enabledSlotsX == MAX_SLOTS_ENABLED_XY) {
            xPlus.enabled = false;
        } else {
            xMinus.enabled = true;
            xPlus.enabled = true;
        }

        if(enabledSlotsY == 1) {
            yMinus.enabled = false;
        } else if(enabledSlotsY == MAX_SLOTS_ENABLED_XY) {
            yPlus.enabled = false;
        } else {
            yMinus.enabled = true;
            yPlus.enabled = true;
        }

        for(int x = 0; x < MAX_SLOTS_ENABLED_XY; x++){
            for(int y = 0; y < MAX_SLOTS_ENABLED_XY; y++){
                int key = y * MAX_SLOTS_ENABLED_XY + x;
                if(x >= enabledSlotsX || y >= enabledSlotsY){
                    slots.get(key).setActive(false);
                } else {
                    slots.get(key).setActive(true);
                }
            }
        }
    }

    @Override
    public void renderBackground(float delta, int mouseX, int mouseY) {
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        gui.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
