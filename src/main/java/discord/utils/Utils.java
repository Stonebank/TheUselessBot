package discord.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

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

    public static double random(double min, double max) {
        final double n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : random((int) n));
    }

    public static Class[] getClasses(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) {
        List<Class> classes = new ArrayList<Class>();
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

}
