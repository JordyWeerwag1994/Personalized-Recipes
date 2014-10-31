package common.xandayn.personalrecipes.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GUISlot {

    private static final RenderItem renderItem = new RenderItem();
    private static final Tessellator tesselator = Tessellator.instance;
    public static final int _GUI_SLOT_SIZE = 16;

    private ItemStack item = null;
    private boolean active = true;
    private int x, y;
    public int stackLimit;

    public GUISlot(int x, int y, int stackLimit) {
        this.x = x;
        this.y = y;
        this.stackLimit = stackLimit;
    }

    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GL11.glColor3f(1, 1, 1);
        if(active) {
            if (contains(mouseX - guiLeft, mouseY - guiTop)) {
                render(guiLeft, guiTop, 0xC6);
            }
            if (item != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, 0, 32);
                renderItem.zLevel = 200;
                FontRenderer font = item.getItem().getFontRenderer(item);
                if (font == null) font = Minecraft.getMinecraft().fontRenderer;
                renderItem.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), item, x + guiLeft, y + guiTop);
                if(item.stackSize > 1)
                    renderItem.renderItemOverlayIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), item, x + guiLeft, y + guiTop, Integer.toString(item.stackSize));
                renderItem.zLevel = 0;
                GL11.glPopMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        } else {
            render(guiLeft, guiTop, 0x55);
        }
    }

    private void render(int offsetX, int offsetY, int color){
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPushMatrix();
            tesselator.startDrawing(GL11.GL_QUADS);
            tesselator.setColorRGBA(color, color, color, 0xFF);
            tesselator.addVertex(offsetX + x, offsetY + y + _GUI_SLOT_SIZE, 0);
            tesselator.setColorRGBA(color, color, color, 0xFF);
            tesselator.addVertex(offsetX + x + _GUI_SLOT_SIZE, offsetY + y + _GUI_SLOT_SIZE, 0);
            tesselator.setColorRGBA(color, color, color, 0xFF);
            tesselator.addVertex(offsetX + x + _GUI_SLOT_SIZE, offsetY + y, 0);
            tesselator.setColorRGBA(color, color, color, 0xFF);
            tesselator.addVertex(offsetX + x, offsetY + y, 0);
            tesselator.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public boolean contains(int x, int y){
        return x >= this.x && y >= this.y && x <= this.x + _GUI_SLOT_SIZE && y <= this.y + _GUI_SLOT_SIZE;
    }

    public void setItem(ItemStack item){
        if(item != null) {
            if (item.stackSize > stackLimit) {
                ItemStack temp = item.copy();
                temp.stackSize = stackLimit;
                this.item = temp.copy();
            } else {
                this.item = item.copy();
            }
        } else {
            this.item = null;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        if(!active) item = null;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEmpty() {
        return item == null;
    }
}
