package me.rayorsomething.mrgnfndr;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.rayorsomething.mrgnfndr.commands.MrgnfndrCommand;
import me.rayorsomething.mrgnfndr.commands.sub.Subcommand;
import me.rayorsomething.mrgnfndr.commands.sub.Help;
import me.rayorsomething.mrgnfndr.commands.sub.Find;

// (partly) Taken from Mindlessly
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
    public static String uuid;
    public static Config config = new Config();
    public static MrgnfndrCommand commandManager = new MrgnfndrCommand(new Subcommand[] {
            new Help(),
            new Find()
    });

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ProgressManager.ProgressBar progressBar = ProgressManager.push("Mrgn_Fndr", 1);
        progressBar.step("Registering events and commands");
        ClientCommandHandler.instance.registerCommand(commandManager);
        config.preload();
        uuid = Minecraft.getMinecraft().getSession().getPlayerID();
        Reference.logger.info("Registered events and commands!");
        ProgressManager.pop(progressBar);
    }
}