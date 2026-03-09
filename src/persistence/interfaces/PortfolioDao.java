package persistence.interfaces;

import business.entities.Portfolio;
import java.util.List;

public interface PortfolioDao {

    Portfolio getById(int id);

    List<Portfolio> getAll();

    void add(Portfolio portfolio);

    void update(Portfolio portfolio);

    void delete(int id);

}