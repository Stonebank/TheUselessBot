package discord.commands.container;


import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GP extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder builder, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {
        bot.getChannel().sendMessage("<:moneybag:718197721495765214>You have " + Utils.formatNumber(user.getGp()) + " GP!").queue();
    }

    @Override
    public String[] getCommand() {
        return new String[] { "-gp", "-coins", "-gold", "-goldpoints" };
    }

    @Override
    public String getDescription() {
        return "Shows the amount of GP you have in your discord account.";
    }

    @Override
    public String getArguments() {
        return null;
    }

}

