package me.rayorsomething.mrgnfndr.commands.sub;

import me.rayorsomething.mrgnfndr.Config;
import net.minecraft.command.ICommandSender;

public class Cost implements Subcommand {
    @Override
    public String getCommandName() {
        return "cost";
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
        return "Sets the max cost";
    }

    @Override
    public boolean processCommand(ICommandSender sender, String[] args) {
        Config.maxCost = Integer.parseInt(args[0]);
        return true;
    }
}