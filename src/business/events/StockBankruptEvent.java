package business.events;

public class StockBankruptEvent {

    private final String symbol;

    public StockBankruptEvent(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}