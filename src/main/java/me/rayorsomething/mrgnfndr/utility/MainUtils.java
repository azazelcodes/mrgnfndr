package me.rayorsomething.mrgnfndr.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gg.essential.universal.UChat;
import gg.essential.universal.wrappers.message.UTextComponent;
import me.rayorsomething.mrgnfndr.Config;
import me.rayorsomething.mrgnfndr.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;


import java.util.Set;
import com.google.common.collect.Sets;


// Taken from Mindlessly
public class MainUtils {
    public final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static JsonElement getJson(String jsonUrl) {
        try {
            URL url = new URL(jsonUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Connection", "close");
            return new JsonParser().parse(new InputStreamReader(conn.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String removeColorCodes(String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }

    public static void sendMessageWithPrefix(String message) {
        UChat.chat(EnumChatFormatting.GOLD + ("[mrgnfndr] ") + EnumChatFormatting.GRAY + message.replaceAll("&", "§"));
    }
}