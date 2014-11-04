package common.xandayn.personalrecipes.client.gui.component;

import common.xandayn.personalrecipes.util.References;
import common.xandayn.personalrecipes.util.Rendering;

public class GUICheckBox extends GUIComponent{

    private static final int _CHECK_BOX_TEXTURE_X = 44;
    private static final int _CHECK_BOX_TEXTURE_Y = 85;
    private static final int _CHECK_BOX_TEXTURE_WIDTH = 14;
    private static final int _CHECK_BOX_TEXTURE_HEIGHT = 14;

    private boolean checked;

    public GUICheckBox(int x, int y, int size, boolean checked){
        super(x, y, size, size);
        this.checked = checked;
    }

    public GUICheckBox(int x, int y) {
        this(x, y, 5, false);
    }

    public boolean isChecked(){
        return checked;
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        if(contains(mouseX, mouseY)) {
            checked = !checked;
        }
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        Rendering.bindTexture(References.GUI_COMPONENTS);
        Rendering.drawTexturedRectangleWithUVStretching(x, y, width, height, _CHECK_BOX_TEXTURE_X, checked ? _CHECK_BOX_TEXTURE_Y + _CHECK_BOX_TEXTURE_HEIGHT : _CHECK_BOX_TEXTURE_Y, _CHECK_BOX_TEXTURE_WIDTH, _CHECK_BOX_TEXTURE_HEIGHT);
    }
}
