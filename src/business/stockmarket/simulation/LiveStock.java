package business.stockmarket.simulation;

import shared.configuration.AppConfig;

public class LiveStock {

    private String symbol;
    private StockState currentState;
    private double currentPrice;

    // new stock
    public LiveStock(String symbol) {
        this.symbol = symbol;
        this.currentPrice =
                AppConfig.getInstance().getStockResetValue();
        this.currentState = new StableState();
    }

    // load from file
    public LiveStock(String symbol, double price, String stateName) {
        this.symbol = symbol;
        this.currentPrice = price;
        this.currentState = createState(stateName);
    }

    public void updatePrice() {

        double change =
                currentState.calculatePriceChange(this);

        currentPrice += change;

        // bankruptcy check
        if (currentPrice <= 0) {
            currentPrice = 0;
            setState(new BankruptState());
        }
    }

    void setState(StockState state) {
        this.currentState = state;
    }

    private StockState createState(String name) {

        switch (name) {
            case "GROWING":
                return new GrowingState();

            case "FALLING":
                return new FallingState();

            case "BANKRUPT":
                return new BankruptState();

            case "STABLE":
            default:
                return new StableState();
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getStateName() {
        return currentState.getName();
    }
}