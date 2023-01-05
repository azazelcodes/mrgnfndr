package me.rayorsomething.mrgnfndr.utility;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import gg.essential.universal.UChat;
import net.minecraft.util.EnumChatFormatting;


// Taken from Mindlessly
public class MainUtils {
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

    public static void sendMessageWithPrefix(String message) {
        UChat.chat(EnumChatFormatting.GOLD + ("[mrgnfndr] ") + EnumChatFormatting.GRAY + message.replaceAll("&", "§"));
    }
}