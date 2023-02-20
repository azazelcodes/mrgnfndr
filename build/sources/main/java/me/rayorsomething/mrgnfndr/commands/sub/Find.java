package me.rayorsomething.mrgnfndr.commands.sub;

import me.rayorsomething.mrgnfndr.utility.APIHandler;
import net.minecraft.command.ICommandSender;

//Taken from Mindlessly
public class Find implements Subcommand {
    @Override
    public String getCommandName() {
        return "find";
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
        return "Sends the found margins";
    }

    @Override
    public boolean processCommand(ICommandSender sender, String[] args) {
        APIHandler.findMargins();
        return true;
    }
}