package discord.event.guild;

import discord.Discord;
import discord.entity.DiscordSave;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnGuildJoin extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        if (!Discord.getBot().isReady())
            return;

        DiscordSave save = new DiscordSave();
        event.getGuild().getMembers().forEach(member -> save.create(member.getUser()));

        assert event.getGuild().getDefaultChannel() != null;
        event.getGuild().getDefaultChannel().sendMessage("Hello @everyone!\n\nTo view commands, please type -commands").queue();

        System.out.println("The useless bot has joined " + event.getGuild().getName());

    }

}
