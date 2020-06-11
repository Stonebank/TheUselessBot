package discord.commands.container.admin;

import discord.commands.DiscordCommand;
import discord.commands.DiscordCommandRestrictions;
import discord.entity.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommandRestrictions(discord_id = { "123844064486359042" }  )
public class GiveGP extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (cmd.length < 2) {
            bot.getChannel().sendMessage("Use: -addgp amount").queue();
            return;
        }

        int gp = Integer.parseInt(cmd[1]);

        user.modifyGP(gp, true);

        bot.getChannel().sendMessage("Adding GP: " + gp).queue();

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-givegp", "-addgp" };
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getArguments() {
        return null;
    }

}
