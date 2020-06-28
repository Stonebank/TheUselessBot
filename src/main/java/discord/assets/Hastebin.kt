package discord.assets

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

class Hastebin(private val text: String) {

    fun publish(bot : MessageReceivedEvent) : String? {

        try {

            val textData = text.toByteArray(StandardCharsets.UTF_8)
            val length = textData.size

            val hastebinURL = "https://hastebin.com/documents"
            val url = URL(hastebinURL)
            val connection = url.openConnection() as HttpsURLConnection

            connection.doOutput = true
            connection.instanceFollowRedirects = true
            connection.requestMethod = "POST"
            connection.setRequestProperty("User-Agent", "The useless bot (a discord bot)")
            connection.setRequestProperty("Content-Length", length.toString())
            connection.useCaches = false

            val data = DataOutputStream(connection.outputStream)
            data.write(textData)

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var response = reader.readLine()

            if (response.contains("\"key\"")) {
                response = response.substring(response.indexOf(":") + 2, response.length - 2)
                response = "https://hastebin.com/$response"
            }

            return response

        } catch (e : IOException) {
            bot.channel.sendMessage("Could not generate a link, try again in 5 minutes...").queue()
            return null
        }

    }

}