package discord.configuration;

import lombok.AccessLevel;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    @Getter(AccessLevel.PUBLIC) private static final Properties config = new Properties();

    public static void loadConfig() {

        File file = new File("./config.properties");

        if (!file.exists()) {
            System.err.println("Config file could not be found! Please create config.properties in root folder and define properties");
            return;
        }

        try (FileReader fileReader = new FileReader(file)) {

            config.load(fileReader);

        } catch (IOException e) {

            System.err.println("Something went wrong loading the config.properties file!");
            e.printStackTrace();

        }

    }

}
