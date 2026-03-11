package business.stockmarket.simulation;

import business.events.StockBankruptEvent;
import business.events.StockBankruptListener;
import business.events.StockUpdateEvent;
import business.events.StockUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class StockMarket {

    private static StockMarket instance;

    private final List<LiveStock> stocks = new ArrayList<>();

    // OBSERVER LISTENERS
    private final List<StockUpdateListener> updateListeners = new ArrayList<>();
    private final List<StockBankruptListener> bankruptListeners = new ArrayList<>();


    private StockMarket() {
    }

    public static StockMarket getInstance() {

        if (instance == null) {
            instance = new StockMarket();
        }

        return instance;
    }

    //  ADD NEW STOCK
    public void addStock(String symbol) {
        stocks.add(new LiveStock(symbol));
    }

    //  ADD EXISTING STOCK (LOAD GAME)
    public void addStock(String symbol, double price, String state) {
        stocks.add(new LiveStock(symbol, price, state));
    }

    // ADD LISTENERS
    public void addUpdateListener(StockUpdateListener listener) {
        updateListeners.add(listener);
    }

    public void addBankruptListener(StockBankruptListener listener) {
        bankruptListeners.add(listener);
    }

    //  NOTIFY METHODS
    private void notifyStockUpdated(LiveStock stock) {

        StockUpdateEvent event =
                new StockUpdateEvent(
                        stock.getSymbol(),
                        stock.getCurrentPrice(),
                        stock.getStateName());

        for (StockUpdateListener listener : updateListeners) {
            listener.onStockUpdated(event);
        }
    }

    private void notifyStockBankrupt(LiveStock stock) {

        StockBankruptEvent event =
                new StockBankruptEvent(stock.getSymbol());

        for (StockBankruptListener listener : bankruptListeners) {
            listener.onStockBankrupt(event);
        }
    }

    //  MARKET TICK
    public void updateAllStocks() {

        for (LiveStock stock : stocks) {

            double oldPrice = stock.getCurrentPrice();

            stock.updatePrice();

            double newPrice = stock.getCurrentPrice();

            // console log (må gerne være)
            System.out.println(
                    stock.getSymbol()
                            + " | "
                            + stock.getStateName()
                            + " | "
                            + oldPrice
                            + " -> "
                            + newPrice
            );

            //  OBSERVER EVENTS
            notifyStockUpdated(stock);

            if (stock.getStateName().equals("BANKRUPT")) {
                notifyStockBankrupt(stock);
            }
        }

        System.out.println("------");
    }
}