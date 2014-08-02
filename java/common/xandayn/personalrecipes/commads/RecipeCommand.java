package common.xandayn.personalrecipes.commads;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;


/**
 * PersonalizedRecipes - RecipeCommand
 *
 * A handler that registers the "recipe" command to Minecraft.
 *
 * @license
 *   Copyright (C) 2014  xandayn
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author xandayn
 */
public class RecipeCommand implements ICommand{

    private ArrayList<String> aliases;

    public RecipeCommand(){
        aliases = new ArrayList<>();
        aliases.add("recipe");
        aliases.add("rec");
    }

    @Override
    public String getCommandName() {
        return "recipe";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "recipe <type> [optional:remove]";
    }

    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(!(sender instanceof EntityPlayer)){
            sender.addChatMessage(new ChatComponentText("This command must be sent by a player ingame.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }
        if(args.length < 1 || args.length > 2 || !RecipeRegistry.containsAlias(args[0])){
            sender.addChatMessage(new ChatComponentText("Invalid command arguments."));
            sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
            return;
        }
        if(args.length == 2 && !args[1].equalsIgnoreCase("remove")){
            sender.addChatMessage(new ChatComponentText("Invalid command arguments."));
            sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
            return;
        }
        EntityPlayer player = (EntityPlayer)sender;
        player.openGui(PersonalizedRecipes.INSTANCE, RecipeRegistry.getIDFromAlias(args[0]), null, args.length == 2 ? 1 : 0, 0, 0);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return (sender instanceof EntityPlayer) && (PersonalizedRecipes.ALLOW_NON_OP_COMMANDS || MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) sender).getGameProfile()));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
        if(var2.length == 1) {
            String partialCommand = var2[0];
            ArrayList<String> correctCommands = new ArrayList<>();
            for(String s : RecipeRegistry.getAliases()){
                if(s.contains(partialCommand) && !s.equals(partialCommand)){
                    correctCommands.add(s);
                }else if(s.equals(partialCommand)){
                    return null;
                }
            }
            return correctCommands;
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] var1, int var2) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
