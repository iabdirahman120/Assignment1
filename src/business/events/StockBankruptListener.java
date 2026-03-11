package business.events;

public interface StockBankruptListener {

    void onStockBankrupt(StockBankruptEvent event);
}