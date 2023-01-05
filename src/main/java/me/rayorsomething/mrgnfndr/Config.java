package me.rayorsomething.mrgnfndr;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

public class Config extends Vigilant {

    @Property(type = PropertyType.NUMBER, category = "API Handling", subcategory = "Basic", name = "Minimum Margin", description = "The minimum amount of profit that is required for the mod to send you a margin listing", max = Integer.MAX_VALUE, increment = 5)
    public static int minMargin = 50;
    //@Property(type = PropertyType.SWITCH, category = "Flipping", subcategory = "Basic", name = "Alert Sounds", description = "Whether a pling sound should be played upon flip sent")
    //public static boolean alertSounds = true;
    //@Property(type = PropertyType.TEXT, category = "Confidential", name = "API Key", protectedText = true, description = "Run /api new to set it automatically, or paste one if you do not want to renew it")
    //public static String apiKey = "";

    public static final File CONFIG_FILE = new File("config/mrgnfndr.toml");
    public static final Config INSTANCE = new Config();

    public Config() {
        super(CONFIG_FILE, "Mrgn_Fndr Menu");
        initialize();
    }

    @Property(type = PropertyType.BUTTON, category = "Links", name = "Mindlessly's Discord", description = "Join Mindlessly's Discord server!")
    public static void discord() {
        UDesktop.browse(URI.create("https://discord.gg/b3JBsh8fEd"));
    }

    @Property(type = PropertyType.BUTTON, category = "Links", name = "GitHub", description = "Help with the development!")
    public static void github() {
        UDesktop.browse(URI.create("https://github.com/rayorsomething/mrgn_fndr"));
    }

}
