package persistence.fileimplementation;

import business.entities.*;
import persistence.interfaces.UnitOfWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileUnitOfWork implements UnitOfWork {

    private final String directoryPath;

    private List<Portfolio> portfolios;
    private List<Stock> stocks;
    private List<OwnedStock> ownedStocks;
    private List<Transaction> transactions;
    private List<StockPriceHistory> priceHistory;

    private static final Object FILE_WRITE_LOCK = new Object();

    public FileUnitOfWork(String directoryPath)
    {
        this.directoryPath = directoryPath;
        ensureFilesExist();
    }

    private void ensureFilesExist()
    {
        try
        {
            Files.createDirectories(Paths.get(directoryPath));

            createFileIfMissing("portfolios.txt");
            createFileIfMissing("stocks.txt");
            createFileIfMissing("ownedstocks.txt");
            createFileIfMissing("transactions.txt");
            createFileIfMissing("pricehistory.txt");
        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed to initialize persistence files", e);
        }
    }

    private void createFileIfMissing(String fileName) throws IOException
    {
        var path = Paths.get(directoryPath + "/" + fileName);

        if(!Files.exists(path))
        {
            Files.createFile(path);
        }
    }

    private List<String> readAllLines(String filePath)
    {
        try
        {
            return Files.readAllLines(Paths.get(filePath));
        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed to read from file: " + filePath, e);
        }
    }

    // ---------------- STOCKS ----------------

    public List<Stock> getStocks()
    {
        if(stocks == null)
        {
            stocks = new ArrayList<>();

            List<String> lines = readAllLines(directoryPath + "/stocks.txt");

            for(String line : lines)
            {
                stocks.add(fromPSVStock(line));
            }
        }

        return stocks;
    }

    private String toPSV(Stock stock)
    {
        return stock.getSymbol() + "|" +
                stock.getName() + "|" +
                stock.getCurrentPrice() + "|" +
                stock.getCurrentState();
    }

    private Stock fromPSVStock(String psv)
    {
        String[] parts = psv.split("\\|");

        return new Stock(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                parts[3]
        );
    }

    // ---------------- PORTFOLIOS ----------------

    public List<Portfolio> getPortfolios()
    {
        if(portfolios == null)
        {
            portfolios = new ArrayList<>();

            List<String> lines = readAllLines(directoryPath + "/portfolios.txt");

            for(String line : lines)
            {
                portfolios.add(fromPSVPortfolio(line));
            }
        }

        return portfolios;
    }

    private String toPSV(Portfolio p)
    {
        return p.getId() + "|" + p.getCurrentBalance();
    }

    private Portfolio fromPSVPortfolio(String psv)
    {
        String[] parts = psv.split("\\|");

        return new Portfolio(
                Integer.parseInt(parts[0]),
                Double.parseDouble(parts[1])
        );
    }

    // ---------------- OWNED STOCK ----------------

    public List<OwnedStock> getOwnedStocks()
    {
        if(ownedStocks == null)
        {
            ownedStocks = new ArrayList<>();

            List<String> lines = readAllLines(directoryPath + "/ownedstocks.txt");

            for(String line : lines)
            {
                ownedStocks.add(fromPSVOwnedStock(line));
            }
        }

        return ownedStocks;
    }

    private String toPSV(OwnedStock os)
    {
        return os.getId() + "|" +
                os.getPortfolioId() + "|" +
                os.getStockSymbol() + "|" +
                os.getNumberOfShares();
    }

    private OwnedStock fromPSVOwnedStock(String psv)
    {
        String[] parts = psv.split("\\|");

        return new OwnedStock(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                parts[2],
                Integer.parseInt(parts[3])
        );
    }

    // ---------------- TRANSACTIONS ----------------

    public List<Transaction> getTransactions()
    {
        if(transactions == null)
        {
            transactions = new ArrayList<>();

            List<String> lines = readAllLines(directoryPath + "/transactions.txt");

            for(String line : lines)
            {
                transactions.add(fromPSVTransaction(line));
            }
        }

        return transactions;
    }

    private String toPSV(Transaction t)
    {
        return t.getId() + "|" +
                t.getPortfolioId() + "|" +
                t.getStockSymbol() + "|" +
                t.getType() + "|" +
                t.getQuantity() + "|" +
                t.getPricePerShare() + "|" +
                t.getTotalAmount() + "|" +
                t.getFee() + "|" +
                t.getTimestamp();
    }

    private Transaction fromPSVTransaction(String psv)
    {
        String[] parts = psv.split("\\|");

        return new Transaction(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                Integer.parseInt(parts[4]),
                Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6]),
                Double.parseDouble(parts[7]),
                LocalDateTime.parse(parts[8])
        );
    }

    // ---------------- PRICE HISTORY ----------------

    public List<StockPriceHistory> getPriceHistory()
    {
        if(priceHistory == null)
        {
            priceHistory = new ArrayList<>();

            List<String> lines = readAllLines(directoryPath + "/pricehistory.txt");

            for(String line : lines)
            {
                priceHistory.add(fromPSVPriceHistory(line));
            }
        }

        return priceHistory;
    }

    private String toPSV(StockPriceHistory ph)
    {
        return ph.getId() + "|" +
                ph.getStockSymbol() + "|" +
                ph.getPrice() + "|" +
                ph.getTimestamp();
    }

    private StockPriceHistory fromPSVPriceHistory(String psv)
    {
        String[] parts = psv.split("\\|");

        return new StockPriceHistory(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2]),
                LocalDateTime.parse(parts[3])
        );
    }

    // ---------------- UNIT OF WORK METHODS ----------------

    @Override
    public void beginTransaction()
    {
        clearData();
    }

    @Override
    public void rollback()
    {
        clearData();
    }

    @Override
    public void commit()
    {
        synchronized(FILE_WRITE_LOCK)
        {
            try
            {
                if(stocks != null)
                    writeStocks();

                if(portfolios != null)
                    writePortfolios();

                if(ownedStocks != null)
                    writeOwnedStocks();

                if(transactions != null)
                    writeTransactions();

                if(priceHistory != null)
                    writePriceHistory();
            }
            catch(IOException e)
            {
                throw new RuntimeException("Failed writing persistence files", e);
            }
        }

        clearData();
    }

    private void writeStocks() throws IOException
    {
        List<String> lines = new ArrayList<>();

        for(Stock s : stocks)
            lines.add(toPSV(s));

        Files.write(Paths.get(directoryPath + "/stocks.txt"), lines);
    }

    private void writePortfolios() throws IOException
    {
        List<String> lines = new ArrayList<>();

        for(Portfolio p : portfolios)
            lines.add(toPSV(p));

        Files.write(Paths.get(directoryPath + "/portfolios.txt"), lines);
    }

    private void writeOwnedStocks() throws IOException
    {
        List<String> lines = new ArrayList<>();

        for(OwnedStock os : ownedStocks)
            lines.add(toPSV(os));

        Files.write(Paths.get(directoryPath + "/ownedstocks.txt"), lines);
    }

    private void writeTransactions() throws IOException
    {
        List<String> lines = new ArrayList<>();

        for(Transaction t : transactions)
            lines.add(toPSV(t));

        Files.write(Paths.get(directoryPath + "/transactions.txt"), lines);
    }

    private void writePriceHistory() throws IOException
    {
        List<String> lines = new ArrayList<>();

        for(StockPriceHistory ph : priceHistory)
            lines.add(toPSV(ph));

        Files.write(Paths.get(directoryPath + "/pricehistory.txt"), lines);
    }

    private void clearData()
    {
        portfolios = null;
        stocks = null;
        ownedStocks = null;
        transactions = null;
        priceHistory = null;
    }
}