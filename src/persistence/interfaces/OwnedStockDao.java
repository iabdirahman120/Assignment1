package persistence.interfaces;

import business.entities.OwnedStock;
import java.util.List;

public interface OwnedStockDao {

    OwnedStock getById(int id);

    List<OwnedStock> getAll();

    void add(OwnedStock ownedStock);

    void update(OwnedStock ownedStock);

    void delete(int id);

}
