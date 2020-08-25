package discord.commands.container.kotlin.osrs

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.entity.highscore.Skills
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.awt.Color
import java.io.IOException

class HSRecentActivity : DiscordCommand() {

    override fun getArguments(): String {
        return "The Oldschool RuneScape account name that you wish to look up."
    }

    override fun getDescription(): String {
        return "Returns a list of recent levels and/or achievements obtained by the player."
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-recentfive", "-5", "-activity")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Error! $arguments | $description")?.queue()
            return
        }

        val stringBuilder = StringBuilder()
        val embedBuilder = EmbedBuilder()
        val img: String?
        val activity: Document?

        for (i in 1 until cmd.size)
            stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        try {
            activity = Jsoup.connect("https://oldschool.runeclan.com/xp-tracker/user/$stringBuilder").userAgent("Mozilla/5.0").get()
        } catch (e : IOException) {
            bot?.channel?.sendMessage("Could not find player $stringBuilder, try again...")
            return
        }

        img = try {
            activity.select("img").first().absUrl("src")
        } catch (e : IOException) {
            "https://www.runeclan.com/images/gnome.png"
        }

        embedBuilder.setTitle(stringBuilder.toString().capitalize(), "https://oldschool.runeclan.com/xp-tracker/user/$stringBuilder").setThumbnail(img).setColor(Color.YELLOW)
        embedBuilder.appendDescription("**Recent ${activity.select("div.xp_tracker_activity_div").size} activity**\n\n")

        for (element in activity.select("div.xp_tracker_activity_div")) {

            if (element == null)
                continue

            for (skill in Skills.values()) {

                if (element.text().toLowerCase().contains(skill.name.toLowerCase()))
                    embedBuilder.appendDescription("${skill.emoji} ${element.text()}\n")

            }

        }

        bot?.channel?.sendMessage(embedBuilder.build())?.queue()

    }

}