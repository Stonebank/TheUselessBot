package discord.commands.container.kotlin.`fun`

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup
import java.lang.management.ManagementFactory
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class JeffBezos : DiscordCommand() {


    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "Find out how much money Jeff Bezos has made since the bot was launched"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-bezos", "-jeffbezos", "-jeff")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        val uptime = ManagementFactory.getRuntimeMXBean().uptime
        val connection = Jsoup.connect("https://www.forbes.com/profile/jeff-bezos/").userAgent("Mozilla/5.0").get().select("div.profile-info__item-value-container")
        val date = SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse("26-05-2020 00:01:00")

        bot?.channel?.sendMessage("Since the creation of the useless bot, Jeff Bezos has earned $${Utils.formatNumber(TimeUnit.MILLISECONDS.toSeconds(date.time) * 2489.toLong())} USD\nThe bot has been online for ${Utils.formatTime(TimeUnit.MILLISECONDS.toSeconds(uptime))}and Jeff Bezos has earned $${Utils.formatNumber(TimeUnit.MILLISECONDS.toSeconds(uptime) * 2489.toLong())} USD\nTotal networth is ${connection.text()}")?.queue()

    }

}