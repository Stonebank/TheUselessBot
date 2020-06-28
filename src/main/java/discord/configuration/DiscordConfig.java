package discord.configuration;

import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DiscordConfig {

    public static final String TOKEN = Config.getConfig().getProperty("token");

    public static final String PASTEBIN_TOKEN = Config.getConfig().getProperty("pastebin_token");

    public static final GatewayIntent[] intent = new GatewayIntent[] { GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS };

    public static final Path OSRS_ITEM_DB = Paths.get(Config.getConfig().getProperty("osrs_db_path"));

    public static final boolean DEBUG = Boolean.parseBoolean(Config.getConfig().getProperty("debug"));

    public static final Connection CSGO_STATS = Jsoup.connect(Config.getConfig().getProperty("csgo_stats"));
}
