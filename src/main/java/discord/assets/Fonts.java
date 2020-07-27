package discord.assets;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Fonts {

    public static void registerFont(String... font) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {

            System.out.println("Registered " + Arrays.toString(font) + " font");

            for (String fontName : font)
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/discord/assets/font/" + fontName)));

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

}
