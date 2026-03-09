package shared.logging;

import shared.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Logger logger = Logger.getInstance();

        logger.log("INFO", "Application started");
        logger.log("WARNING", "Stock not found in database");
        logger.log("ERROR", "Failed to save data");

    }
}
