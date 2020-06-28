package discord.commands.container;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Roll extends DiscordCommand {
    
    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        try {

            if (cmd.length < 2)
                bot.getChannel().sendMessage("You rolled " + Utils.random(1, 100) + " out of 100.").queue();

            if (cmd.length == 2)
                bot.getChannel().sendMessage("You rolled " + Utils.random(1, Integer.parseInt(cmd[1])) + " out of " + cmd[1] + ".").queue();

            if (cmd.length == 3 && Integer.parseInt(cmd[2]) > Integer.parseInt(cmd[1]))
                bot.getChannel().sendMessage("You rolled " + Utils.random(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2])) + " out of " + cmd[2] + ". Min: " + cmd[1] + ", Max: " + cmd[2]).queue();
            else
                bot.getChannel().sendMessage("You entered an argument wrong!").queue();

            if (cmd.length > 3)
                bot.getChannel().sendMessage("⛔Error!⛔\n-You can use -roll to roll between 1 and 100\n-roll max to roll to maximum value \n-roll min max").queue();

        } catch (NumberFormatException e) {

            bot.getChannel().sendMessage("Arguments can only be integer (numbers)").queue();

        }

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-roll" };
    }

    @Override
    public String getDescription() {
        return "Roll between 1-100, 1 to maximum or minimum to maximum";
    }

    @Override
    public String getArguments() {
        return "Arguments can only be integer (numbers)";
    }

}
