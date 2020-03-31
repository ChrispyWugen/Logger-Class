import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Class to log messages and errors to a logfile
 */
public class DO_Logger {

    private static Logger logger = null;

    public static synchronized Logger getInstance(Configuration config) {
        if (logger == null) {
            logger = Logger.getLogger("EF-Audit-Trail-Logger");
            try {
                String filename = getFileName();
                Handler handler = new FileHandler(filename);
                logger.addHandler(configureOutputHandler(handler));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    /**
     * Creates a filename String from a timestamp
     *
     * @return String filename of the current log file containing timestamp
     */
    private static String getFileName() {
        String fileName = "";

        //Check if log directories exist, else create them
        File mainLogsDirectory = new File(Configuration.getInstance().getPathToCSV() + "\\Logs\\");
        if (mainLogsDirectory.exists() == false) {
            mainLogsDirectory.mkdir();
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        fileName = fileName.concat(mainLogsDirectory.toString() + "\\" + timestamp.toString() + ".txt");

        return fileName;
    }

    /**
     *  Configures the logger output settings and adds formatter to the given Handler object
     * @return Handler object
     */
    private static synchronized Handler configureOutputHandler(Handler handler){

        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tc %2$s%n%4$s: %5$s%6$s%n");
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);

        return handler;
    }

}
