package discord.configuration;

import java.util.Arrays;
import java.util.List;

public class DiscordConfig {

    public static final String TOKEN = Config.getConfig().getProperty("token");

    public static final boolean DEBUG = Boolean.parseBoolean(Config.getConfig().getProperty("debug"));

    public static List<String> ADMIN = Arrays.asList("123844064486359042", "406185982933598210");

}
