package discord.commands.container.csgo;

import discord.Discord;
import discord.commands.DiscordCommand;
import discord.configuration.DiscordConfig;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;

public class CSGOGlobalStatistics extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        try {

            embed = new EmbedBuilder().setTitle("CS:GO statistics").setColor(Color.YELLOW).setThumbnail("https://pbs.twimg.com/profile_images/1223028955453607936/JRoa7k5t_400x400.jpg");

            text = new StringBuilder();

            DiscordConfig.connection.userAgent("Mozilla/5.0");

            Document doc = DiscordConfig.connection.get();

            Elements element = doc.select("div.mmStats");

            for (Element e : element)
                text.append(e.text()).append("\n");

            embed.setDescription(text.toString());

            bot.getChannel().sendMessage(embed.build()).queue();

        } catch (IOException e) {
            Discord.getBot().sendErrorMessage("Could not grab CSGO statistics...", CSGOGlobalStatistics.class);
            bot.getChannel().sendMessage("Could not grab latest statistics for CSGO...").queue();
            e.printStackTrace();
        }

    }

    @Override
    public String[] getCommand() {
        return new String[]{"-csgo", "-cs"};
    }

    @Override
    public String getDescription() {
        return "A global statistics for CS:GO.";
    }

    @Override
    public String getArguments() {
        return null;
    }

}
