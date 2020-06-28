package discord.commands.container.kotlin

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class GP : DiscordCommand() {

    override fun execute(builder: EmbedBuilder, text: StringBuilder, bot: MessageReceivedEvent, user: DiscordUser, vararg cmd: String) {
        bot.channel.sendMessage("<:moneybag:718197721495765214> You have ${Utils.formatNumber(user.gp)} GP!").queue()
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-gp", "-coins", "-gold", "-goldpoints")
    }

    override fun getDescription(): String {
        return "Shows the amount of GP you have in your discord account."
    }

    override fun getArguments(): String? {
        return null
    }

}