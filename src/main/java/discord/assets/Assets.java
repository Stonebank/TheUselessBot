package discord.assets;

import discord.utils.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {

    public static void generateDrop(MessageReceivedEvent bot, int quantity, int... items) {

        try {

            BufferedImage img = ImageIO.read(new File("./data/discord/assets/image/bank/1.jpg"));
            Graphics2D g = img.createGraphics();
            g.setFont(new Font("OSRSFont", Font.BOLD, 16));
            g.setColor(Color.decode("#ff981f"));
            g.drawString(bot.getAuthor().getName() + "'s bank - Page 1 of 1 (value: Fuck your dad Jasser)", 65, 21);

            int x = 20;
            int y = 50;
            int itemCount = 0;
            int multiplier = 1;

            for (int item : items) {
                BufferedImage itemPNG = ImageIO.read(new File("./data/discord/assets/image/items/" + item + ".png"));
                g.setFont(new Font("OSRSFont", Font.TRUETYPE_FONT, 14));
                g.setColor(Color.YELLOW);
                if (quantity >= 1 && quantity <= 99_999)
                    g.setColor(Color.YELLOW);
                if (quantity >= 100_000 && quantity <= 9_999_999)
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.GREEN);
                g.drawString(quantity >= 1_000_000 && quantity <= 9_999_999 ? String.format("%04d", quantity).substring(0, 4) + "K" : Utils.getApproxValue(quantity), x - 3, y + 5);
                g.drawImage(itemPNG.getScaledInstance(itemPNG.getWidth(), itemPNG.getHeight(), Image.SCALE_SMOOTH), x, y, itemPNG.getWidth(), itemPNG.getHeight(), null);
                x += 60;
                itemCount++;
                if (itemCount > 0 && itemCount % 8 == 0) {
                    multiplier++;
                    x = 20;
                    y = 50 * multiplier;
                }
            }

            File file = new File("./data/discord/assets/image/bank/bank_1.png");
            ImageIO.write(img, "png", file);

            bot.getChannel().sendFile(file).queue();

            g.dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
