package business.stockmarket.services;

import business.events.StockBankruptEvent;
import business.events.StockBankruptListener;

public class StockBankruptService implements StockBankruptListener {

    @Override
    public void onStockBankrupt(StockBankruptEvent event) {

        System.out.println(
                "Stock bankrupt: "
                        + event.getSymbol()
        );
    }
}