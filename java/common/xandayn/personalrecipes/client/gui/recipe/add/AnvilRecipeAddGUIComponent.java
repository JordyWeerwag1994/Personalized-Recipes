package common.xandayn.personalrecipes.client.gui.recipe.add;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIInventoryComponent;
import common.xandayn.personalrecipes.client.gui.component.GUIItemListDialogSlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.client.gui.recipe.RecipeGUIComponent;
import common.xandayn.personalrecipes.recipe.data.AnvilRecipeData;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class AnvilRecipeAddGUIComponent extends RecipeGUIComponent {

    private GUITextField recipeCost;
    private GuiButton save;
    private GUIItemListDialogSlot input1, input2, output, dialogSlot;
    private GUIInventoryComponent inventoryComponent;

    public AnvilRecipeAddGUIComponent() {
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
        buttonList.add(save = new GuiButton(0, guiLeft + 12, guiTop + 7, 36, 20, "Save"));
        buttonList.add(new GuiButton(1, guiLeft + 80, guiTop + 7, 36, 20, "Back"));
        components.add(inventoryComponent = new GUIInventoryComponent(guiLeft - ((GUIInventoryComponent._TEXTURE_WIDTH / 2) - (xSize / 2)), guiTop + ySize, player, gui));
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if(dialogSlot == null) {
            super.update(mouseX, mouseY);
            save.enabled = input1.getItem() != null && input2.getItem() != null && output.getItem() != null;
            if(recipeCost.getText() != null) {
                if(Integer.valueOf(recipeCost.getText()) < 1) {
                    recipeCost.setText(String.valueOf(1));
                }
            }
            for (GUIComponent component : components) {
                if (component instanceof GUIItemListDialogSlot) {
                    GUIItemListDialogSlot slot = (GUIItemListDialogSlot) component;
                    if (slot.isDialogOpen()){
                        dialogSlot = slot;
                        break;
                    }
                }
            }
        } else {
            dialogSlot.update(mouseX, mouseY);
            if(!dialogSlot.isDialogOpen()) dialogSlot = null;
        }
    }

    @Override
    public boolean keyTyped(char value, int keyCode) {
        if(dialogSlot == null)
            return super.keyTyped(value, keyCode);
        else
            return dialogSlot.keyTyped(value, keyCode);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(dialogSlot == null) {
            switch (button.id) {
                case 0:
                    AnvilRecipeData data = new AnvilRecipeData();
                    data.recipeCost = Integer.valueOf(recipeCost.getText());
                    data.itemInputs = new ArrayList<>(Arrays.asList(input1.getItem(), input2.getItem()));
                    data.itemOutputs = new ArrayList<>(Arrays.asList(output.getItem()));
                    data.register();
                    gui.returnToSelectScreen();
                    break;
                case 1:
                    gui.returnToSelectScreen();
                    break;
            }
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(dialogSlot == null) {
            if(!inventoryComponent.hasSelection())
                super.mousePressed(mouseX, mouseY, mouseButton);
            else {
                for(GuiButton button : buttonList) {
                    if(button.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)){
                        button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                        actionPerformed(button);
                        break;
                    }
                }
                for(GUIComponent component : components){
                    if(component instanceof GUIItemListDialogSlot) {
                        if(component.contains(mouseX, mouseY)) {
                            ItemStack item = null;
                            if(mouseButton == 0) {
                                item = inventoryComponent.getSelectedItem();
                            }
                            ((GUIItemListDialogSlot) component).setItem(item);
                        }
                    } else {
                        component.mousePressed(mouseX, mouseY, mouseButton);
                    }
                }
            }
        } else
            dialogSlot.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) {
        if(dialogSlot == null)
            super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        else
            dialogSlot.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(dialogSlot == null)
            super.mouseReleased(mouseX, mouseY, mouseButton);
        else
            dialogSlot.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(dialogSlot == null)
            super.renderBackground(mouseX, mouseY);
        else
            dialogSlot.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(dialogSlot == null)
            super.renderForeground(mouseX, mouseY);
        else
            dialogSlot.renderForeground(mouseX, mouseY);
    }
}
