
package persistence.fileimplementation;

import business.entities.Portfolio;
import persistence.interfaces.PortfolioDao;

import java.util.List;

public class FilePortfolioDao implements PortfolioDao {

    private final FileUnitOfWork uow;

    public FilePortfolioDao(FileUnitOfWork uow)
    {
        this.uow = uow;
    }

    @Override
    public Portfolio getById(int id)
    {
        for(Portfolio p : uow.getPortfolios())
        {
            if(p.getId() == id)
                return p;
        }

        return null;
    }

    @Override
    public List<Portfolio> getAll()
    {
        return uow.getPortfolios();
    }

    @Override
    public void add(Portfolio portfolio)
    {
        uow.getPortfolios().add(portfolio);
    }

    @Override
    public void update(Portfolio portfolio)
    {
        List<Portfolio> list = uow.getPortfolios();

        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getId() == portfolio.getId())
            {
                list.set(i, portfolio);
                return;
            }
        }

        throw new RuntimeException("Portfolio not found");
    }

    @Override
    public void delete(int id)
    {
        uow.getPortfolios().removeIf(p -> p.getId() == id);
    }
}