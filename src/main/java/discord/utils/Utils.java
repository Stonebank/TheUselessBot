package discord.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class Utils {

    private static final Random RANDOM = new Random();

    public static int random(int maxValue) {
        if (maxValue <= 0)
            return 0;
        return RANDOM.nextInt(maxValue);
    }

    public static int random(int min, int max) {
        final int n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : random(n));
    }

    public static Class<?>[] getClasses(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    public static List<Object> getObjects(String location) {
        List<Object> objects = new ArrayList<>();

        for (File files : Objects.requireNonNull(new File("./build/classes/java/main/" + location.replace(".", "/")).listFiles())) {
            if (files.getName().contains("$"))
                continue;

            try {

                Object event = (Class.forName(location + "." + files.getName().replaceAll(".class", "")).newInstance());
                objects.add(event);

            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        return objects;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add(Class
                            .forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    public static String formatNumber(Object input) {
        return new DecimalFormat("#,###,##0").format(input);
    }

    public static String getApproxValue(long amount) {
        if (amount >= 1_000_000_000L) {
            return (new DecimalFormat(".##").format(amount * 0.000_000_001)).replace(',', '.') + "b";
        } else if (amount >= 1_000_000) {
            return (new DecimalFormat(".#").format(amount * 0.000_001)).replace(',', '.') + "m";
        } else if (amount >= 100_000) {
            return (formatNumber(amount / 1_000)) + "k";
        }
        return formatNumber(amount);
    }

    public static String formatTime(long seconds) {
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        minutes -= hours * 60;
        seconds -= (hours * 60 * 60) + (minutes * 60);
        hours -= days * 24;
        return (days != 0 ? days + " " + (days > 1 ? "days " : "day ") : "")
                + (hours != 0 ? hours + " " + (hours > 1 ? "hours " : "hour ") : "")
                + (minutes != 0 ? minutes + " " + (minutes > 1 ? "minutes " : "minute ") : "")
                + (seconds != 0 ? seconds + " " + (seconds > 1 ? "seconds " : "second ") : "");
    }

    public static int getItemDefinitionsSize() {
        return 30000;
    }
}
