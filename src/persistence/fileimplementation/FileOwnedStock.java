package persistence.fileimplementation;

import business.entities.OwnedStock;
import persistence.interfaces.OwnedStockDao;

import java.util.List;

public class FileOwnedStockDao implements OwnedStockDao {

    private final FileUnitOfWork uow;

    public FileOwnedStockDao(FileUnitOfWork uow)
    {
        this.uow = uow;
    }

    @Override
    public OwnedStock getById(int id)
    {
        for(OwnedStock os : uow.getOwnedStocks())
        {
            if(os.getId() == id)
                return os;
        }

        return null;
    }

    @Override
    public List<OwnedStock> getAll()
    {
        return uow.getOwnedStocks();
    }

    @Override
    public void add(OwnedStock ownedStock)
    {
        uow.getOwnedStocks().add(ownedStock);
    }

    @Override
    public void update(OwnedStock ownedStock)
    {
        List<OwnedStock> list = uow.getOwnedStocks();

        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getId() == ownedStock.getId())
            {
                list.set(i, ownedStock);
                return;
            }
        }

        throw new RuntimeException("OwnedStock not found");
    }

    @Override
    public void delete(int id)
    {
        uow.getOwnedStocks().removeIf(os -> os.getId() == id);
    }
}
