package Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Logger {
    private static final Logger instance = new Logger();

    public String logname = "log";
    protected String env = System.getProperty("user.dir");
    private static File logFile;
    private static PrintWriter writer;


    public static Logger getInstance() {
        return instance;
    }

    public static Logger getInstance(String name) {
        instance.logname = name;
        instance.createLogFile();
        return instance;
    }

    public void createLogFile() {
        File logsFolder = new File(env + '/' + "logs");
        if (!logsFolder.exists()) {
            System.err.println("INFO: Creating new logs directory in " + env);
            logsFolder.mkdir();
        }

        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        Calendar cal = Calendar.getInstance();

        logname = logname + '-' + dateFormat.format(cal.getTime()) + ".log";
        Logger.logFile = new File(logsFolder.getName(), logname);
        try {
            if (logFile.createNewFile()) {
                System.err.println("INFO: Creating new log file");
            }
        } catch (IOException e) {
            System.err.println("ERROR: Cannot create log file");
            System.exit(1);
        }
    }

    private Logger() {
        if (instance != null) {
            throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
        }
        this.createLogFile();
    }

    public static void log(String message) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            Calendar cal = Calendar.getInstance();

            FileWriter out = new FileWriter(Logger.logFile, true);
            writer = new PrintWriter(out, true);
            writer.println("[" + dateFormat.format(cal.getTime()) + "] " + message);

            out.close();
        } catch (IOException e) {
            System.err.println("ERROR: Could not write to log file");
        }
    }
}
