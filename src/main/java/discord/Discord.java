package discord;

import discord.configuration.DiscordConfig;
import discord.event.guild.OnGuildJoin;
import discord.event.guild.OnMemberJoin;
import discord.event.message.OnMessageReceived;
import lombok.AccessLevel;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;

import javax.security.auth.login.LoginException;

public class Discord {

    @Getter(AccessLevel.PUBLIC)
    private static Discord bot;

    private final JDA jda;

    public Discord() throws LoginException, InterruptedException {

        jda = new JDABuilder(DiscordConfig.TOKEN).build();

        jda.awaitReady();

        jda.addEventListener(new OnMessageReceived(), new OnGuildJoin(), new OnMemberJoin());

        setActivity();

    }

    public void setActivity() {
        jda.getPresence().setActivity(Activity.listening("Music"));
    }

    public boolean isReady() {
        return jda instanceof ReadyEvent;
    }

}
