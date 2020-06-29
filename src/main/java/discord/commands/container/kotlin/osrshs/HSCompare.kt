package discord.commands.container.kotlin.osrshs

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import discord.entity.highscore.Highscore
import discord.entity.highscore.Skills
import discord.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.Color

class HSCompare : DiscordCommand() {

    override fun getArguments(): String {
        return "Example: -compare lynx_titan zezima"
    }

    override fun getDescription(): String {
        return "Compare two OSRS accounts stats and XP"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-compare")
    }

    override fun execute(a: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String) {

        val player1Name = cmd[1].replace("_".toRegex(), " ")
        val player2Name = cmd[2].replace("_".toRegex(), " ")

        if (player1Name.isBlank() || player2Name.isBlank()) {
            bot?.channel?.sendMessage("You need two players in order to compare. $arguments")
            return
        }

        if (player1Name.equals(player2Name, ignoreCase = true) || player2Name.equals(player1Name, ignoreCase = true)) {
            bot?.channel?.sendMessage("You can't compare the player with the same player.")?.queue()
            return
        }

        val p1 = bot?.let { Highscore(player1Name, it) }
        val p2 = bot?.let { Highscore(player2Name, it) }

        val p1Stats = IntArray(Skills.values().size)
        val p2Stats = IntArray(Skills.values().size)

        val p1XP = LongArray(Skills.values().size)
        val p2XP = LongArray(Skills.values().size)

        val embed = EmbedBuilder().setTitle("${p1?.name} (rank: ${Utils.formatNumber(p1?.getRank())}) vs ${p2?.name} (rank: ${Utils.formatNumber(p2?.getRank())})").setColor(Color((0..255).random(), (0..255).random(), (0..255).random())).setThumbnail("http://www.theoslounge.com/wp-content/uploads/2020/02/skillicon.png")

        for (skill in Skills.values()) {

            p1Stats[skill.ordinal] = p1?.getSkillLevel(skill)!!
            p2Stats[skill.ordinal] = p2?.getSkillLevel(skill)!!

            p1XP[skill.ordinal] = p1.getSkillExperience(skill)
            p2XP[skill.ordinal] = p2.getSkillExperience(skill)

            when {
                p1Stats[skill.ordinal] > p2Stats[skill.ordinal] -> embed.appendDescription("${p1.name} has better stats in ${skill.emoji} ${skill.name.toLowerCase().capitalize()} **(${p1.getSkillLevel(skill)}** vs ${p2.getSkillLevel(skill)})\n")
                p2Stats[skill.ordinal] > p1Stats[skill.ordinal] -> embed.appendDescription("${p2.name} has better stats in ${skill.emoji} ${skill.name.toLowerCase().capitalize()} (${p1.getSkillLevel(skill)} vs **${p2.getSkillLevel(skill)})**\n")
            }

            if (p1Stats[skill.ordinal] == p2Stats[skill.ordinal]) {
                when {
                    p1XP[skill.ordinal] > p2XP[skill.ordinal] -> embed.appendDescription("${p1.name} has better xp in ${skill.emoji} ${skill.name.toLowerCase().capitalize()}\n")
                    p2XP[skill.ordinal] > p1XP[skill.ordinal] -> embed.appendDescription("${p2.name} has better xp in ${skill.emoji} ${skill.name.toLowerCase().capitalize()}\n")
                    else -> embed.appendDescription("Both are equal in ${skill.emoji} ${skill.name.toLowerCase().capitalize()}")
                }
            }

        }

        println(embed.descriptionBuilder.toString().length)

        if (embed.descriptionBuilder.toString().length >= 2048)
            embed.descriptionBuilder.toString().replace("[0-9]".toRegex(), "").replace("(","").replace(")", "")

        bot?.channel?.sendMessage(embed.build())?.queue()

    }

}