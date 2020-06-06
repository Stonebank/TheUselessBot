package discord;

import discord.assets.Assets;
import discord.commands.DiscordCommand;
import discord.configuration.Config;
import discord.configuration.DiscordConfig;
import discord.core.ProcessManager;
import discord.entity.DiscordSave;
import discord.event.ReadyListener;
import discord.event.guild.OnGuildJoin;
import discord.event.guild.OnMemberJoin;
import discord.event.message.OnMessageReceived;
import lombok.AccessLevel;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Objects;

public class Discord {

    @Getter(AccessLevel.PUBLIC)
    private static Discord bot;

    @Getter
    private final JDABuilder jdaBuilder = JDABuilder.create(DiscordConfig.TOKEN, Arrays.asList(DiscordConfig.intent));

    @Getter(AccessLevel.PUBLIC)
    private final JDA jda = jdaBuilder.addEventListeners(new ReadyListener()).build();

    public Discord() throws LoginException, InterruptedException {

        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS);

        jda.awaitReady();

        jda.setAutoReconnect(true);

        jda.addEventListener(new OnMessageReceived(), new OnGuildJoin(), new OnMemberJoin());
        System.out.println("Registered following events");
        jda.getEventManager().getRegisteredListeners().forEach(System.out::println);

        DiscordCommand.init();

        Assets.registerFont();

        registerMembers();

        setActivity();

        ProcessManager.getInstance().init();

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

    public void registerMembers() {

        DiscordSave save = new DiscordSave();

        for (Guild guild : jda.getGuilds()) {

            if (guild == null)
                continue;

            for (Member member : guild.getMembers()) {

                if (member == null || member.getUser().isBot())
                    continue;

                save.create(member.getUser());

            }

        }

    }

    public boolean isReady() {
        return jda.getStatus().isInit();
    }

}
