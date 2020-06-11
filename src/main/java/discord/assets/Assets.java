package discord.assets;

import discord.utils.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {

    public static void generateGP(MessageReceivedEvent bot, int gp) {

        try {

            BufferedImage img = ImageIO.read(new File("./data/discord/assets/image/coins.png"));
            var width = img.getWidth();
            var height = img.getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufferedImage.createGraphics();

            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);
            g.setFont(new Font("OSRSFont", Font.PLAIN, 14));

            if (gp >= 1 && gp <= 99_999)
                g.setColor(Color.YELLOW);
            if (gp >= 100_000 && gp <= 9_999_999)
                g.setColor(Color.WHITE);
            else
                g.setColor(Color.GREEN);

            g.drawString(gp >= 1_000_000 && gp <= 9_999_999 ? String.format("%04d", gp).substring(0, 4) + "K" : Utils.getApproxValue(gp), 10, 15);

            g.dispose();

            File file = new File("./data/discord/assets/image/new_coins.png");
            ImageIO.write(bufferedImage, "png", file);

            bot.getChannel().sendFile(file).queue();

        } catch (IOException e) {

            bot.getChannel().sendMessage("Cannot generate GP image... But! You received **" + Utils.getApproxValue(gp) + " GP!**").queue();

            e.printStackTrace();
        }

    }

}
