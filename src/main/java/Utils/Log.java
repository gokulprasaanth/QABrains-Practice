package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    // Initialize the Log4j core engine to stamp this specific class name to messages
    private static final Logger logger = LogManager.getLogger(Log.class);
    // By going through LogManager, Log4j configures the logger as per your specifications in the log4j2.xml file.
    // Whereas new logger() won't know where to save files, what format to use or whether to print to the console or not.


    // Info Level: General lifecycle steps (e.g., "Navigating to Login Page")
    public static void info(String message) {
        logger.info(message);
    }

    // Warn Level: Suspicious actions or retry cycles that aren't critical failures yet
    public static void warn(String message) {
        logger.warn(message);
    }

    // Error Level: Test execution step failures or missing elements
    public static void error(String message) {
        logger.error(message);
    }

    // Fatal Level: Critical architecture crashes (e.g., Database or Grid is completely down)
    public static void fatal(String message) {
        logger.fatal(message);
    }

    // Debug Level: Technical data outputs (e.g., API payloads, database query tables)
    public static void debug(String message) {
        logger.debug(message);
    }
    
}
