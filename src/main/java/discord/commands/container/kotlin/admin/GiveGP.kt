package discord.commands.container.kotlin.admin

import discord.commands.DiscordCommand
import discord.commands.DiscordCommandRestrictions
import discord.entity.DiscordUser
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

@DiscordCommandRestrictions(discord_id = ["123844064486359042"])
class GiveGP : DiscordCommand() {

    override fun getArguments(): String {
        return "Argument can only be an integer."
    }

    override fun getDescription(): String? {
        return null
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-addgp", "-addcoins")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2 || cmd.size > 2) {
            bot?.channel?.sendMessage("Error! $arguments")?.queue()
            return
        }

        when (cmd[1]) {

            "random" -> {
                val gp = (0..Integer.MAX_VALUE).random().toLong()
                user?.modifyGP(gp, true)
                bot?.channel?.sendMessage("RANDOM AMOUNT ADDED: ${Utils.formatNumber(gp)} GP")?.queue()
            }

            "max", "max_cash" -> {
                user?.modifyGP(Integer.MAX_VALUE.toLong(), true)
                bot?.channel?.sendMessage("Adding ${Utils.formatNumber(Integer.MAX_VALUE)} GP")?.queue()
            }

            else -> {

                try {

                    cmd[1]?.toLong()?.let { user?.modifyGP(it, true) }
                    bot?.channel?.sendMessage("Adding ${Utils.formatNumber(cmd[1]?.toLong())} GP")?.queue()

                } catch (e: NumberFormatException) {
                    bot?.channel?.sendMessage("Error! $arguments")?.queue()
                }

            }

        }

    }

}
