package discord.configuration;

public class DiscordConfig {

    public static final String TOKEN = Config.getConfig().getProperty("token");

    public static final boolean DEBUG = Boolean.parseBoolean(Config.getConfig().getProperty("debug"));

}
