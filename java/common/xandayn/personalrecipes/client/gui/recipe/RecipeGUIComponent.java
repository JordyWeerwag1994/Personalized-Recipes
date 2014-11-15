package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public abstract class RecipeGUIComponent {

    protected int guiLeft = 0, guiTop = 0;
    protected int xSize, ySize;
    protected ResourceLocation texture;
    protected RecipeHandlerGUI gui;
    protected ArrayList<GuiButton> buttonList;
    protected EntityPlayer player;

    protected ArrayList<GUIComponent> components;

    public RecipeGUIComponent() {
        this.buttonList = new ArrayList<>();
        this.components = new ArrayList<>();
    }

    public void initGUI(RecipeHandlerGUI gui, EntityPlayer player) {
        buttonList.clear();
        components.clear();
        this.gui = gui;
        this.guiLeft = (gui.width - this.xSize) / 2;
        this.guiTop = gui.getGuiTop();
        this.player = player;
    }

    public void renderBackground(int mouseX, int mouseY) {
        Rendering.bindTexture(texture);
        Rendering.drawTexturedRectangle(guiLeft, guiTop, 0, 0, xSize, ySize);
        for(GUIComponent component : components){
            component.renderBackground(mouseX, mouseY);
        }
    }

    public void renderForeground(int mouseX, int mouseY) {
        for(GUIComponent slot : components) {
            slot.renderForeground(mouseX, mouseY);
        }
        for(GuiButton button : buttonList) {
            button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }
    }

    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        for(GuiButton button : buttonList) {
            if(button.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)){
                button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
                actionPerformed(button);
                break;
            }
        }
        for(GUIComponent component : components){
            component.mousePressed(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(GUIComponent component : components){
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress) {
        for(GUIComponent component : components){
            component.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSincePress);
        }
    }

    public void update(int mouseX, int mouseY){
        for(GUIComponent component : components){
            component.update(mouseX, mouseY);
        }
    }

    public abstract void actionPerformed(GuiButton button);

    public boolean keyTyped(char value, int keyCode) {
        for(GUIComponent component : components) {
            if(component.keyTyped(value, keyCode))
                return true;
        }
        return false;
    }
}
