package business.stockmarket.simulation;

public class BankruptState implements StockState {

    private int ticks = 0;

    @Override
    public double calculatePriceChange(LiveStock stock) {

        ticks++;

        if (ticks >= 5) {
            stock.setState(new StableState());
        }

        return 0;
    }

    @Override
    public String getName() {
        return "BANKRUPT";
    }
}