package discord.commands.container.kotlin.minion

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class BuyMinion : DiscordCommand() {

    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "Purchase a minion, all you need is 50m GP and a forever caring home."
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-buym", "-buyminion")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser, vararg cmd: String?) {

        if (user.hasMinion) {
            bot?.channel?.sendMessage("You already have a minion, therefore you cannot buy another one **yet**")?.queue()
            return
        }

        if (user.gp < 50_000_000) {
            bot?.channel?.sendMessage("You need 50m GP in order to purchase your own minion")?.queue()
            return
        }

        user.hasMinion = true
        user.modifyGP(50_000_000, false)

        bot?.channel?.sendMessage("Congratulations ${bot.author.name}! You have bought your own minion. Type -minion")?.queue()

    }

}