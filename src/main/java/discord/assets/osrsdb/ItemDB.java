package discord.assets.osrsdb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discord.utils.Utils;

import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

public class ItemDB {

    public static ItemDB[] item_db = new ItemDB[Utils.getItemDefinitionsSize()];

    public static Calendar calender;

    private final int id;
    private final int buy_price;
    private final int sell_price;
    private final int storePrice;

    private final int highAlch, lowAlch;

    private final String name;

    private final boolean member;

    public ItemDB(String name, int id, int buy_price, int sell_price, int storePrice, boolean member) {
        this.name = name;
        this.id = id;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
        this.storePrice = storePrice;
        this.member = member;

        this.highAlch = (int) (storePrice * 0.6);
        this.lowAlch = (int) (storePrice * 0.4);

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBuyPrice() {
        return buy_price;
    }

    public boolean getMember() {
        return member;
    }

    public int getSellPrice() {
        return sell_price;
    }

    public int getStorePrice() {
        return storePrice;
    }

    public int getAlch(boolean high) {
        return high ? highAlch : lowAlch;
    }

    public static ItemDB findItem(String name) {

        for (ItemDB itemDB : item_db) {

            if (itemDB == null)
                continue;

            if (!itemDB.getName().equalsIgnoreCase(name))
                continue;

            return itemDB;

        }
        return null;

    }

    public static void parse(String path) {

        try {


            File file = new File(path);

            JsonElement jsonParser = JsonParser.parseReader(new FileReader(file));

            JsonObject jsonObject = (JsonObject) jsonParser;

            for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {

                if (jsonObject.get(String.valueOf(i)) == null)
                    continue;

                String itemID = String.valueOf(i);

                String name = jsonObject.getAsJsonObject(itemID).get("name").getAsString();
                int id = jsonObject.getAsJsonObject(itemID).get("id").getAsInt();
                int buyPrice = jsonObject.getAsJsonObject(itemID).get("buy_average").getAsInt();
                int sellPrice = jsonObject.getAsJsonObject(itemID).get("sell_average").getAsInt();
                int storePrice = jsonObject.getAsJsonObject(itemID).get("sp").getAsInt();
                boolean member = jsonObject.getAsJsonObject(itemID).get("members").getAsBoolean();

                ItemDB.item_db[i] = new ItemDB(name, id, buyPrice, sellPrice, storePrice, member);

            }

            calender = Calendar.getInstance();
            calender.add(Calendar.MINUTE, 30);

            System.out.println("Finished parsing OSRS item DB!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}