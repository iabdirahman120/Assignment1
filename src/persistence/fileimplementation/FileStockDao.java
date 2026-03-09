package persistence.fileimplementation;

import business.entities.Stock;
import persistence.interfaces.StockDao;
import java.util.List;

public class FileStockDao implements StockDao {

    private final FileUnitOfWork uow;

    public FileStockDao(FileUnitOfWork uow)
    {
        this.uow = uow;
    }

    @Override
    public Stock getById(String symbol)
    {
        List<Stock> stocks = uow.getStocks();

        for(Stock stock : stocks)
        {
            if(stock.getSymbol().equals(symbol))
            {
                return stock;
            }
        }

        return null;
    }

    @Override
    public List<Stock> getAll()
    {
        return uow.getStocks();
    }

    @Override
    public void add(Stock stock)
    {
        List<Stock> stocks = uow.getStocks();
        stocks.add(stock);
    }

    @Override
    public void update(Stock stock)
    {
        List<Stock> stocks = uow.getStocks();

        for(int i = 0; i < stocks.size(); i++)
        {
            if(stocks.get(i).getSymbol().equals(stock.getSymbol()))
            {
                stocks.set(i, stock);
                return;
            }
        }

        throw new RuntimeException("Stock not found: " + stock.getSymbol());
    }

    @Override
    public void delete(String symbol)
    {
        List<Stock> stocks = uow.getStocks();

        stocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    }
}
