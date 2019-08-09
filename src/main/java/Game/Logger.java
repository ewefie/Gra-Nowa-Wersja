package Game;

import javax.swing.*;

public final class Logger extends JTextArea {
    private static final Logger instance = new Logger();
    private final static String newline = "\n";

    public String logname = "log";

    public static Logger getInstance() {
        return instance;
    }

    public static Logger getInstance(String name) {
        instance.logname = name;
        return instance;
    }

    private Logger() {
        if (instance != null) {
            throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
        }
    }

    public static void log(String message) {
        getInstance().append(message + newline);
    }
}
