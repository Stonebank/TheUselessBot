package discord.event.guild;

import discord.entity.DiscordSave;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnMemberJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        DiscordSave saving = new DiscordSave();
        saving.create(event.getUser());
    }

}
