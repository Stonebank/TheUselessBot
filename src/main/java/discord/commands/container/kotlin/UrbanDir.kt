package discord.commands.container.kotlin

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup
import java.io.IOException

class UrbanDir : DiscordCommand() {

    override fun getArguments(): String {
        return "The word you wish to search for. Example: -urban simp"
    }

    override fun getDescription(): String {
        return "Returns the definition of the input word"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-urban", "-urbandir")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Argument error! $arguments")?.queue()
            return
        }

        try {

            val stringBuilder = StringBuilder()

            for (i in 1 until cmd.size)
                stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

            val connection = Jsoup.connect("https://www.urbandictionary.com/define.php?term=$stringBuilder").userAgent("Mozilla/5.0").get().select("div.meaning")

            for (e in connection) {

                if (e.allElements.eachText()[0].length >= 2000) {
                    bot?.channel?.sendMessage("The definition was too long for discord (2000 words limit), here is a snippet and your link: https://www.urbandictionary.com/define.php?term=$stringBuilder for the rest.")?.queue()

                    stringBuilder.clear()

                    for (i in 0..1999)
                        stringBuilder.append(e.allElements.eachText()[0].split("")[i])

                    bot?.channel?.sendMessage(stringBuilder)?.queue()

                    return
                }

                bot?.channel?.sendMessage("**Top definition for $stringBuilder**")?.queue()
                bot?.channel?.sendMessage(e.allElements.eachText()[0])?.queue()

                break

            }

        } catch (e : IOException) {

            bot?.channel?.sendMessage("The word could not be found on Urban Dir.")?.queue()

        }

    }

}