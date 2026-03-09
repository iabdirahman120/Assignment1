package presentation;

import shared.logging.ConsoleLogOutput;
import shared.logging.Logger;
import shared.logging.LogOutput;

public class RunApp {

    public static void main(String[] args) {

        // create output implementation
        LogOutput consoleOutput = new ConsoleLogOutput();

        // get logger instance
        Logger logger = Logger.getInstance();

        // set output destination
        logger.setOutput(consoleOutput);

        // log some messages
        logger.log("INFO", "Application started");
        logger.log("WARNING", "Stock not found in database");

        try {
            throw new Exception("Database connection failed");
        } catch (Exception exception) {
            logger.log("ERROR", "Failed to save data: " + exception.getMessage());
        }

    }
}
