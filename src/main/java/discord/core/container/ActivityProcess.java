package discord.core.container;

import discord.Discord;
import discord.core.ServiceProcessor;
import discord.core.Timer;
import net.dv8tion.jda.api.entities.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ActivityProcess implements ServiceProcessor {

    private int population;

    @Override
    public Timer getTimer() {
        return new Timer(1, TimeUnit.MINUTES);
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public void init() {

        try {

            final URL osrs = new URL("https://oldschool.runescape.com/");

            final URLConnection conn = osrs.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {

                try (final Stream<String> content = reader.lines()) {

                    content.filter(line -> line.contains("player-count")).findAny().ifPresent(
                            player_count -> population = Integer.parseInt(player_count.replaceAll("[^\\d.]", "")));

                }
            }

            Discord.getBot().getJda().getPresence().setActivity(Activity.playing("with " + population + " OSRS players"));

        } catch (IOException e) {
            Discord.getBot().getJda().getPresence().setActivity(Activity.playing("Error! Couldn't grab OSRS player count..."));
            e.printStackTrace();
        }

    }

}
