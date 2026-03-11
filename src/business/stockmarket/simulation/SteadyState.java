package business.stockmarket.simulation;

import java.util.Random;

public class SteadyState implements StockState {

    private static final Random random = new Random();

    @Override
    public double calculatePriceChange(LiveStock stock) {

        double change = random.nextDouble() - 0.5; // -0.5 to +0.5

        // transitions
        double r = random.nextDouble();

        if (r < 0.05) {
            stock.setState(new GrowingState());
        } else if (r < 0.10) {
            stock.setState(new FallingState());
        }

        return change;
    }

    @Override
    public String getName() {
        return "STEADY";
    }
}