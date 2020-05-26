package discord.event.guild;

import discord.entity.DiscordSave;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnGuildJoin extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        DiscordSave save = new DiscordSave();
        event.getGuild().getMembers().forEach(member -> save.create(member.getUser()));

        assert event.getGuild().getDefaultChannel() != null;
        event.getGuild().getDefaultChannel().sendMessage("Hello @everyone!").queue();

    }

}
