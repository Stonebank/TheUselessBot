package discord.commands.container.osrs;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class OSRSHighscore extends DiscordCommand {


    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        try {

            Connection connection = Jsoup.connect("https://secure.runescape.com/m=hiscore_oldschool/hiscorepersonal.ws?user1=natella").userAgent("Mozilla/5.0");

            Document document = connection.get();

            Elements element = document.select("div.hiscoresHiddenBG");

            System.out.println(element.get(0).text());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-stats" };
    }

    @Override
    public String getDescription() {
        return "Grabs the stats of a player from the OSRS highscores. use -stats zezima";
    }

    @Override
    public String getArguments() {
        return "Player name, usage: -stats zezima";
    }

}
