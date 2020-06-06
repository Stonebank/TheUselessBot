package discord.core;

import java.util.concurrent.TimeUnit;

public class Timer {

    private final int delay;
    private final TimeUnit unit;

    public Timer(int delay, TimeUnit unit) {
        this.delay = delay;
        this.unit = unit;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public int getTime() {
        return delay;
    }

    public long getMS() {

        switch (unit) {
            case DAYS:
                return TimeUnit.DAYS.toMillis(delay);
            case HOURS:
                return TimeUnit.MILLISECONDS.toMillis(delay);
            case MINUTES:
                return TimeUnit.MINUTES.toMillis(delay);
            case SECONDS:
                return TimeUnit.SECONDS.toMillis(delay);
            default:
                return delay;
        }

    }

    public String getTimeLeft() {
        return getTime() + " " + getUnit().toString().toLowerCase();
    }

}
