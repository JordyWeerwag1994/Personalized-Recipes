package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.client.gui.component.dialog.GUIItemListDialog;
import net.minecraft.client.gui.GuiButton;

public class GUIItemListDialogSlot extends GUISlot {

    private GUIItemListDialog dialog;
    public boolean wasOpen = false;

    public GUIItemListDialogSlot(int x, int y, int dialogX, int dialogY, int stackLimit) {
        super(x, y, stackLimit);
        dialog = new GUIItemListDialog(dialogX, dialogY, stackLimit);
    }

    public boolean isDialogOpen(){
        return dialog.isOpen();
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            super.update(mouseX, mouseY);
            if(wasOpen) { //dialog was just closed
                wasOpen = false;
                setItem(dialog.getResult());
            }
        } else
            dialog.update(mouseX, mouseY);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(!isActive()) return;
        if(!isDialogOpen())
            super.actionPerformed(button);
        else
            dialog.actionPerformed(button);
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, float timeSincePress) {
        if(!isActive()) return;
        if(!dialog.isOpen())
            super.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        else
            dialog.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            if(this.contains(mouseX, mouseY)) {
                if (mouseButton == 0) {
                    wasOpen = true;
                    dialog.reset();
                    dialog.setResult(getItem());
                    dialog.open();
                } else if (mouseButton == 1) {
                    setItem(null);
                }
            }
        } else
            dialog.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(!isActive()) return;
        if(!isDialogOpen()) {
            super.mouseReleased(mouseX, mouseY, mouseButton);
        } else {
            dialog.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean keyTyped(char key, int keyCode) {
        if(!isDialogOpen())
            return super.keyTyped(key, keyCode);
        else
            return dialog.keyTyped(key, keyCode);
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        if(!isDialogOpen())
            super.renderBackground(mouseX, mouseY);
        else
            dialog.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        if(!isDialogOpen())
            super.renderForeground(mouseX, mouseY);
        else
            dialog.renderForeground(mouseX, mouseY);
    }
}
