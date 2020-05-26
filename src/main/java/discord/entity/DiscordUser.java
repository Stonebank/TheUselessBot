package discord.entity;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter @Setter
public class DiscordUser {

    /* The Discord unique ID and Discord tag */
    private final String id;
    private final String discordTag;

    /* The Discord virtual GP */
    private long gp;

    public DiscordUser(User user) {
        this.id = user.getId();
        this.discordTag = user.getAsTag();
    }

}
