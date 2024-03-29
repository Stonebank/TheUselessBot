package discord.entity.highscore

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class Highscore(val name: String, val bot : MessageReceivedEvent) {

    private val stats = ArrayList<String>()

    fun getRank() : Int {
        return stats[0].split(",".toRegex()).toTypedArray()[0].toInt()
    }

    fun getSkillLevel(skill: Skills): Int {
        return stats[skill.skillIndex].split(",".toRegex()).toTypedArray()[1].toInt()
    }

    fun getSkillExperience(skill: Skills): Long {
        return stats[skill.skillIndex].split(",".toRegex()).toTypedArray()[2].toLong()
    }

    private fun getStats(name: String) {

            try {

                val url = URL("https://secure.runescape.com/m=hiscore_oldschool/index_lite.ws?player=$name")

                val connection = url.openConnection()

                val input = BufferedReader(InputStreamReader(connection.getInputStream()))

                var inputLine : String?

                while (input.readLine().also { inputLine = it } != null)
                    inputLine?.let { stats.add(it) }

                input.close()

            } catch ( e : IOException) {

                bot.channel.sendMessage("ERROR! Could not find $name on the oldschool runescape highscore.").queue()

            }

    }

    init {
        getStats(name)
    }

}