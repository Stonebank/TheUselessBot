import discord.Discord;
import discord.configuration.Config;

import javax.security.auth.login.LoginException;

public class Launch {

    public static void main(String... args) throws LoginException, InterruptedException {

        Config.loadConfig();

        Discord.setBot(new Discord());
    }

}
