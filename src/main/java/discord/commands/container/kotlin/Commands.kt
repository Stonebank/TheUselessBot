package discord.commands.container.kotlin

import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory
import com.besaba.revonline.pastebinapi.paste.PasteExpire
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity
import discord.commands.DiscordCommand
import discord.configuration.DiscordConfig
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

class Commands : DiscordCommand() {

    override fun getArguments(): String? {
        return null
    }

    override fun getDescription(): String {
        return "Generates a list of commands for the server"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-commands", "-help")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        val factory = PastebinFactory()
        val pastebin = factory.createPastebin(DiscordConfig.PASTEBIN_TOKEN)
        val builder = factory.createPaste()

        val stringBuilder = StringBuilder()

        getCommands().forEach { (_: Array<String?>?, command: DiscordCommand) -> stringBuilder.append("${Arrays.toString(command.command)} | ${if (command.arguments == null) "None" else command.arguments} | ${command.description} \n\n") }

        builder.setTitle("The useless bot commands, link generated for ${bot?.guild?.name}").setRaw(stringBuilder.toString()).setMachineFriendlyLanguage("text").setVisiblity(PasteVisiblity.Public).setExpire(PasteExpire.TenMinutes)

        val paste = builder.build()

        val result = pastebin.post(paste)

        if (result.hasError()) {
            bot?.channel?.sendMessage("Could not generate a link at this time... try again in 5 minutes.")?.queue()
            return
        }

        bot?.channel?.sendMessage("**This is a list of all commands, in format: command options - arguments - description, total commands: ${getCommands().size}**\n${result.get()}")?.queue()

    }

}