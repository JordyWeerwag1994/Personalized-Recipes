package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;

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
public class GUISlidingList extends GUIComponent {
    protected static final int _STRING_HEIGHT = 9;
    protected static final int _STRINGS_WITHOUT_SCROLL = 7;
    protected static final int _SELECTION_WIDTH = 58;
    protected static final int _SELECTION_HEIGHT = 70;
    protected static final int _SELECTION_OFFSET_X = 3;
    protected static final int _SELECTION_OFFSET_Y = 3;
    protected static final int _SLIDER_OFFSET_X = 61;
    protected static final int _SLIDER_OFFSET_Y = 3;
    protected static final int _MAX_SLIDER_SCROLL = 61;

    protected static final int _STRING_BUFFER = 1;

    protected static final int _GUI_SLIDER_DECORATIVE_TEXTURE_WIDTH = 74;
    protected static final int _GUI_SLIDER_DECORATIVE_TEXTURE_HEIGHT = 76;
    protected static final int _GUI_SLIDER_DECORATIVE_TEXTURE_X = 0;
    protected static final int _GUI_SLIDER_DECORATIVE_TEXTURE_Y = 0;

    protected static final int _GUI_SLIDER_SLIDER_TEXTURE_WIDTH = 10;
    protected static final int _GUI_SLIDER_SLIDER_TEXTURE_HEIGHT = 9;
    protected static final int _GUI_SLIDER_SLIDER_TEXTURE_X = 74;
    protected static final int _GUI_SLIDER_SLIDER_TEXTURE_Y = 0;

    protected static final int _GUI_SLIDER_SELECTION_TEXTURE_WIDTH = 58;
    protected static final int _GUI_SLIDER_SELECTION_TEXTURE_HEIGHT = 9;
    protected static final int _GUI_SLIDER_SELECTION_TEXTURE_X = 0;
    protected static final int _GUI_SLIDER_SELECTION_TEXTURE_Y = 76;

    protected String[] selectionStrings;
    protected boolean slideEnabled, sliding;
    protected int mouseLastY;
    protected int scrollValue;
    protected int selected;
    protected int arrayOffset;
    protected int sliderScroll = 0;
    protected Rectangle slider;
    protected Rectangle selectionBox;
    protected Rectangle[] selections;


    public GUISlidingList(int xPos, int yPos, String... selectionStrings){
        super(xPos, yPos, _GUI_SLIDER_DECORATIVE_TEXTURE_X, _GUI_SLIDER_DECORATIVE_TEXTURE_Y);
        this.selectionStrings = selectionStrings;
        this.selected = -1;
        selections = new Rectangle[_STRINGS_WITHOUT_SCROLL];
        for(int i = 0; i < selections.length; i++){
            int yOffset = i * _STRING_HEIGHT;
            selections[i] = new Rectangle(_SELECTION_OFFSET_X + xPos, (_SELECTION_OFFSET_Y + yOffset) + yPos, _GUI_SLIDER_SELECTION_TEXTURE_WIDTH, _GUI_SLIDER_SELECTION_TEXTURE_HEIGHT);
        }
        selectionBox = new Rectangle(xPos + _SELECTION_OFFSET_X, yPos + _SELECTION_OFFSET_Y, _SELECTION_WIDTH, _SELECTION_HEIGHT);
        slider = new Rectangle(xPos + _SLIDER_OFFSET_X, yPos + _SLIDER_OFFSET_Y, _GUI_SLIDER_SLIDER_TEXTURE_WIDTH, _GUI_SLIDER_SLIDER_TEXTURE_HEIGHT);
        slideEnabled =  selectionStrings.length > _STRINGS_WITHOUT_SCROLL;
        int hiddenAliases = slideEnabled ? selectionStrings.length - _STRINGS_WITHOUT_SCROLL : 0;
        scrollValue = hiddenAliases == 0 ? 0 : hiddenAliases > _MAX_SLIDER_SCROLL ? hiddenAliases / _MAX_SLIDER_SCROLL : _MAX_SLIDER_SCROLL / hiddenAliases;
    }

