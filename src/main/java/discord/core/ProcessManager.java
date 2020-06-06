package discord.core;

import discord.utils.Utils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProcessManager {

    private static final ProcessManager INSTANCE = new ProcessManager();

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private final List<Object> services;

    public ProcessManager() {
        services = Utils.getObjects(ProcessManager.class.getPackage().getName() + ".container");
    }

    public static ProcessManager getInstance() {
        return INSTANCE;
    }

    public void init() {
        services.forEach(((service) -> start((ServiceProcessor) service)));
        System.out.println(services.size() + " processes started");
    }

    private void start(final ServiceProcessor processor) {

        service.scheduleAtFixedRate(() -> {
            try {
                processor.init();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, processor.getTimer().getTime(), processor.getTimer().getUnit());

    }

}
