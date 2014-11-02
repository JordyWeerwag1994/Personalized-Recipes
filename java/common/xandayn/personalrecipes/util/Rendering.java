package common.xandayn.personalrecipes.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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
public class Rendering {
    private static final Tessellator _TESSELATOR = Tessellator.instance;
    private static final RenderItem _RENDER_ITEM = new RenderItem();
    private static final double _UV_MULT = 0.00390625;

    public static void drawItem(int x, int y, ItemStack item){
        _RENDER_ITEM.zLevel = 200;
        FontRenderer font = item.getItem().getFontRenderer(item);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        RenderHelper.enableGUIStandardItemLighting();
        _RENDER_ITEM.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), item, x, y);
        if(item.stackSize > 1)
            _RENDER_ITEM.renderItemOverlayIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), item, x, y, Integer.toString(item.stackSize));
        RenderHelper.disableStandardItemLighting();
        _RENDER_ITEM.zLevel = 0;
    }

    public static void drawColoredRectangle(int x, int y, int width, int height, int r, int g, int b){
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPushMatrix();
        _TESSELATOR.startDrawing(GL11.GL_QUADS);
        _TESSELATOR.setColorRGBA(r, g, b, 0xFF);
        _TESSELATOR.addVertex(x, y + height, 0);
        _TESSELATOR.setColorRGBA(r, g, b, 0xFF);
        _TESSELATOR.addVertex(x + width,  y + height, 0);
        _TESSELATOR.setColorRGBA(r, g, b, 0xFF);
        _TESSELATOR.addVertex(x + width, y, 0);
        _TESSELATOR.setColorRGBA(r, g, b, 0xFF);
        _TESSELATOR.addVertex(x, y, 0);
        _TESSELATOR.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawTexturedRectangle(int xPos, int yPos, int u, int v, int width, int height){
        GL11.glPushMatrix();
            _TESSELATOR.startDrawing(GL11.GL_QUADS);
            _TESSELATOR.addVertexWithUV(xPos, yPos + height, 0, u * _UV_MULT, (v + height) * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos + width, yPos + height, 0, (u + width) * _UV_MULT, (v + height) * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos + width, yPos, 0, (u + width) * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos, yPos, 0, u * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.draw();
        GL11.glPopMatrix();
    }

    public static void drawTexturedRectangleWithUVStretching(int xPos, int yPos, int u, int v, int width, int height){
        GL11.glPushMatrix();
            _TESSELATOR.startDrawing(GL11.GL_QUADS);
            _TESSELATOR.addVertexWithUV(xPos, yPos + height, 0, u * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos + width, yPos + height, 0, u * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos + width, yPos, 0, u * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.addVertexWithUV(xPos, yPos, 0, u * _UV_MULT, v * _UV_MULT);
            _TESSELATOR.draw();
        GL11.glPopMatrix();
    }

    public static void drawString(String toDraw, int xPos, int yPos, int color){
        Minecraft.getMinecraft().fontRenderer.drawString(toDraw, xPos, yPos, color, true);
    }

    public static void bindTexture(ResourceLocation location) {
        Minecraft.getMinecraft().renderEngine.bindTexture(location);
    }
}
