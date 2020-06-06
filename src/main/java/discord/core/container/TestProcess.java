package discord.core.container;

import discord.core.ServiceProcessor;
import discord.core.Timer;

import java.util.concurrent.TimeUnit;

public class TestProcess implements ServiceProcessor {

    @Override
    public Timer getTimer() {
        return new Timer(5, TimeUnit.SECONDS);
    }

    @Override
    public void init() {

        System.out.println("hey");

    }

}
