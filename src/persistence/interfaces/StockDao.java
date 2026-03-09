package persistence.interfaces;

import business.entities.Stock;
import java.util.List;

public interface StockDao {

    Stock getById(String symbol);

    List<Stock> getAll();

    void add(Stock stock);

    void update(Stock stock);

    void delete(String symbol);

}