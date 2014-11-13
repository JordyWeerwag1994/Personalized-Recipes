package common.xandayn.personalrecipes.client.gui.component;

import java.util.ArrayList;

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
public class GUISearchableSlidingList extends GUISlidingList {

    private GUITextField searchBar;
    private String[] _originalList;

    public GUISearchableSlidingList(int scrollListX, int scrollListY, int searchBarX, int searchBarY, int searchBarWidth, String... defaults) {
        super(scrollListX, scrollListY, defaults);
        _originalList = defaults;
        searchBar = new GUITextField(searchBarX, searchBarY, searchBarWidth);
    }

    @Override
    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        searchBar.update(mouseX, mouseY);
    }

    private void recalculateListValues(){
        slideEnabled =  selectionStrings.length > _STRINGS_WITHOUT_SCROLL;
        int hiddenAliases = slideEnabled ? selectionStrings.length - _STRINGS_WITHOUT_SCROLL : 0;
        scrollValue = hiddenAliases == 0 ? 0 : hiddenAliases > _MAX_SLIDER_SCROLL ? hiddenAliases / _MAX_SLIDER_SCROLL : _MAX_SLIDER_SCROLL / hiddenAliases;
    }

    private void updateSelections(){
        selected = -1;
        sliderScroll = 0;
        arrayOffset = 0;
        String text = searchBar.getText();
        if(text != null){
            ArrayList<String> subList = new ArrayList<>();
            for(String s : _originalList){
                if(s.toLowerCase().contains(text.toLowerCase())){
                    subList.add(s);
                }
            }
            selectionStrings = subList.toArray(new String[subList.size()]);
            recalculateListValues();
        } else {
            selectionStrings = _originalList;
            recalculateListValues();
        }
    }

    @Override
    public boolean keyTyped(char key, int keyCode){
        if(searchBar.keyTyped(key, keyCode)){
            updateSelections();
            return true;
        }
        return false;
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        super.renderBackground(mouseX, mouseY);
        searchBar.renderBackground(mouseX, mouseY);
    }

    @Override
    public void renderForeground(int mouseX, int mouseY) {
        super.renderForeground(mouseX, mouseY);
        searchBar.renderForeground(mouseX, mouseY);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, int mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        searchBar.mousePressed(mouseX, mouseY, mouseButton);
        if(searchBar.wasCleared()){
            updateSelections();
        }
    }

    public void setSelected(String selected) {
        searchBar.setText(selected);
        updateSelections();
    }
}
