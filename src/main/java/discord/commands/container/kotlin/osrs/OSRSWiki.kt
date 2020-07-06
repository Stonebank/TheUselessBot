package discord.commands.container.kotlin.osrs

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup

class OSRSWiki : DiscordCommand() {

    override fun getArguments(): String {
        return ""
    }

    override fun getDescription(): String {
        return ""
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-wiki")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Missing arguments! $arguments")?.queue()
            return
        }

        val embedBuilder = EmbedBuilder()

        val stringBuilder = StringBuilder()
        for (i in 1 until cmd.size)
            stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        val wiki = Jsoup.connect("https://oldschool.runescape.wiki/w/${stringBuilder.replace(" ".toRegex(), "_")}").userAgent("Mozilla/5.0").get()

        embedBuilder.setTitle(wiki.select("h1").text(), "https://oldschool.runescape.wiki/w/${stringBuilder.replace(" ".toRegex(), "_")}").setDescription(wiki.select("p")[1].text()).setThumbnail(wiki.select("img")[2].absUrl("src"))

        embedBuilder.appendDescription("\n\n**The Oldschool RuneScape Wiki**")

        bot?.channel?.sendMessage(embedBuilder.build())?.queue()

    }

}