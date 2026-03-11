package business.stockmarket.simulation;

import java.util.ArrayList;
import java.util.List;

public class StockMarket {

    private static StockMarket instance;

    private List<LiveStock> stocks = new ArrayList<>();

    private StockMarket() {
    }

    public static StockMarket getInstance() {
        if (instance == null) {
            instance = new StockMarket();
        }
        return instance;
    }

    // add NEW stock by symbol
    public void addStock(String symbol) {
        stocks.add(new LiveStock(symbol));
    }

    // add EXISTING stock (when loading game)
    public void addStock(String symbol, double price, String state) {
        stocks.add(new LiveStock(symbol, price, state));
    }

    // simulation tick
    public void updateAllStocks() {

        for (LiveStock s : stocks) {

            double oldPrice = s.getCurrentPrice();

            s.updatePrice();

            double newPrice = s.getCurrentPrice();

            System.out.println(
                    s.getSymbol()
                            + " | "
                            + s.getStateName()
                            + " | "
                            + oldPrice
                            + " -> "
                            + newPrice
            );
        }

        System.out.println("------");
    }
}