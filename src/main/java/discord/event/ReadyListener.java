package discord.event;


import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

public class ReadyListener implements EventListener {

    @Override
    public void onEvent(@Nonnull GenericEvent event) {

        if (event instanceof ReadyEvent)
            System.out.println("The useless bot is ready for usage!");

    }

}
