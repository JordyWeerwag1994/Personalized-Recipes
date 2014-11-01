package common.xandayn.personalrecipes.client.gui.component.dialog;

import common.xandayn.personalrecipes.client.gui.component.GUISearchableSlidingList;
import common.xandayn.personalrecipes.client.gui.component.GUISlot;
import common.xandayn.personalrecipes.client.gui.component.GUITextField;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIItemListDialog extends GUIDialog<ItemStack> {
    private GUISearchableSlidingList itemList;
    private GuiButton submit;
    private GUISlot slot;
    private GUITextField itemCount;
    private int stackSize;
    ItemStack[] items;
    String[] names;

    public GUIItemListDialog(int x, int y, int stackSize){
        super(x, y, 128, 86);
        this.stackSize = stackSize;
        names = new String[Item.itemRegistry.getKeys().size()];
        items = new ItemStack[names.length];
        int i = 0;
        for(Object o : Item.itemRegistry){
            items[i] = new ItemStack((Item)o);
            names[i] = items[i].getDisplayName();
            i++;
        }
        texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/dialog/item_list_dialog.png");
        itemList = new GUISearchableSlidingList(x + 4, y + 5, x + 79, y + 4, 44, names);
        buttonList.add(submit = new GuiButton(1, x + 79, y + 53, 44, 14, "Submit"));
        buttonList.add(new GuiButton(2, x + 79, y + 68, 44, 14, "Cancel"));
        slot = new GUISlot(x + 83, y + 20, stackSize);
        itemCount = new GUITextField(x + 102, y + 21, 21, 2, null);
        itemCount.setAllowed('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        submit.enabled = itemList.getSelected() != null;
        itemList.update(mouseX, mouseY);
        itemCount.update(mouseX, mouseY);
    }

    @Override
    public void reset() {
        super.reset();
        itemList = new GUISearchableSlidingList(x + 4, y + 5, x + 79, y + 4, 44, names);
        itemCount = new GUITextField(x + 102, y + 21, 21, 2, null);
        itemCount.setAllowed('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
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
        if(itemList.getSelected() != null) {
            for (int i = 0; i < names.length; i++) {
                if (this.itemList.getSelected().equals(names[i])) {
                    slot.setItem(items[i]);
                    break;
                }
            }
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
