package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GUIInventoryComponent extends GUIComponent {

    public static final int _TEXTURE_WIDTH = 176;
    private static final int _TEXTURE_HEIGHT = 90;
    private static final int _TEXTURE_OFFSET_X = 74;
    private static final int _TEXTURE_OFFSET_Y = 0;

    private static final int _SELECTION_TEXTURE_OFFSET_X = 74;
    private static final int _SELECTION_TEXTURE_OFFSET_Y = 90;
    private static final int _SELECTION_TEXTURE_SIZE = 24;

    private GUISlot[] _inventorySlots = new GUISlot[36];
    private GUISlot _selected;

    public GUIInventoryComponent(int x, int y, EntityPlayer player, RecipeHandlerGUI gui) {
        super(x, y, _TEXTURE_WIDTH, _TEXTURE_HEIGHT);
        for(int i = 0; i < 9; i++) {
            _inventorySlots[i] = new GUISlot(x + (8 + 18 * i), y + 66, 64, gui);
            _inventorySlots[i].setItem(player.inventory.mainInventory[i]);
        }
        for(int i = 0; i < 27; i++) {
            _inventorySlots[i+9] = new GUISlot(x + (8 + 18 * (i % 9)), y + (8 + 18 * (i / 9)), 64, gui);
            _inventorySlots[i+9].setItem(player.inventory.mainInventory[i+9]);
        }
    }

    public boolean hasSelection() {
        return _selected != null;
    }

    public ItemStack getSelectedItem() {
        return _selected.getItem();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        for(GUISlot slot : _inventorySlots) {
            if(slot.contains(mouseX, mouseY)) {
                if(_selected == slot) {
                    _selected = null;
                } else {
                    _selected = slot;
                }
            }
        }
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        Rendering.bindTexture(References.GUI_COMPONENTS);
        Rendering.drawTexturedRectangle(x, y, _TEXTURE_OFFSET_X, _TEXTURE_OFFSET_Y, width, height);
        for(GUISlot slot : _inventorySlots) {
            slot.renderBackground(mouseX, mouseY);
        }
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(_selected != null) {
            Rendering.bindTexture(References.GUI_COMPONENTS);
            Rendering.drawTexturedRectangle(_selected.x - 4, _selected.y - 4, _SELECTION_TEXTURE_OFFSET_X, _SELECTION_TEXTURE_OFFSET_Y, _SELECTION_TEXTURE_SIZE, _SELECTION_TEXTURE_SIZE);
        }

        for(GUISlot slot : _inventorySlots) {
            slot.renderForeground(mouseX, mouseY);
        }
    }
}
