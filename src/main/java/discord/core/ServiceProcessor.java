package discord.core;

public interface ServiceProcessor {

    Timer getTimer();

    int getDelay();

    void init();


}
