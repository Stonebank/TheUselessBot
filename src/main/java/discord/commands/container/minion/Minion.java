package discord.commands.container.minion;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Minion extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (user.getMinion() == null)
            user.setMinion(new boolean[] { false, false });

        if (!user.getMinion()[0]) {
            bot.getChannel().sendMessage("You do not have a minion! Purchase one today by doing -buyminion or -buym").queue();
            return;
        }

        bot.getChannel().sendMessage(user.getMinion()[1] ? "<:minion:718547445985968158>Minion_name is currently doing whatever... (TODO)" : "<:minion:718547445985968158>Minion_name is currently doing nothing. Commands:\n-setname <name>\nTODO MORE COMMANDS!").queue();

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-minion", "-m" };
    }

    @Override
    public String getDescription() {
        return "Check what your minion is currently doing.";
    }

    @Override
    public String getArguments() {
        return null;
    }

}
