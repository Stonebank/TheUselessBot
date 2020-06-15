package discord.commands.container.kotlin

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class Test : DiscordCommand() {

    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "just a test"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-test", "-jasser")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {
        bot?.channel?.sendMessage("this message was coded with kotlin")?.queue()
    }

}