package business.entities;

public class Portfolio {

    private final int id; // PK
    private double currentBalance;

    public Portfolio(int id, double currentBalance) {
        this.id = id;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
