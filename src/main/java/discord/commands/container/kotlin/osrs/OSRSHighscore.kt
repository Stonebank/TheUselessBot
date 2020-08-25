package discord.commands.container.kotlin.osrs

import com.google.common.primitives.Doubles
import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.entity.highscore.Highscore
import discord.entity.highscore.Skills
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jsoup.Jsoup
import java.awt.Color
import java.io.IOException
import kotlin.math.floor


class OSRSHighscore : DiscordCommand() {

    override fun getArguments(): String {
        return "The player name you want to look up, example: -hs Woox"
    }

    override fun getDescription(): String {
        return "Fetch the stats and experience from OSRS highscore"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-stats", "-osrsstats", "-oshighscore", "-highscore", "-hs")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        if (cmd.size < 2) {
            bot?.channel?.sendMessage("Error! $arguments | $description")?.queue()
            return
        }

        val stringBuilder = StringBuilder()

        val embedBuilder = EmbedBuilder()

        for (i in 1 until cmd.size)
            stringBuilder.append(cmd[i]).append(if (i == cmd.size - 1) "" else " ")

        val player = bot?.let { Highscore(stringBuilder.toString(), it) }
        val img : String

        img = try {
            val connection = Jsoup.connect("https://www.runeclan.com/user/$stringBuilder").userAgent("Mozilla/5.0").get()
            connection.select("img").first().absUrl("src")
        } catch (e : IOException) {
            "https://www.runeclan.com/images/gnome.png"
        }

        embedBuilder.setTitle("${player?.name?.capitalize()} (overall rank: ${Utils.formatNumber(player?.getRank())}, ${player?.let { getCombatLevel(it).toInt() }})").setColor(Color((0..255).random(), (0..255).random(), (0..255).random())).setThumbnail(img)

        for (skill in Skills.values())
            embedBuilder.appendDescription("${skill.emoji} ${player?.getSkillLevel(skill)} (XP: ${Utils.formatNumber(player?.getSkillExperience(skill))})\n")

        if (player?.getSkillLevel(Skills.TOTAL)!! < 2277)
            embedBuilder.appendDescription("\n\n**${player.name.capitalize()} is ${Skills.TOTAL.emoji}${2277 - player.getSkillLevel(Skills.TOTAL)} ${if (2277 - player.getSkillLevel(Skills.TOTAL) == 1) "level" else "levels"} away from maxing**")

        for (skill in Skills.values()) {

            if (player.getSkillLevel(skill) == 99 || skill == Skills.TOTAL || embedBuilder.descriptionBuilder.toString().length >= 1900)
                continue

            embedBuilder.appendDescription("\n**${99 - player.getSkillLevel(skill)} ${if (player.getSkillLevel(skill) == 1) "level" else "levels"} in ${skill.emoji} ${skill.name.toLowerCase().capitalize()}**")

        }

        if (embedBuilder.descriptionBuilder.toString().length >= 1900)
            embedBuilder.appendDescription("\n**NOTE: CAN'T DISPLAY MORE, DISCORD ALLOWS 2048 CHARACTERS**")

        bot.channel.sendMessage(embedBuilder.build()).queue()

    }

    private fun getCombatLevel(player: Highscore): Double {
        val base: Double = .25 * (player.getSkillLevel(Skills.DEFENCE) + player.getSkillLevel(Skills.HITPOINTS) + floor((player.getSkillLevel(Skills.PRAYER) / 2).toDouble()))
        val melee: Double = .325 * (player.getSkillLevel(Skills.ATTACK) + player.getSkillLevel(Skills.STRENGTH))
        val range: Double = .325 * (floor((player.getSkillLevel(Skills.RANGED) / 2).toDouble()) + player.getSkillLevel(Skills.RANGED))
        val magic: Double = .325 * (floor((player.getSkillLevel(Skills.MAGIC) / 2).toDouble()) + player.getSkillLevel(Skills.MAGIC))
        return floor(base + Doubles.max(melee, range, magic))
    }

}