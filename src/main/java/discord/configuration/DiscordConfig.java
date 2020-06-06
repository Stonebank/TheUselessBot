package discord.configuration;

import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jsoup.Jsoup;

import org.jsoup.Connection;
import java.util.Arrays;
import java.util.List;

public class DiscordConfig {

    public static final String TOKEN = Config.getConfig().getProperty("token");

    public static final GatewayIntent[] intent = new GatewayIntent[] { GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGES };

    public static List<String> ADMIN = Arrays.asList("123844064486359042", "406185982933598210");

    public static final boolean DEBUG = Boolean.parseBoolean(Config.getConfig().getProperty("debug"));

    public static final Connection connection = Jsoup.connect(Config.getConfig().getProperty("csgo_stats"));

}
