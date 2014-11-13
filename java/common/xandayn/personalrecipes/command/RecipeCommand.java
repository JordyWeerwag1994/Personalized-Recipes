package common.xandayn.personalrecipes.command;

import common.xandayn.personalrecipes.recipe.RecipeRegistry;
import common.xandayn.personalrecipes.util.References;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

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
public class RecipeCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "recipe";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "recipe (optional)[type] (optional)[-remove]";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(!(sender instanceof EntityPlayer)){
            sender.addChatMessage(new ChatComponentText("This command must be sent by a player ingame."));
            return;
        }
        if(args.length > 2) {
            sender.addChatMessage(new ChatComponentText("Invalid usage."));
            sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
            return;
        }
        int id = 0, remove = 0;
        String temp = "ERROR";
        for(String arg : args) {
            if(arg.equalsIgnoreCase("-remove")) {
                remove = 1;
                continue;
            }
            temp = arg.toLowerCase().substring(0, 1).toUpperCase() + arg.toLowerCase().substring(1);
            System.out.println(temp);
            if(!RecipeRegistry.INSTANCE.isAliasUnique(temp)) {
                id = RecipeRegistry.INSTANCE.getAliasIntID(temp);
            }
        }
        if(remove != 0 && RecipeRegistry.INSTANCE.getRecipeCount(id) == 0) {
            sender.addChatMessage(new ChatComponentText("No recipes registered for selected type: \"" + temp + "\""));
        } else {
            ((EntityPlayer) sender).openGui(References.MOD_ID, id, null, remove, 0, 0);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return (sender instanceof EntityPlayer) && (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) sender).getGameProfile()));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] var2) {
        ArrayList<String> correctCommands = new ArrayList<>();
        if(var2.length == 0) {
            correctCommands.addAll(RecipeRegistry.INSTANCE.getRegisteredAliases());
            return correctCommands;
        } else if(var2.length == 1) {
            String partialCommand = var2[0];
            for(String s : RecipeRegistry.INSTANCE.getRegisteredAliases()){
                if(s.contains(partialCommand) && !s.equals(partialCommand)){
                    correctCommands.add(s);
                }else if(s.equals(partialCommand)){
                    return null;
                }
            }
            return correctCommands;
        } else {
            return null;
        }
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
