package business.stockmarket.simulation;

import java.util.Random;

public class GrowingState implements StockState {

    private static final Random random = new Random();

    @Override
    public double calculatePriceChange(LiveStock stock) {

        double change = random.nextDouble() * 2; // mostly up

        double r = random.nextDouble();

        if (r < 0.10) {
            stock.setState(new StableState());
        }
        else if (r < 0.15) {
            stock.setState(new FallingState());
        }

        return change;
    }

    @Override
    public String getName() {
        return "GROWING";
    }
}