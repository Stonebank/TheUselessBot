package discord.commands.container.kotlin.`fun`

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup

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
        val embedBuilder = EmbedBuilder().setColor(Utils.getRGB(celebrity.select("img").first().absUrl("src"))).setTitle(stringBuilder.toString().capitalize(), "https://www.forbes.com/profile/${stringBuilder.replace(" ".toRegex(), "-").toLowerCase()}/").setThumbnail(celebrity.select("img").first().absUrl("src"))

        embedBuilder.appendDescription("${celebrity.select("div.profile-subheading").text()}, rank: ${celebrity.select("span.profile-heading__rank").text()} \n\n**Networth: ${celebrity.select("div.profile-info__item-value").text()} (real time)**")
        embedBuilder.appendDescription("\n\n${celebrity.select("div.profile-text").text()}\n\n")
        embedBuilder.appendDescription("**Facts**\n")

        val statsTitle = celebrity.select("span.profile-stats__title")
        val statsText = celebrity.select("span.profile-stats__text")

        for (i in 0 until 3)
            embedBuilder.appendDescription("${statsTitle[i].text()}: ${statsText[i].text()}\n")

        bot?.channel?.sendMessage(embedBuilder.build())?.queue()


    }

}