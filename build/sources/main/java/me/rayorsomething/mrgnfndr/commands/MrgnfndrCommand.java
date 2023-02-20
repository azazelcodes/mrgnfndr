package me.rayorsomething.mrgnfndr.commands;

import gg.essential.api.EssentialAPI;
import me.rayorsomething.mrgnfndr.Main;
import me.rayorsomething.mrgnfndr.Reference;
import me.rayorsomething.mrgnfndr.utility.MainUtils;
import me.rayorsomething.mrgnfndr.commands.sub.Subcommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


// STOLEN FROM MINDLESSLY hehe
public class MrgnfndrCommand extends CommandBase {
    private final Subcommand[] subcommands;

    public MrgnfndrCommand(Subcommand[] subcommands) {
        this.subcommands = subcommands;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("mf", "mrgn");
    }

    @Override
    public String getCommandName() {
        return "mrgnfndr";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/mrgnfndr <subcommand> <arguments>";
    }

    public void sendHelp(ICommandSender sender) {
        List<String> commandUsages = new LinkedList<>();
        for (Subcommand subcommand : this.subcommands) {
            if (!subcommand.isHidden()) {
                commandUsages.add(EnumChatFormatting.GRAY + "/mrgnfndr " + subcommand.getCommandName() + " "
                        + subcommand.getCommandUsage() + EnumChatFormatting.DARK_GRAY + " - " + EnumChatFormatting.GOLD + subcommand.getCommandDescription());
            }
        }
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Mrgn_Fndr " + EnumChatFormatting.GREEN
                + Reference.VERSION + "\n" + String.join("\n", commandUsages)));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            EssentialAPI.getGuiUtil().openScreen(Main.config.gui());
            return;
        }
        for (Subcommand subcommand : this.subcommands) {
            if (Objects.equals(args[0], subcommand.getCommandName())) {
                if (!subcommand.processCommand(sender, Arrays.copyOfRange(args, 1, args.length))) {
                    // processCommand returned false
                    MainUtils.sendMessageWithPrefix("&cFailed to execute command, command usage: /mrgnfndr "
                            + subcommand.getCommandName() + " " + subcommand.getCommandUsage());
                }
                return;
            }
        }
        MainUtils.sendMessageWithPrefix(EnumChatFormatting.RED
                + "The subcommand wasn't found, please refer to the help message below for the list of subcommands");
        sendHelp(sender);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> possibilities = new LinkedList<>();
        for (Subcommand subcommand : subcommands) {
            possibilities.add(subcommand.getCommandName());
        }
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, possibilities);
        }
        return null;
    }
}