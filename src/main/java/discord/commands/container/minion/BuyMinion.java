package discord.commands.container.minion;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class BuyMinion extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (user.getMinion() == null)
            user.setMinion(new boolean[] { false, false });

        if (user.getMinion()[0]) {
            bot.getChannel().sendMessage("You already have a minion, you can't have two **yet**").queue();
            return;
        }

        if (user.getGp() < 50_000_000) {
            bot.getChannel().sendMessage("You do not have the 50m GP to buy a minion. You have: " + user.getGp() + " GP.").queue();
            return;
        }

        bot.getChannel().sendMessage("Finding the right minion...").queue(message -> message.editMessage("I think i've got it!").queueAfter(3, TimeUnit.SECONDS, msg -> msg.editMessage("Say hello to your new <:minion:718547445985968158>minion @" + bot.getAuthor().getAsTag()).queue()));
        user.getMinion()[0] = true;

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-buyminion", "-buym" };
    }

    @Override
    public String getDescription() {
        return "Purchase your own minion!";
    }

    @Override
    public String getArguments() {
        return null;
    }

}
