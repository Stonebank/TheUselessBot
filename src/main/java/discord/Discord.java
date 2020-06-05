package discord;

import discord.assets.Assets;
import discord.commands.DiscordCommand;
import discord.configuration.Config;
import discord.configuration.DiscordConfig;
import discord.event.guild.OnGuildJoin;
import discord.event.guild.OnMemberJoin;
import discord.event.message.OnMessageReceived;
import lombok.AccessLevel;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public class Discord {

    @Getter(AccessLevel.PUBLIC)
    private static Discord bot;

    private final JDA jda;

    public Discord() throws LoginException, InterruptedException {

        jda = new JDABuilder(DiscordConfig.TOKEN).build();

        jda.awaitReady();

        jda.addEventListener(new OnMessageReceived(), new OnGuildJoin(), new OnMemberJoin());

        DiscordCommand.init();

        setActivity();

        Assets.registerFont();

    }

    public void setActivity() {

        String activity = Config.getConfig().getProperty("activity");
        String description = Config.getConfig().getProperty("activity_desc");

        if (activity.isEmpty()) {
            System.err.println("The activity is not set in the config.properties!");
            return;
        }

        if (description.isEmpty()) {
            System.err.println("You must set the bots activity description");
            return;
        }

        switch (activity.toLowerCase()) {

            case "watching":
                jda.getPresence().setActivity(Activity.watching(description));
                break;

            case "listening":
                jda.getPresence().setActivity(Activity.listening(description));
                break;

            case "playing":
                jda.getPresence().setActivity(Activity.playing(description));
                break;

            default:
                jda.getPresence().setActivity(Activity.listening("To some music"));

        }

        System.out.println("Setting activity to " + activity + ": " + description);

    }

    public void sendAdminPrivateMessage(String id, String content) {
        Objects.requireNonNull(jda.getUserById(id)).openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    public void sendErrorMessage(String content, Class<?> source) {
        DiscordConfig.ADMIN.forEach(id -> sendAdminPrivateMessage(id, source.getPackage().getName() + ", " + source.getSimpleName() + ": " + content));
    }

}
