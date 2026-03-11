package business.stockmarket.simulation;

import shared.configuration.AppConfig;

public class MarketUpdateThread extends Thread {

    private final StockMarket market;
    private final int updateFrequency;

    public MarketUpdateThread() {
        this.market = StockMarket.getInstance();
        this.updateFrequency =
                AppConfig.getInstance().getUpdateFrequencyInMs();
    }

    @Override
    public void run() {

        while (true) {

            market.updateAllStocks();

            try {
                Thread.sleep(updateFrequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}