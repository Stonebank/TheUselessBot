package discord.entity;

import discord.assets.Assets;
import discord.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Calendar;

@Getter @Setter
public class DiscordUser {

    /* The Discord unique ID and Discord tag */
    private final String id;
    private final String discordTag;

    /* The Discord virtual GP */
    public long gp;

    /* Daily questions */
    private String dailyQuestion;
    private String[] dailyAnswers;
    private Calendar dailyCooldown;

    /* Minion */
    public boolean hasMinion;
    public String minionName;

    public DiscordUser(User user) {
        this.id = user.getId();
        this.discordTag = user.getAsTag();
    }

    public void modifyGP(long amount, boolean add) {

        if (add)
            gp += amount;
        else
            gp -= amount;
    }

    public void giveDailyReward(MessageReceivedEvent bot, DiscordSave save, boolean incorrect) {

        setDailyQuestion(null);
        setDailyAnswers(null);

        int gp = incorrect ? Utils.random(1_000_000, 10_000_000) / 2 : Utils.random(1_000_000, 10_000_000);

        bot.getChannel().sendMessage("<:moneybag:718197721495765214>You answered " + (incorrect ? "**incorrectly**" : "**correctly**") + " and received...").queue();

        Assets.generateGP(bot, gp);

        modifyGP(gp, true);

        save.save(this);

    }

    public String getMinionName() {
        return minionName == null ? discordTag.split("#")[0] + "'s minion" : minionName;
    }

}
