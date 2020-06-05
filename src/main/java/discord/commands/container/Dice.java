package discord.commands.container;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Dice extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (user.getGp() == 0) {
            bot.getChannel().sendMessage("<:sadpepega:718351007024807956>You do not have any GP to gamble!").queue();
            return;
        }

        if (cmd[1].toLowerCase().contains("k"))
            cmd[1] = cmd[1].replace("k", "000");

        else if (cmd[1].toLowerCase().contains("m"))
            cmd[1] = cmd[1].replace("m", "000000");

        else if (cmd[1].toLowerCase().contains("b"))
            cmd[1] = cmd[1].replace("b", "000000000");

        else if (!cmd[1].toLowerCase().contains("k") || !cmd[1].toLowerCase().contains("m") || !cmd[1].toLowerCase().contains("b")) {
            cmd[1] = cmd[1].replaceAll("[^0-9]+", "");
            bot.getChannel().sendMessage("<:sadcat:661285485670301697>You can use 'k', 'm' and 'b'").queue();
        }

        if (Long.parseLong(cmd[1]) > user.getGp()) {
            bot.getChannel().sendMessage("Not so fast! You have " + Utils.formatNumber(user.getGp()) + " GP.").queue();
            return;
        }

        if (Long.parseLong(cmd[1]) > 2_000_000_000) {
            bot.getChannel().sendMessage("You can only gamble up to 2B at a time.").queue();
            return;
        }

        embed = new EmbedBuilder().setTitle("Dice roll").setColor(Color.ORANGE).setThumbnail("https://i.imgur.com/sySQkSX.png");

        int roll = Utils.random(1, 100);

        long gp = roll == 73 ? (long) (Long.parseLong(cmd[1]) * 1.73) : Long.parseLong(cmd[1]);

        embed.setDescription("You rolled **" + roll + "** on the percentile dice and " + (roll > 54 ? "won " : "lost ") + (roll > 54 ? "" : "-") + Utils.getApproxValue(gp) + " GP.");
        user.modifyGP(gp, roll > 54);

        bot.getChannel().sendMessage(embed.build()).queue();

    }

    @Override
    public String[] getCommand() {
        return new String[]{"-gamble", "-dice"};
    }

    @Override
    public String getDescription() {
        return "Gamble your money away! Just kidding, we do not encourage gambling but the option is there!";
    }

    @Override
    public String getArguments() {
        return null;
    }

}
