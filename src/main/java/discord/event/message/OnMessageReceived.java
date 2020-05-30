package discord.event.message;

import discord.commands.DiscordCommand;
import discord.entity.DiscordSave;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnMessageReceived extends ListenerAdapter {

    private final DiscordSave SaveManager = new DiscordSave();

    private EmbedBuilder embed;
    private StringBuilder text;

    @Override
    public void onMessageReceived(MessageReceivedEvent bot) {

        if (bot.getAuthor().isBot())
            return;

        String[] cmd = bot.getMessage().getContentRaw().split(" ");

        DiscordUser user = SaveManager.load(bot.getAuthor());
        SaveManager.save(user);

        DiscordCommand.getCommands().forEach((string, command) -> {

            for (String commands : string) {

                if (cmd[0].equalsIgnoreCase(commands)) {

                    if (command.checkAnnotation(command.getClass(), bot))
                        return;

                    command.execute(embed, text, bot, user, cmd);
                }

            }

        });

        SaveManager.save(user);

    }

}
