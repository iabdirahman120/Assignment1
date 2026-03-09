package shared.configuration;

public class AppConfig {

    private static AppConfig instance;

    private final int startingBalance;
    private final double transactionFee;
    private final int updateFrequencyInMs;
    private final double stockResetValue;

    private AppConfig() {

        startingBalance = 10000;
        transactionFee = 5.0;
        updateFrequencyInMs = 10;
        stockResetValue = 100.0;

    }

    public static AppConfig getInstance() {

        if (instance == null) {
            instance = new AppConfig();
        }

        return instance;
    }

    public int getStartingBalance() {
        return startingBalance;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public int getUpdateFrequencyInMs() {
        return updateFrequencyInMs;
    }

    public double getStockResetValue() {
        return stockResetValue;
    }

}