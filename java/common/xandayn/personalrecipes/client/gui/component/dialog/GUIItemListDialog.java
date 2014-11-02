package common.xandayn.personalrecipes.client.gui.component.dialog;

import common.xandayn.personalrecipes.client.gui.component.GUISearchableSlidingList;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Util;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class GUIItemListDialog extends GUIDialog<ItemStack> {
    private GUISearchableSlidingList itemList;
    private GuiButton submit;
    private GUISlot slot;
    private GUITextField itemCount;
    private int stackSize;
    ArrayList<ItemStack> items;
    ArrayList<String> names;

    public GUIItemListDialog(int x, int y, int stackSize){
        super(x, y, 128, 86);
        this.stackSize = stackSize;
        items = Util.getAllItemsAndBlocks();
        names = new ArrayList<>(items.size());
        for(ItemStack item : items){
            names.add(item.getDisplayName());
        }
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/dialog/item_list_dialog.png");
        itemList = new GUISearchableSlidingList(x + 4, y + 5, x + 79, y + 4, 44, names.toArray(new String[names.size()]));
        buttonList.add(submit = new GuiButton(1, x + 79, y + 53, 44, 14, "Submit"));
        buttonList.add(new GuiButton(2, x + 79, y + 68, 44, 14, "Cancel"));
        slot = new GUISlot(x + 83, y + 20, stackSize);
        itemCount = new GUITextField(x + 102, y + 21, 21, 2, null);
        itemCount.setAllowed(GUITextField.NUMERIC_ONLY);
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        submit.enabled = itemList.getSelected() != null;
        itemList.update(mouseX, mouseY);
        itemCount.update(mouseX, mouseY);
        if(itemCount.getText() != null){
            int count = Integer.parseInt(itemCount.getText());
            if(count > slot.getItem().getMaxStackSize())
                itemCount.setText(String.valueOf(slot.getItem().getMaxStackSize()));
            else if(count > stackSize)
                itemCount.setText(String.valueOf(stackSize));
        }
    }

    @Override
    public void reset() {
        super.reset();
        itemList = new GUISearchableSlidingList(x + 4, y + 5, x + 79, y + 4, 44, names.toArray(new String[names.size()]));
        itemCount = new GUITextField(x + 102, y + 21, 21, 2, null);
        itemCount.setAllowed(GUITextField.NUMERIC_ONLY);
        slot.setItem(null);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        itemList.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, float timeSincePress) {
        super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        itemList.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(isDisposed()) return;
        super.mousePressed(mouseX, mouseY, mouseButton);
        itemList.mousePressed(mouseX, mouseY, mouseButton);
        itemCount.mousePressed(mouseX, mouseY, mouseButton);
        if(slot.contains(mouseX, mouseY)){
            slot.setItem(null);
            itemList.clearSelection();
        }
    }

    @Override
    public boolean keyTyped(char key, int keyCode) {
        return !isDisposed() && (itemList.keyTyped(key, keyCode) || itemCount.keyTyped(key, keyCode));
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(isDisposed()) return;
        super.renderBackground(mouseX, mouseY);
        itemList.renderBackground(mouseX, mouseY);
        itemCount.renderBackground(mouseX, mouseY);
        slot.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(isDisposed()) return;
        super.renderForeground(mouseX, mouseY);
        itemList.renderForeground(mouseX, mouseY);
        itemCount.renderForeground(mouseX, mouseY);
        slot.renderForeground(mouseX, mouseY);
        if(itemList.getSelected() != null) {
            slot.setItem(items.get(names.indexOf(itemList.getSelected())));
        } else {
            slot.setItem(null);
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(isDisposed()) return;
        switch (button.id){
            case 1:
                //The dialog finished successfully
                this.close();
                result = slot.getItem();
                result.stackSize = itemCount.getText() == null ? result.stackSize : Integer.parseInt(itemCount.getText());
                break;
            case 2:
                //The dialog was not finished successfully
                this.close();
                this.result = null;
                break;
        }
    }
}
