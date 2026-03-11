package business.events;

public interface StockUpdateListener {

    void onStockUpdated(StockUpdateEvent event);
}