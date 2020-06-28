package discord.commands.container.kotlin

import discord.assets.Hastebin
import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

class Commands : DiscordCommand() {

    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "Generates a list of commands for the server"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-commands", "-help")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        val stringBuilder = StringBuilder()

        getCommands().forEach { (_: Array<String?>?, command: DiscordCommand) -> stringBuilder.append("${Arrays.toString(command.command)} | ${if (command.arguments == null) "None" else command.arguments} | ${command.description} \n\n") }

        bot?.channel?.sendMessage("**This is a list of all commands, in format: command options - arguments - description, total commands: ${getCommands().size}**\n${Hastebin(stringBuilder.toString()).publish(bot)}")?.queue()

    }

}