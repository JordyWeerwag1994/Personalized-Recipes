package common.xandayn.personalrecipes.client.gui;

import common.xandayn.personalrecipes.client.gui.recipe.IRecipeGUIComponent;
import common.xandayn.personalrecipes.lib.References;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

/**
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
public class RecipeHandlerGUI extends GuiContainer {

    private static final int _STRING_HEIGHT = 9;
    private static final int _STRINGS_WITHOUT_SCROLL = 7;
    /**
     * An enumeration to define what our current state is,
     * if we've just opened the inventory and haven't selected
     * a recipe handler, or if we've selected a handler to use.
     */
    private GUIState _curState;

    private ResourceLocation background;
    private IRecipeGUIComponent component = null;
    private EntityPlayer player;

    //JUST_OPENED state variables
    private String[] aliases;
    private boolean sliding, slideEnabled;
    private int mouseLastY;
    private int scrollValue;
    private int arrayOffset;
    private int selected = -1;
    private Rectangle slider;
    private Rectangle selectionBox;
    private Rectangle[] selections;
    private GuiButton selectButton;
    private GuiButton exitButton;

    private int sliderScroll = 0, maxSliderScroll;

    private enum GUIState {
        JUST_OPENED,
        TYPE_SELECTED
    }

    /**
     * A basic implementation of the Container class, the only reason this
     * is here is because GuiContainer requires it, it is unused other than
     * that.
     */
    public static class RH_Container extends Container {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }
    }

    public RecipeHandlerGUI(EntityPlayer player) {
        super(new RH_Container());
        _curState = GUIState.JUST_OPENED;
        background = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/handler_selector.png");
        xSize = 172;
        ySize = 128;
        aliases = RecipeRegistry.getRegisteredAliases().toArray(new String[RecipeRegistry.registeredRecipeHandlerCount()]);
        this.player = player;
        maxSliderScroll = 61;
        slideEnabled =  aliases.length >= _STRINGS_WITHOUT_SCROLL;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);
    }

    @Override
    public void initGui() {
        super.initGui();
        initialize();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        switch (_curState){
            case JUST_OPENED:
                for(int i = 0; i < selections.length; i++){
                    if(i >= aliases.length) break;
                    if(selections[i].contains(mouseX, mouseY)){
                        selected = selected == i + arrayOffset ? -1 : i + arrayOffset;
                        break;
                    }
                }
                break;
            case TYPE_SELECTED:
                component.mousePressed(mouseX, mouseY, mouseButton);
                break;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceClick) {
        super.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceClick);
        switch (_curState){
            case JUST_OPENED:
                slider.grow(0, 3);
                sliding = slider.contains(mouseX, mouseY - sliderScroll);
                slider.grow(0, -3);
                break;
            case TYPE_SELECTED:
                component.mousePressedAndDragged(mouseX, mouseY, mouseButton, timeSinceClick);
                break;
        }
    }

    private void startTypeSelectedState(){
        buttonList.clear();
        component = RecipeRegistry.getRecipeGUIComponent(RecipeRegistry.getAliasIntID(aliases[selected]));
        _curState = GUIState.TYPE_SELECTED;
        component.initGUI(this);
    }

    private void initialize() {
        _curState = GUIState.JUST_OPENED;
        int hiddenAliases = slideEnabled ? aliases.length - _STRINGS_WITHOUT_SCROLL : 0;
        scrollValue = hiddenAliases == 0 ? 0 : maxSliderScroll / hiddenAliases;
        buttonList.clear();
        component = null;
        selected = -1;
        slider = new Rectangle(guiLeft + 81, guiTop + 29, 10, 9);
        selections = new Rectangle[_STRINGS_WITHOUT_SCROLL];
        for(int i = 0; i < selections.length; i++){
            int yOffset = i * 9;
            selections[i] = new Rectangle(23 + guiLeft, (29 + yOffset) + guiTop, 58, 9);
        }
        selectionBox = new Rectangle(guiLeft + 23, guiTop + 29, 58, 70);
        registerGuiButton(selectButton = new GuiButton(0, guiLeft + 110, guiTop + 37, 39, 20, "Select"));
        registerGuiButton(exitButton = new GuiButton(1, guiLeft + 110, guiTop + 71, 39, 20, "Exit"));
        exitButton.enabled = false;
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
        switch (_curState){
            case JUST_OPENED:
                sliding = false;
                break;
            case TYPE_SELECTED:
                component.mouseReleased(mouseX, mouseY, mouseButton);
                break;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (_curState){
            case JUST_OPENED:
                switch (button.id){
                    case 0:
                        if(selected > -1){
                            startTypeSelectedState();
                        }
                        break;
                    case 1:
                        player.closeScreen();
                        break;
                }
                break;
            case TYPE_SELECTED:
                component.actionPerformed(button);
                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        switch (_curState){
            case JUST_OPENED:
                selectButton.enabled = selected != -1;
                if(exitButton != null && !exitButton.enabled) exitButton.enabled = true;
                break;
            case TYPE_SELECTED:
                component.updateScreen();
                break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int mouseX, int mouseY) {
        switch (_curState){
            case JUST_OPENED:
                Minecraft.getMinecraft().renderEngine.bindTexture(background);
                drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
                int mouseDeltaY = mouseY - mouseLastY;
                mouseLastY = mouseY;
                drawTexturedModalRect(slider.getX(), slider.getY() + sliderScroll, 172, slideEnabled ? 0 : slider.getHeight(), slider.getWidth(), slider.getHeight());
                if(slideEnabled) {
                    if (sliding) {
                        sliderScroll += mouseDeltaY;
                        if (sliderScroll < 0) sliderScroll = 0;
                        if (sliderScroll > maxSliderScroll) sliderScroll = maxSliderScroll;
                    } else if(selectionBox.contains(mouseX, mouseY)) {
                        sliderScroll -= (Mouse.getDWheel() / 30);
                        if (sliderScroll < 0) sliderScroll = 0;
                        if (sliderScroll > maxSliderScroll) sliderScroll = maxSliderScroll;
                    }
                }
                if(selected - arrayOffset >= 0 && selected - arrayOffset < selections.length){
                    drawTexturedModalRect(selections[selected - arrayOffset].getX(), selections[selected - arrayOffset].getY(), 182, 0, selections[selected - arrayOffset].getWidth(), selections[selected - arrayOffset].getHeight());
                }
                break;
            case TYPE_SELECTED:
                component.renderBackground(delta, mouseX, mouseY);
                break;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        switch (_curState) {
            case JUST_OPENED:
                drawString(fontRendererObj, "Select a Recipe Handler", 28, 6, 0xFFEEEEEE);
                if(selected != -1) {
                    drawString(fontRendererObj, "Selected: " + aliases[selected], 34, 106, 0xFFEEEEEE);
                }
                if(!slideEnabled)
                    for(int i = 0; i < aliases.length; i++) {
                        drawString(fontRendererObj, aliases[i].length() > 10 ? aliases[i].substring(0, 8).concat("...") : aliases[i], 24, 30 + (i * _STRING_HEIGHT), 0xFFFFFFFF);
                    }
                else {
                    arrayOffset = sliderScroll / scrollValue;
                    for(int i = 0; i < _STRINGS_WITHOUT_SCROLL; i++){
                        if(i + arrayOffset >= aliases.length) break;
                        drawString(fontRendererObj, aliases[i + arrayOffset].length() > 10 ? aliases[i + arrayOffset].substring(0, 8).concat("...") : aliases[i + arrayOffset], 24, 30 + (i * _STRING_HEIGHT), 0xFFFFFFFF);
                    }
                }
                break;
            case TYPE_SELECTED:
                GL11.glPushMatrix();
                GL11.glTranslatef(-guiLeft, -guiTop, 0);
                component.renderForeground(mouseX, mouseY);
                GL11.glPopMatrix();
                break;
        }
    }

    public int getSizeX(){
        return xSize;
    }

    public int getSizeY(){
        return ySize;
    }

    public void clearButtonList(){
        buttonList.clear();
    }

    @SuppressWarnings("unchecked")
    public void registerGuiButton(GuiButton button){
        buttonList.add(button);
    }

    public void returnToSelectScreen() {
        initialize();
    }
}
