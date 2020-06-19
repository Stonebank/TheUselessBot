package discord.commands.container.kotlin.minion

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class SetNameMinion : DiscordCommand() {

    override fun getArguments(): String {
        return "A name for your bot. -setname Jasser Al Thair"
    }

    override fun getDescription(): String {
        return "Name your bot!"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-setname")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent, user: DiscordUser, vararg cmd: String?) {

        if (!user.hasMinion) {
            bot.channel.sendMessage("You do not own a minion... More information: -minion").queue()
            return
        }

        if (cmd.size < 2) {
            bot.channel.sendMessage("Error! $arguments").queue()
            return
        }

        val stringBuilder = StringBuilder()

        for (i in 1 until cmd.size)
            stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        user.minionName = stringBuilder.toString()
        bot.channel.sendMessage("Renamed your minion to <:minion:718547445985968158> **${user.getMinionName()}**").queue()

    }

}