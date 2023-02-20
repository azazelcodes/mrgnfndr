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
    private static List<Integer> bzRBuy;
    private static List<Integer> bzRSell;
    private static List<String> bzNames;
    private static List<String> itemNames;

    static {
        bzRBuy = new ArrayList<>();
        bzRSell = new ArrayList<>();
        bzNames = new ArrayList<>();
        itemNames = new ArrayList<>();
    }

    public static void findMargins() {
        loadItemNames();

        bzRBuy.clear();
        bzRSell.clear();
        bzNames.clear();

        JsonObject bzObject = Objects.requireNonNull(getJson("https://api.hypixel.net/skyblock/bazaar")).getAsJsonObject().get("products").getAsJsonObject();
        for (Map.Entry<String, JsonElement> bzEntry : bzObject.entrySet()) {
            JsonObject bzCurrent = (JsonObject) bzObject.get(bzEntry.getKey());
            JsonObject bzStatus = (JsonObject) bzCurrent.get("quick_status");
            bzRBuy.add((int) Math.round(Double.parseDouble(bzStatus.get("buyPrice").getAsString())));
            bzRSell.add((int) Math.round(Double.parseDouble(bzStatus.get("sellPrice").getAsString())));
            bzNames.add(bzCurrent.get("product_id").getAsString());
        }

        for (int i = 0; i < bzNames.size(); i++) {
            int margin = bzRBuy.get(i) - bzRSell.get(i);
            if ((maxCost == 0 || bzRSell.get(i) < maxCost) && margin > Config.minMargin - 1) {
                String nameEntry = bzNames.get(i);
                if (itemNames.contains(nameEntry)) {
                    MainUtils.sendMessageWithPrefix(String.format("Item: %1$s for %2$d margin!", itemNames.get(itemNames.indexOf(nameEntry)), margin));
                } else if (!enchants && nameEntry.contains("ENCHANTMENT_")) {
                    // Do nothing
                } else {
                    MainUtils.sendMessageWithPrefix(String.format("Item: %1$s for %2$d margin!", nameEntry, margin));
                }
            }
        }
    }

    private static void loadItemNames() {
        if (!itemNames.isEmpty()) {
            return;
        }

        JsonObject jsonObject = Objects.requireNonNull(getJson("https://api.hypixel.net/resources/skyblock/items")).getAsJsonObject();
        JsonArray itemArray = jsonObject.getAsJsonArray("items");
        for (JsonElement item : itemArray) {
            JsonObject itemCurrent = item.getAsJsonObject();
            JsonElement itemNameJSON = itemCurrent.get("name");
            if (itemNameJSON != null) {
                itemNames.add(itemNameJSON.getAsString());
            }
        }
    }
}
