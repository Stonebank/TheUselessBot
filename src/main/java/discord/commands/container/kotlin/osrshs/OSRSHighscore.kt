package discord.commands.container.kotlin.osrshs

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.entity.highscore.Highscore
import discord.entity.highscore.Skills
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class OSRSHighscore : DiscordCommand() {

    override fun getArguments(): String {
        return "The player name you want to look up"
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

        embedBuilder.setTitle("${player?.name?.capitalize()} (overall: ${player?.getRank()} | ${player?.getRank()?.toLong()?.let { Utils.getApproxValue(it) }})").setColor(Color((0..255).random(), (0..255).random(), (0..255).random()))

        for (skill in Skills.values())
            embedBuilder.appendDescription("${skill.emoji} ${player?.getSkillLevel(skill)} (XP: ${Utils.formatNumber(player?.getSkillExperience(skill))})\n")

        if (player?.getSkillLevel(Skills.TOTAL)!! < 2277)
            embedBuilder.appendDescription("\n\n**${player.name} is ${2277 - player.getSkillLevel(Skills.TOTAL)} levels away from maxing**")

        for (skill in Skills.values()) {

            if (player.getSkillLevel(skill) == 99 || skill == Skills.TOTAL || embedBuilder.descriptionBuilder.toString().length >= 1900)
                continue

            embedBuilder.appendDescription("\n**${99 - player.getSkillLevel(skill)} levels in ${skill.emoji} ${skill.name.toLowerCase().capitalize()}**")

        }

        if (embedBuilder.descriptionBuilder.toString().length >= 1900)
            embedBuilder.appendDescription("\n**NOTE: CAN'T DISPLAY MORE, DISCORD ALLOWS 2048 CHARACTERS**")

        bot.channel.sendMessage(embedBuilder.build()).queue()

    }

}