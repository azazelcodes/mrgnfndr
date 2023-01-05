package me.rayorsomething.mrgnfndr.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.rayorsomething.mrgnfndr.Config;

import static me.rayorsomething.mrgnfndr.Config.enchants;
import static me.rayorsomething.mrgnfndr.Config.maxCost;
import static me.rayorsomething.mrgnfndr.utility.MainUtils.getJson;

public class APIHandler {
    public static List < Integer > bzRBuy = new ArrayList < > ();
    public static List < Integer > bzRSell = new ArrayList < > ();
    public static List < String > bzNames = new ArrayList < > ();
    public static List < String > itemNames = new ArrayList < > ();

    public static void findMargins() {
        itemNames.clear();

        JsonArray itemArray = Objects.requireNonNull(getJson("https://api.hypixel.net/resources/skyblock/items")).getAsJsonObject().get("items").getAsJsonArray();

        for (JsonElement item: itemArray) {
            JsonObject itemCurrent = item.getAsJsonObject();
            JsonElement itemNameJSON = itemCurrent.get("name");

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
            int margin = bzRBuy.get(index) - bzRSell.get(index);

            if ((maxCost == 0 || bzRSell.get(index) < maxCost) && margin > Config.minMargin) {
                if (itemNames.contains(nameEntry)) {
                    MainUtils.sendMessageWithPrefix(String.format("Item: %1$s for %2$d margin!", itemNames.get(itemNames.indexOf(nameEntry)), margin));
                } else if (enchants && nameEntry.contains("ENCHANTMENT_")) {
                    MainUtils.sendMessageWithPrefix(String.format("Item: %1$s for %2$d margin!",nameEntry, margin));
                } else {
                    MainUtils.sendMessageWithPrefix(String.format("Item: %1$s for %2$d margin!",nameEntry, margin));
                }
            }
            index++;
        }

        MainUtils.sendMessageWithPrefix("Updated Prices!");
    }
}
