package discord.core.container;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import discord.assets.osrsdb.ItemDB;
import discord.configuration.DiscordConfig;
import discord.core.ServiceProcessor;
import discord.core.Timer;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

public class OsrsDBProcess implements ServiceProcessor {

    @Override
    public Timer getTimer() {
        return new Timer(30, TimeUnit.MINUTES);
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public void init() {

        try {

            Stopwatch stopwatch = Stopwatch.createStarted();

            InputStream url = new URL("https://rsbuddy.com/exchange/summary.json").openStream();
            Files.copy(url, DiscordConfig.OSRS_ITEM_DB, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Fetched Summary.json from https://rsbuddy.com/exchange/summary.json");

            JsonElement parser = JsonParser.parseReader(new FileReader(DiscordConfig.OSRS_ITEM_DB.toString()));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.write(DiscordConfig.OSRS_ITEM_DB, gson.toJson(parser).getBytes());

            System.out.println("Reformatted " + DiscordConfig.OSRS_ITEM_DB + " to " + gson.getClass().getPackage().getName());

            ItemDB.parse(DiscordConfig.OSRS_ITEM_DB.toString());

            stopwatch.stop();
            System.out.println("Parsed data from RSBuddy in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
