package common.xandayn.personalrecipes.client.gui.component.dialog;

import common.xandayn.personalrecipes.client.gui.component.GUIComponent;
import common.xandayn.personalrecipes.util.Rendering;
import net.minecraft.util.ResourceLocation;

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
