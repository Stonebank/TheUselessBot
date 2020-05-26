package discord.event.message;

import discord.Discord;
import discord.entity.DiscordSave;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnMessageReceived extends ListenerAdapter {

    private final DiscordSave SaveManager = new DiscordSave();

    @Override
    public void onMessageReceived(MessageReceivedEvent bot) {

        if (bot.getAuthor().isBot())
            return;

        if (!Discord.getBot().isReady())
            return;

        DiscordUser user = SaveManager.load(bot.getAuthor());
        SaveManager.save(user);

    }

}
