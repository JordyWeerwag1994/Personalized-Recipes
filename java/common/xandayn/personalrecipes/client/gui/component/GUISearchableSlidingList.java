package common.xandayn.personalrecipes.client.gui.component;

import java.util.ArrayList;

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
}
