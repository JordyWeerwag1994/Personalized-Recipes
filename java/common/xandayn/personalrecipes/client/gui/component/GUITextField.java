package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;

import java.util.Arrays;
import java.util.List;

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
public class GUITextField extends GUIComponent {

    public static final int MINIMUM_TEXT_FIELD_WIDTH = 14;

    public static final Character[] DEFAULT_ALLOWED = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ,
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '};
    public static final Character[] NUMERIC_ONLY = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    public static final Character[] ALPHABET_ONLY = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    private static final String CARET = "|";
    private static final int _TEXT_FIELD_HEIGHT = 14;
    private static final int _TEXT_FIELD_TEXTURE_SIZE = 14;
    private static final int _TEXT_FIELD_TEXTURE_X = 44;
    private static final int _TEXT_FIELD_TEXTURE_Y = 85;
    private static final int _STRING_BUFFER = 3;
    private boolean active;
    private Rectangle bounds;
    private StringBuilder contents;
    private boolean wasCleared = false;
    private int limit;
    private List<Character> allowed = Arrays.asList(DEFAULT_ALLOWED);

    public GUITextField(int x, int y, int width, String defaultText){
        this(x, y, width, -1, defaultText);
    }

    public GUITextField(int x, int y, int width){
        this(x, y, width, null);
    }

    public GUITextField(int x, int y, int width, int limit, String defaultText){
        super(x, y, width, _TEXT_FIELD_HEIGHT);
        this.limit = limit;
        if(this.width < MINIMUM_TEXT_FIELD_WIDTH) {
            System.err.println("Attemped to create a GUITextField with a width less than 14, width has been set to 14.");
            this.width = MINIMUM_TEXT_FIELD_WIDTH;
        }
        if(defaultText == null)
            contents = new StringBuilder();
        else
            contents = new StringBuilder(defaultText);
        bounds = new Rectangle(x, y, width, _TEXT_FIELD_HEIGHT);
    }

    public void setAllowed(Character... allowed){
        this.allowed = Arrays.asList(allowed);
    }

    public boolean isActive(){
        return active;
    }

    public boolean wasCleared(){
        return wasCleared;
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        wasCleared = false;
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        Rendering.bindTexture(References.GUI_COMPONENTS);
        Rendering.drawTexturedRectangleWithUVStretching(x, y, width, _TEXT_FIELD_HEIGHT, _TEXT_FIELD_TEXTURE_X, _TEXT_FIELD_TEXTURE_Y, _TEXT_FIELD_TEXTURE_SIZE, _TEXT_FIELD_TEXTURE_SIZE);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        String s = contents.toString();
        int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(s.length() > 6 ? s.substring(s.length() - 6, s.length()) : s);
        Rendering.drawString(s.length() > 6 ? s.substring(s.length() - 6, s.length()) : s, x + _STRING_BUFFER, y + _STRING_BUFFER, 0xFFFFFFFF);
        if(active)
            Rendering.drawString(CARET, x + _STRING_BUFFER + width + 1, y + _STRING_BUFFER, 0xFFFFFFFF);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        if(bounds.contains(mouseX, mouseY)){
            switch(mouseButton){
                case 0:
                    active = true;
                    break;
                case 1:
                    wasCleared = true;
                    contents = new StringBuilder();
                    break;
            }
        } else {
            active = false;
        }
    }

    @Override
    public boolean keyTyped(char typed, int keyCode){
        if(active) {
            if (keyCode == Keyboard.KEY_BACK && contents.length() > 0) {
                contents.deleteCharAt(contents.length() - 1);
            } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_ESCAPE) {
                active = false;
            } else {
                if(!allowed.contains(typed)) return false;
                if(limit == -1)
                    contents.append(typed);
                else if(contents.length() < limit){
                    contents.append(typed);
                }
            }
            return true;
        }
        return false;
    }

    public void setText(String text){
        contents = new StringBuilder(text);
    }

    public String getText() {
        return contents.length() == 0 ? null : contents.toString();
    }
}
