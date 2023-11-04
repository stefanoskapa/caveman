package org.caveman.util;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class Logger {

    public enum Level {
        DEBUG, INFO, ERROR, ALL
    }

    private static final Logger instance = new Logger();
    private final Set<Level> enabledLogLevels = EnumSet.noneOf(Level.class);

    private Logger() { }

    public static Logger getInstance() {
        return instance;
    }

    public void setLogLevels(Level... levels) {
        instance.enabledLogLevels.clear();
        instance.enabledLogLevels.addAll(Arrays.asList(levels));
    }


    private boolean shouldLog(Level level) {
        return enabledLogLevels.contains(Level.ALL) || enabledLogLevels.contains(level);
    }

    public void debug(String message) {
        if (shouldLog(Level.DEBUG)) {
            System.out.println("[DEBUG] " + message);
        }
    }

    public void info(String message) {
        if (shouldLog(Level.INFO)) {
            System.out.println("[INFO]  " + message);
        }
    }

    public void error(String message) {
        if (shouldLog(Level.ERROR)) {
            System.err.println("[ERROR] " + message);
        }
    }
}


