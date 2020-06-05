package discord.commands.container;

import discord.commands.DiscordCommand;
import discord.entity.DiscordUser;
import discord.minigame.daily.DailyQuestion;
import discord.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Daily extends DiscordCommand {

    @Override
    public void execute(EmbedBuilder embed, StringBuilder text, MessageReceivedEvent bot, DiscordUser user, String... cmd) {

        if (user.getDailyCooldown() != null && Calendar.getInstance().getTime().getTime() < user.getDailyCooldown().getTime().getTime()) {
            bot.getChannel().sendMessage("You can claim your next daily in " + Utils.formatTime((user.getDailyCooldown().getTime().getTime() / 1000) - (Calendar.getInstance().getTime().getTime() / 1000))).queue();
            return;
        }

        final DailyQuestion question = DailyQuestion.values()[Utils.random(DailyQuestion.values().length)];

       user.setDailyQuestion(question.getQuestion());
       user.setDailyAnswers(question.getAnswer());

       user.setDailyCooldown(Calendar.getInstance());
       user.getDailyCooldown().add(is24HourLocale() ? Calendar.HOUR_OF_DAY : Calendar.HOUR, 12);


       // user.setAnswerTimer(new SecondsTimer(20));

        bot.getChannel().sendMessage("**Daily Trivia:** " + user.getDailyQuestion()).queue();

        if (question.hasPicture())
            bot.getChannel().sendFile(question.getPicture()).queue();

    }

    @Override
    public String[] getCommand() {
        return new String[] { "-daily" };
    }

    @Override
    public String getDescription() {
        return "Initiates your daily trivia question";
    }

    @Override
    public String getArguments() {
        return null;
    }

    private boolean is24HourLocale() {
        String output = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        return !output.contains(" AM") && !output.contains(" PM");
    }

}
