package discord.commands.container.kotlin.minion

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class Minion : DiscordCommand() {

    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "Returns the status of your minion"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-minion", "-m")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser, vararg cmd: String?) {

        if (!user.hasMinion) {
            bot?.channel?.sendMessage("You do not own a minion. Interested? Purchase one! -buyminion")?.queue()
            return
        }

        bot?.channel?.sendMessage("<:minion:718547445985968158> **${user.getMinionName()}** is currently doing nothing! Actions will be added soon.\n\n**Change your minions name!** (-setname <name>)")?.queue()

    }

}