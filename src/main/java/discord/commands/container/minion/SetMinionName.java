package discord.commands.container.minion;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetMinionName extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (user.getMinion() == null)
            user.setMinion( new boolean[] { false, false} );

        if (!user.getMinion()[0]) {
            bot.getChannel().sendMessage("You do not have a minion.").queue();
            return;
        }

        if (cmd.length < 4) {
            bot.getChannel().sendMessage("Error! " + getArguments()).queue();
            return;
        }

        text = new StringBuilder();

        for (int i = 3; i < cmd.length; i++)
            text.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");

        user.setMinionName(text.toString());
        bot.getChannel().sendMessage("Your have renamed your minion to <:minion:718547445985968158> " + user.getMinionName()).queue();

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-m setname" };
    }

    @Override
    public String getDescription() {
        return "Set the name of your minion";
    }

    @Override
    public String getArguments() {
        return "-m setname <name>";
    }

}
