package common.xandayn.personalrecipes.client.gui.component.dialog;

import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.util.ResourceLocation;

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
public abstract class GUIDialog<T> extends GUIComponent {
    protected ResourceLocation texture;
    protected boolean disposed = false, open = false;
    /**
     * The object returned by this dialog, the dialog cannot finish
     * until this object is not null
     */
    protected T result;

    public GUIDialog(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public final void close(){
        disposed = true;
        open = false;
    }

    public final void open(){
        open = true;
    }

    public final boolean isOpen() {
        return open;
    }

    public void reset(){
        disposed = false;
        open = false;
        result = null;
    }

    public final boolean hasResult(){
        return result != null;
    }


    public final void setResult(T result){
        this.result = result;
    }

    public final T getResult() {
        return result;
    }

    /**
     * Determines whether or not this Dialog is finished. Every function called externally
     * should check to make sure we haven't been disposed, else things won't work.
     */
    public final boolean isDisposed() {
        return disposed;
    }

    @Override
    public void renderBackground(int mouseX, int mouseY){
        if(isDisposed()) return;
        Rendering.bindTexture(texture);
        Rendering.drawTexturedRectangle(this.x, this.y, 0, 0, this.width, this.height);

    }

    @Override
    public void renderForeground(int mouseX, int mouseY){
        if(isDisposed()) return;
        super.renderForeground(mouseX, mouseY);
    }

}
