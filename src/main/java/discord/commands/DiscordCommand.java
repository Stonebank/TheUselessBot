package discord.commands;

import discord.entity.DiscordUser;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public abstract class DiscordCommand {

    private static final HashMap<String[], DiscordCommand> commands = new HashMap<>();

    public static HashMap<String[], DiscordCommand> getCommands() {
        return commands;
    }

    public static void init() {
        try {

            for (Class<?> c : Utils.getClasses(DiscordCommand.class.getPackage().getName() + ".container")) {

                if (c.isAnonymousClass())
                    continue;

                Object o = c.newInstance();

                boolean isDiscordClass = o instanceof DiscordCommand;

                if (!isDiscordClass)
                    continue;

                if (commands.containsValue(o))
                    continue;

                DiscordCommand discordCommand = (DiscordCommand) o;

                commands.put(discordCommand.getCommand(), discordCommand);

            }

            System.out.println("\nSuccessfully loaded " + commands.size() + " discord commands");

        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public abstract void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd);

    public abstract String[] getCommand();

    public abstract String getDescription();

    public abstract String getArguments();

    public boolean checkAnnotation(Class<?> c, MessageReceivedEvent bot) {
        if (c.isAnnotationPresent(DiscordCommandRestrictions.class)) {

            DiscordCommandRestrictions restrictions = c.getDeclaredAnnotation(DiscordCommandRestrictions.class);
            if (Long.parseLong(restrictions.discord_id()[0].replace("[", "").replace("]", "")) > -1) {
                if (Arrays.stream(restrictions.discord_id()).noneMatch(id -> id.equals(bot.getAuthor().getId()))) {
                    bot.getChannel().sendMessage("You do not have the rights to access this command.").queue();
                    return true;
                }
            }

            if (restrictions.command_disabled()) {
                bot.getChannel().sendMessage("This command has been globally disabled by the bot owner.").queue();
                return true;
            }

        }
        return false;
    }

}
