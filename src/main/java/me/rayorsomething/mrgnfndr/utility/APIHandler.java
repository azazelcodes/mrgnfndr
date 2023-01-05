package me.rayorsomething.mrgnfndr.utility;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import java.time.Instant;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import gg.essential.universal.USound;
import gg.essential.universal.UChat;
import gg.essential.universal.wrappers.message.UTextComponent;
import me.rayorsomething.mrgnfndr.Config;
import me.rayorsomething.mrgnfndr.Main;
import me.rayorsomething.mrgnfndr.Reference;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import static me.rayorsomething.mrgnfndr.utility.MainUtils.getJson;

public class APIHandler {
    static JsonObject bzJson;
    static JsonObject npcJson;
    public static List < Integer > bzRBuy = new ArrayList < > ();
    public static List < Integer > bzRSell = new ArrayList < > ();
    public static List < String > bzNames = new ArrayList < > ();
    public static List < String > itemNames = new ArrayList < > ();

    public static void findMargins() {
        itemNames.clear();

        JsonArray npcArray = Objects.requireNonNull(getJson("https://api.hypixel.net/resources/skyblock/items")).getAsJsonObject().get("items").getAsJsonArray();

        for (JsonElement npcItem: npcArray) {
            JsonObject npcCurrent = npcItem.getAsJsonObject();
            JsonElement itemNameJSON = npcCurrent.get("name");

            if (itemNameJSON != null) {
                itemNames.add(itemNameJSON.getAsString());
            }
        }

        bzRBuy.clear();
        bzRSell.clear();
        bzNames.clear();

        JsonObject bzObject = Objects.requireNonNull(getJson("https://api.hypixel.net/skyblock/bazaar")).getAsJsonObject().get("products").getAsJsonObject();

        for (Map.Entry<String, JsonElement> bzEntry : bzObject.entrySet()) {
            JsonObject bzCurrent = (JsonObject) bzObject.get(bzEntry.getKey());

            JsonObject bzStatus = (JsonObject) bzCurrent.get("quick_status");

            bzRBuy.add((int) Math.round(Double.parseDouble(bzStatus.get("buyPrice").getAsString())));
            bzRSell.add((int) Math.round(Double.parseDouble(bzStatus.get("sellPrice").getAsString())));
            bzNames.add(bzCurrent.get("product_id").toString());
        }

        int index = 0;
        for (String nameEntry : bzNames) {
            if (itemNames.contains(nameEntry) && bzRBuy.get(index)-bzRSell.get(index) > Config.minMargin) {
                MainUtils.sendMessageWithPrefix(String.format("Item: %i for %m margin!",itemNames.indexOf(nameEntry), bzRBuy.get(index)-bzRSell.get(index)));
            } else if (bzRBuy.get(index)-bzRSell.get(index) > Config.minMargin) {
                MainUtils.sendMessageWithPrefix(String.format("Item: %i for %m margin!",nameEntry, bzRBuy.get(index)-bzRSell.get(index)));
            }
            index++;
        }

        MainUtils.sendMessageWithPrefix("Updated Prices!");
    }
}
