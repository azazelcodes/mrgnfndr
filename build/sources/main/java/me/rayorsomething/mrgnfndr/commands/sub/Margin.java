package me.rayorsomething.mrgnfndr.commands.sub;

import me.rayorsomething.mrgnfndr.Config;
import me.rayorsomething.mrgnfndr.utility.APIHandler;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;

public class Margin implements Subcommand {
    @Override
    public String getCommandName() {
        return "margin";
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    @Override
    public String getCommandDescription() {
        return "Sets the min margin";
    }

    @Override
    public boolean processCommand(ICommandSender sender, String[] args) {
        Config.minMargin = Integer.parseInt(args[0]);
        return true;
    }
}