package business.entities;

import java.time.LocalDateTime;

public class Transaction {

    private final int id; // PK
    private final int portfolioId; // FK
    private final String stockSymbol; // FK
    private String type;
    private int quantity;
    private double pricePerShare;
    private double totalAmount;
    private double fee;
    private LocalDateTime timestamp;

    public Transaction(int id,
                       int portfolioId,
                       String stockSymbol,
                       String type,
                       int quantity,
                       double pricePerShare,
                       double totalAmount,
                       double fee,
                       LocalDateTime timestamp) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.stockSymbol = stockSymbol;
        this.type = type;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = totalAmount;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getFee() {
        return fee;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}