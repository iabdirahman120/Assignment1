package presentation;

import business.stockmarket.simulation.MarketUpdateThread;
import business.stockmarket.simulation.StockMarket;
import business.stockmarket.services.StockBankruptService;
import business.stockmarket.services.StockListenerService;
import shared.logging.ConsoleLogOutput;
import shared.logging.Logger;
import shared.logging.LogOutput;

public class RunApp {

    public static void main(String[] args) {

        // LOGGER SETUP
        LogOutput consoleOutput = new ConsoleLogOutput();

        Logger logger = Logger.getInstance();
        logger.setOutput(consoleOutput);

        logger.log("INFO", "Application started");


        // STOCK MARKET SETUP
        StockMarket market = StockMarket.getInstance();

        market.addStock("AAPL");
        market.addStock("GOOG");
        market.addStock("MSFT");
        market.addStock("TSLA");


        // ⭐ SERVICES (Observer)
        StockListenerService listenerService = new StockListenerService();
        StockBankruptService bankruptService = new StockBankruptService();

        market.addUpdateListener(listenerService);
        market.addBankruptListener(bankruptService);


        // START THREAD
        MarketUpdateThread thread = new MarketUpdateThread();
        thread.start();
    }
}