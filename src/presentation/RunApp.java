package presentation;

import business.stockmarket.simulation.MarketUpdateThread;
import business.stockmarket.simulation.StockMarket;
import shared.logging.ConsoleLogOutput;
import shared.logging.Logger;
import shared.logging.LogOutput;

public class RunApp {

    public static void main(String[] args) {

        // LOGGER SETUP (behold dette)
        LogOutput consoleOutput = new ConsoleLogOutput();

        Logger logger = Logger.getInstance();
        logger.setOutput(consoleOutput);

        logger.log("INFO", "Application started");


        // ⭐ STOCK MARKET TEST (tilføj dette)
        StockMarket market = StockMarket.getInstance();

        market.addStock("AAPL");
        market.addStock("GOOG");
        market.addStock("MSFT");
        market.addStock("TSLA");

        MarketUpdateThread thread = new MarketUpdateThread();
        thread.start();
    }
}