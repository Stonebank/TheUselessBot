package discord.core.container;

import discord.Discord;
import discord.core.ServiceProcessor;
import discord.core.Timer;

import java.util.concurrent.TimeUnit;

public class TestProcess implements ServiceProcessor {

    @Override
    public Timer getTimer() {
        return new Timer(5, TimeUnit.SECONDS);
    }

    @Override
    public int getDelay() {
        return 5;
    }

    @Override
    public void init() {

        Discord.getBot().sendErrorMessage("hey", Discord.class);

    }

}
