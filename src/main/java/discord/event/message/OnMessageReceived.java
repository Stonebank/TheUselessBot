package discord.event.message;

import discord.Discord;
import discord.commands.DiscordCommand;
import discord.configuration.DiscordConfig;
import discord.entity.DiscordSave;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnMessageReceived extends ListenerAdapter {

    private final DiscordSave SaveManager = new DiscordSave();

    private EmbedBuilder embed;
    private StringBuilder text;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent bot) {

        if (!Discord.getBot().isReady())
            return;

        if (bot.getAuthor().isBot())
            return;

        if (DiscordConfig.DEBUG)
            System.out.println(String.format("%s said in %s: %s", bot.getAuthor().getAsTag(), bot.getGuild().getName(), bot.getMessage().getContentRaw()));

        String[] cmd = bot.getMessage().getContentRaw().split(" ");

        DiscordUser user = SaveManager.load(bot.getAuthor());
        SaveManager.save(user);

        if (user.getDailyAnswers() != null) {

            for (String answers : user.getDailyAnswers())
                if (bot.getMessage().getContentRaw().equalsIgnoreCase(answers))
                    user.giveDailyReward(bot, SaveManager,false);

        }

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
