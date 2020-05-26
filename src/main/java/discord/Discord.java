package discord;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Discord {

    @Getter
    private static Discord bot;

    private final JDA jda;

    public Discord() throws LoginException, InterruptedException {

        jda = new JDABuilder("NzE0NzYyMjQxOTgzMTE5Mzkw.Xszh4Q.GmVRKLhfn1qxryn4VX9tHNi68so").build();

        jda.awaitReady();

    }

}
