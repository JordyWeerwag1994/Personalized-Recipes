package common.xandayn.personalrecipes.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.util.ArrayList;

public abstract class GUIComponent {

    protected int x, y, width, height;
    protected ArrayList<GuiButton> buttonList;

    public GUIComponent(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonList = new ArrayList<>();
    }

    public void update(int mouseX, int mouseY) {
    }

    public abstract void renderBackground(int mouseX, int mouseY);

    public void renderForeground(int mouseX, int mouseY) {
        for(GuiButton button : buttonList){
            button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }
    }

    public void actionPerformed(GuiButton button){ }

    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        for(GuiButton button : buttonList){
            if(button.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)){
                button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                actionPerformed(button);
                break;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, float timeSincePress) { }

    public boolean keyTyped(char key, int keyCode) { return false; }

    public boolean contains(int x, int y) {
        return x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height;
    }
}
