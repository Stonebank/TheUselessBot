package discord.commands.container.kotlin

import discord.assets.Assets
import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TestCommand : DiscordCommand() {

    override fun getArguments(): String {
        return "none"
    }

    override fun getDescription(): String {
        return "test"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-bank")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {
        Assets.generateDrop(bot, 10_000_000, 1004)
    }

}