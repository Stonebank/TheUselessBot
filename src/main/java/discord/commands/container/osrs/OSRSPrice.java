package discord.commands.container.osrs;

import discord.assets.osrsdb.ItemDB;
import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class OSRSPrice extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (cmd.length < 2) {
            bot.getChannel().sendMessage("Error! " + getArguments()).queue();
            return;
        }

        text = new StringBuilder();

        for (int i = 1; i < cmd.length; i++)
            text.append(cmd[i]).append((i == cmd.length - 1) ? "" : " ");

        ItemDB db = ItemDB.findItem(text.toString());

        if (db == null) {
            bot.getChannel().sendMessage("Error! Either untradeable or non-existent item.").queue();
            return;
        }

        embed = new EmbedBuilder();

        embed.setTitle(db.getName() + " (RSBUDDY DATA)").setColor(Utils.getRGB("https://rsbuddy.com/items/" + db.getId() + ".png")).setDescription("Buy price: " + (db.getBuyPrice() > 0 ? Utils.formatNumber(db.getBuyPrice()) + " GP (" + Utils.getApproxValue(db.getBuyPrice()) + ")" : "inactive") + "\nSell price: " + (db.getSellPrice() > 0 ? Utils.formatNumber(db.getSellPrice()) + " GP (" + Utils.getApproxValue(db.getSellPrice()) + ")" : "inactive") + "\nStore price: " + Utils.formatNumber(db.getStorePrice()) + " GP\nHigh alch: " + Utils.formatNumber(db.getAlch(true)) + " GP" + "\nLow Alch: " + Utils.formatNumber(db.getAlch(false)) + " GP" + "\nMembers: " + (db.getMember() ? "Yes" : "No")).setThumbnail("https://rsbuddy.com/items/" + db.getId() + ".png");

        bot.getChannel().sendMessage(embed.build()).queue();

        bot.getChannel().sendMessage("All prices are grabbed from RSBuddy. Update in: " + Utils.formatTimeShort(TimeUnit.MILLISECONDS.toSeconds(ItemDB.calender.getTimeInMillis()) - TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis()), true) + ".\nRSBuddy link: https://rsbuddy.com/exchange?id=" + db.getId() + "&").queue();

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-osrsprice", "-osrsitem", "-finditem", "-price" };
    }

    @Override
    public String getDescription() {
        return "Grabs item information based off RSBuddy.";
    }

    @Override
    public String getArguments() {
        return "Example: -price Blue Partyhat";
    }

}