    @Override
    public void update(int mouseX, int mouseY){
        int mouseDeltaY = mouseY - mouseLastY;
        int movement = Mouse.getDWheel();
        mouseLastY = mouseY;
        if(slideEnabled) {
            if (sliding) {
                sliderScroll += mouseDeltaY;
                if (sliderScroll < 0) sliderScroll = 0;
                if (sliderScroll > _MAX_SLIDER_SCROLL) sliderScroll = _MAX_SLIDER_SCROLL;
            } else if(selectionBox.contains(mouseX, mouseY)) {
                sliderScroll -= (movement / 120);
                if (sliderScroll < 0) sliderScroll = 0;
                if (sliderScroll > _MAX_SLIDER_SCROLL) sliderScroll = _MAX_SLIDER_SCROLL;
            }
        }
    }

    @Override
    public void renderBackground(int mouseX, int mouseY){
        Rendering.bindTexture(References.GUI_COMPONENTS);
        Rendering.drawTexturedRectangle(x, y, _GUI_SLIDER_DECORATIVE_TEXTURE_X, _GUI_SLIDER_DECORATIVE_TEXTURE_Y, _GUI_SLIDER_DECORATIVE_TEXTURE_WIDTH, _GUI_SLIDER_DECORATIVE_TEXTURE_HEIGHT);
        if(selected - arrayOffset >= 0 && selected - arrayOffset < selections.length){
            Rendering.drawTexturedRectangle(selections[selected - arrayOffset].getX(), selections[selected - arrayOffset].getY(), _GUI_SLIDER_SELECTION_TEXTURE_X, _GUI_SLIDER_SELECTION_TEXTURE_Y, selections[selected - arrayOffset].getWidth(), selections[selected - arrayOffset].getHeight());
        }
        Rendering.drawTexturedRectangle(slider.getX(), slider.getY() + sliderScroll, _GUI_SLIDER_SLIDER_TEXTURE_X, slideEnabled ? _GUI_SLIDER_SLIDER_TEXTURE_Y : _GUI_SLIDER_SLIDER_TEXTURE_Y + slider.getHeight(), slider.getWidth(), slider.getHeight());
    }

    @Override
    public void renderForeground(int mouseX, int mouseY){
        if(!slideEnabled)
            for(int i = 0; i < selectionStrings.length; i++) {
                Rendering.drawString(selectionStrings[i].length() >= 10 ? selectionStrings[i].substring(0, 8).concat("...") : selectionStrings[i], x + _SELECTION_OFFSET_X + _STRING_BUFFER, y + _SELECTION_OFFSET_Y + (i * _STRING_HEIGHT) + _STRING_BUFFER, 0xFFFFFFFF);
            }
        else {
            arrayOffset = sliderScroll / scrollValue;
            for (int i = 0; i < _STRINGS_WITHOUT_SCROLL; i++) {
                if (i + arrayOffset >= selectionStrings.length) break;
                Rendering.drawString(selectionStrings[i + arrayOffset].length() >= 10 ? selectionStrings[i + arrayOffset].substring(0, 8).concat("...") : selectionStrings[i + arrayOffset],  x + _SELECTION_OFFSET_X + _STRING_BUFFER, y + _SELECTION_OFFSET_Y + (i * _STRING_HEIGHT) + _STRING_BUFFER, 0xFFFFFFFF);
            }
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        for(int i = 0; i < selections.length; i++){
            if(i >= selectionStrings.length) break;
            if(selections[i].contains(mouseX, mouseY)){
                selected = selected == i + arrayOffset ? -1 : i + arrayOffset;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        sliding = false;
    }

    @Override
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, float timeSincePress) {
        slider.grow(0, 3);
        sliding = slider.contains(mouseX, mouseY - sliderScroll);
        slider.grow(0, -3);
    }

    @Override
    public boolean keyTyped(char key, int keyCode) {
        return false;
    }

    public String getSelected(){
        return selected == -1 || selected >= selectionStrings.length ? null : selectionStrings[selected];
    }

    public void clearSelection() {
        selected = -1;
    }
}
