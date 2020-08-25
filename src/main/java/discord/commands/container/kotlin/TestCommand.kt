package discord.commands.container.kotlin

import discord.commands.DiscordCommand
import discord.entity.DiscordUser
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.awt.*
import java.io.File
import javax.imageio.ImageIO


class TestCommand : DiscordCommand() {

    override fun getArguments(): String {
        return "none"
    }

    override fun getDescription(): String {
        return "test"
    }

    override fun getCommand(): Array<String> {
        return arrayOf("-test")
    }

    override fun execute(embed: EmbedBuilder?, text: StringBuilder?, bot: MessageReceivedEvent?, user: DiscordUser?, vararg cmd: String?) {

        val bank = ImageIO.read(File("./bank.png"))
        val g = bank.graphics as Graphics2D

        val items = intArrayOf(4734, 4753, 4710, 4714, 13576, 4716, 11832, 11834, 21295)
        var x = 0
        var y = 0

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (item in items.indices) {
            val itemId = ImageIO.read(File("./data/discord/assets/image/items/${items[item]}.png"))
            val amount = (1..10).random()

            if (amount in 0..99_999)
                g.color = Color.YELLOW
            else if (amount in 100_000..9_999_999)
                g.color = Color.WHITE
            else
                g.color = Color.GREEN

            if (item == 0) {
                x += itemId.width / 2 - 3
                y += (32 + itemId.height) / 2 + 15
            } else {
                x += itemId.width / 2 * 2 + 16
            }

            g.font = Font("OSRSFontCompactRegular", Font.PLAIN, 16)
            g.drawString(amount.toString(), x, y + 2)
            g.drawImage(itemId.getScaledInstance(itemId.width, itemId.height, Image.SCALE_SMOOTH), x, y, itemId.width, itemId.height, null)
        }
        g.color = Color.decode("#ff981f")
        g.font = Font("OSRSFontCompactRegular", Font.PLAIN, 20)
        g.drawString("Bank of ${bot?.author?.name?.capitalize()}", bank.width / 2 - 65, 22)

        val file = File("./bank_repeating.png")
        ImageIO.write(bank, "png", file)

        bot?.channel?.sendFile(file)?.queue()

    }

}