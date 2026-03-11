package business.stockmarket.services;

import business.events.StockUpdateEvent;
import business.events.StockUpdateListener;

public class StockListenerService implements StockUpdateListener {

    @Override
    public void onStockUpdated(StockUpdateEvent event) {

        System.out.println(
                "Stock updated: "
                        + event.getSymbol()
                        + " price: "
                        + event.getPrice()
                        + " state: "
                        + event.getState()
        );
    }
}
