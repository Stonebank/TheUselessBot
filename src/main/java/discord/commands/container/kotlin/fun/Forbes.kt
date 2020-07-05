package discord.commands.container.kotlin.`fun`

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup
import java.awt.Color

class Forbes : DiscordCommand() {

    override fun getArguments(): String {
        return "Name you wish to look up, example: -networth Jeff Bezos"
    }

    override fun getDescription(): String {
        return "Find out the networth of a celebrity in realtime!"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-networth", "-forbes")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Missing argument! $arguments")?.queue()
            return
        }

        val stringBuilder = StringBuilder()
        for (i in 1 until cmd.size)
            stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        val celebrity = Jsoup.connect("https://www.forbes.com/profile/${stringBuilder.replace(" ".toRegex(), "-").toLowerCase()}/").userAgent("Mozilla/5.0").get()

        if (celebrity == null) {
            bot?.channel?.sendMessage("Could not find ${stringBuilder.toString().capitalize()} on Forbes.")?.queue()
            return
        }

        val embedBuilder = EmbedBuilder().setColor(Color.GREEN).setTitle("${stringBuilder.toString().capitalize()}, rank: ${celebrity.select("span.profile-heading__rank").text()}").setThumbnail(celebrity.select("img").first().absUrl("src"))

        embedBuilder.appendDescription("**Networth: ${celebrity.select("div.profile-info__item-value").text()} (real time)**")
        embedBuilder.appendDescription("\n\n${celebrity.select("div.profile-text").text()}")

        bot?.channel?.sendMessage(embedBuilder.build())?.queue()

    }

}