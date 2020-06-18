package discord.commands.container.kotlin

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup

class UrbanDir : DiscordCommand() {

    override fun getArguments(): String {
        return "The word you wish to search for. Example: -urban simp"
    }

    override fun getDescription(): String {
        return "Returns the definition of the input word"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-urban")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Argument error! $arguments")?.queue()
            return
        }

        @Suppress("NAME_SHADOWING")
        val text = StringBuilder()

        for (i in 1 until cmd.size)
            text.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        val connection = Jsoup.connect("https://www.urbandictionary.com/define.php?term=$text").userAgent("Mozilla/5.0").get().select("div.meaning")

        for (e in connection) {

            if (e.allElements.eachText()[0].length >= 2000) {
                bot?.channel?.sendMessage("The definition was too long for discord, here is your link: https://www.urbandictionary.com/define.php?term=$text")?.queue()
                return
            }

            bot?.channel?.sendMessage("**Top definition for $text**")?.queue()
            bot?.channel?.sendMessage(e.allElements.eachText()[0])?.queue()
            break

        }

    }

}