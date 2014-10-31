package common.xandayn.personalrecipes.client.gui.recipe;

import common.xandayn.personalrecipes.client.gui.RecipeHandlerGUI;
import net.minecraft.client.gui.GuiButton;

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
public interface IRecipeGUIComponent {
    public void initGUI(RecipeHandlerGUI gui);
    public void actionPerformed(GuiButton button);
    public void renderBackground(float delta, int mouseX, int mouseY);
    public void renderForeground(int mouseX, int mouseY);
    public void mousePressed(int mouseX, int mouseY, int mouseButton);
    public void mousePressedAndDragged(int mouseX, int mouseY, int mouseButton, long timeSincePress);
    public void mouseReleased(int mouseX, int mouseY, int mouseButton);
    public void updateScreen();
}
