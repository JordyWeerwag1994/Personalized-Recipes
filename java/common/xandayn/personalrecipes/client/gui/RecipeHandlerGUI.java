package common.xandayn.personalrecipes.client.gui;

import common.xandayn.personalrecipes.lib.References;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class RecipeHandlerGUI extends GuiContainer {

    /**
     * A constant defining how many recipe handler
     * selection buttons can go on a page.
     */
    private static final int _SELECTION_BUTTONS_PER_PAGE = 4;

    /**
     * An enumeration to define what our current state is,
     * if we've just opened the inventory and haven't selected
     * a recipe handler, or if we've selected a handler to use.
     */
    private GUIState _curState;

    private GuiButton next, previous;

    private ResourceLocation background;
    private String[] aliases;
    private int page = 0, lastPage = 0;
    private final int pageCount;
    private IRecipeGUIComponent component = null;
    private EntityPlayer player;

    private enum GUIState {
        JUST_OPENED,
        TYPE_SELECTED
    }

    /**
     * A basic implementation of the Container class, the only reason this
     * is here is because GuiContainer requires it, it is unused other than
     * that.
     */
    public static class RH_Container extends Container {
        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }
    }

    public RecipeHandlerGUI(EntityPlayer player) {
        super(new RH_Container());
        _curState = GUIState.JUST_OPENED;
        pageCount = (RecipeRegistry.registeredRecipeHandlerCount() / _SELECTION_BUTTONS_PER_PAGE) + 1;
        background = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/handler_selector.png");
        xSize = 127;
        ySize = 91;
        aliases = RecipeRegistry.getRegisteredAliases().toArray(new String[RecipeRegistry.registeredRecipeHandlerCount()]);
        this.player = player;
    }

    @Override
    public void initGui() {
        super.initGui();
        createButtons();
    }

    /**
     * A function to recreate buttons when the page is changed if _curState == JUST_OPENED
     */
    @SuppressWarnings("unchecked")
    private void createButtons(){
        switch (_curState){
            case JUST_OPENED:
                buttonList.clear();
                buttonList.add(previous = new GuiButton(0, 42 + guiLeft, 71 + guiTop, 9, 9, "<"));
                buttonList.add(next = new GuiButton(1, 76 + guiLeft, 71 + guiTop, 9, 9, ">"));
                previous.enabled = page != 0;
                next.enabled = pageCount > 0 && page != pageCount;
                buttonList.add(makeButton(2, 20 + guiLeft, 20 + guiTop, 35, 16, 0));
                buttonList.add(makeButton(3, 72 + guiLeft, 20 + guiTop, 35, 16, 1));
                buttonList.add(makeButton(4, 20 + guiLeft, 51 + guiTop, 35, 16, 2));
                buttonList.add(makeButton(5, 72 + guiLeft, 51 + guiTop, 35, 16, 3));
                break;
        }
    }

    /**
     * The function that creates a button with the correct displayString
     * @param id The ID of the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param position The position of the button (0-3) used to determine if the button is set to an alias or disabled.
     * @return A GuiButton that is either linked to a CustomRecipeHandler or is disabled.
     *
     * @see net.minecraft.client.gui.GuiButton
     * @see common.xandayn.personalrecipes.recipe.RecipeRegistry#getRegisteredAliases()
     */
    private GuiButton makeButton(int id, int x, int y, int width, int height, int position){
        int key = page * _SELECTION_BUTTONS_PER_PAGE + position;
        GuiButton toReturn = new GuiButton(id, x, y, width, height, key > aliases.length - 1 ? "None" : aliases[key]);
        if(toReturn.displayString.equals("None")) toReturn.enabled = false;
        return toReturn;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (_curState){
            case JUST_OPENED:
                switch (button.id){
                    case 0:
                        page--;
                        if(page == 0) {
                            button.enabled = false;
                        }
                        break;
                    case 1:
                        page++;
                        if(page == pageCount - 1){
                            button.enabled = false;
                        }
                        break;
                    default:
                        int id = RecipeRegistry.getAliasIntID(button.displayString);
                        System.out.println(id + " " + button.displayString);
                        player.closeScreen();
                        break;
                }
                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        switch (_curState){
            case JUST_OPENED:
                if(page != lastPage){
                    lastPage = page;
                    createButtons();
                }
                break;
            case TYPE_SELECTED:

                break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
//        switch (_curState){
//            case JUST_OPENED:
//
//                break;
//            case TYPE_SELECTED:
//
//                break;
//        }
    }
}
