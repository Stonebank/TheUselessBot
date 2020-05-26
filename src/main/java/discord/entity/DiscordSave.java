package discord.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import discord.Discord;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiscordSave {

    public DiscordUser load(User discord) {

        DiscordUser user;

        try (Reader reader = Files.newBufferedReader(Paths.get("./data/discord/user/" + discord.getId() + ".json"), StandardCharsets.UTF_8)) {

            user = new Gson().fromJson(reader, DiscordUser.class);

            return user;

        } catch (IOException e) {

            System.err.println("An error occurred while attempting to load discord user " + discord.getId());
            e.printStackTrace();

            return null;
        }

    }

    public void save(DiscordUser user) {

        try (Writer writer = Files.newBufferedWriter(Paths.get("./data/discord/user/" + user.getId() + ".json"), StandardCharsets.UTF_8)) {

            new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void create(User discord) {

        DiscordUser user = new DiscordUser(discord);

        if (discord.isBot())
            return;

        if (new File("./data/discord/user/" + discord.getId() + ".json").exists())
            return;

        try (Writer writer = Files.newBufferedWriter(Paths.get("./data/discord/user/" + discord.getId() + ".json"), StandardCharsets.UTF_8)) {

            new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(user, writer);

            System.out.println(Discord.class.getSimpleName() + "Created file for " + discord.getAsTag());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
