package discord.assets;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FontManager {

    public static void registerFont(String... font) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {

            System.out.println("Registered " + Arrays.toString(font) + " font");

            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/discord/assets/font/" + Arrays.toString(font).replace("[","").replace("]","") + ".ttf")));

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

}
