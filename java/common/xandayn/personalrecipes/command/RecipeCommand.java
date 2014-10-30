package common.xandayn.personalrecipes.command;

import common.xandayn.personalrecipes.lib.References;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class RecipeCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "recipe";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "recipe";
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
        EntityPlayer player = (EntityPlayer)sender;
        ((EntityPlayer) sender).openGui(References.MOD_ID, 0, null, 0, 0, 0);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return (sender instanceof EntityPlayer) && (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) sender).getGameProfile()));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
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
