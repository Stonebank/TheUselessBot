package discord.event.guild;

import discord.Discord;
import discord.entity.DiscordSave;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnMemberJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        if (!Discord.getBot().isReady())
            return;

        DiscordSave saving = new DiscordSave();
        saving.create(event.getUser());
    }

}
