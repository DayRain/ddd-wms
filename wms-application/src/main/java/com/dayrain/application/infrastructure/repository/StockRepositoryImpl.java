package com.dayrain.application.infrastructure.repository;

import com.dayrain.application.infrastructure.dao.StockDao;
import com.dayrain.application.infrastructure.dao.StockLockDao;
import com.dayrain.application.infrastructure.pojo.StockPO;
import com.dayrain.wms.common.utils.BeanUtils;
import com.dayrain.wms.common.utils.CollectionUtils;
import com.dayrain.wms.domain.stock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockRepositoryImpl implements StockRepository {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockLockDao stockLockDao;

    @Override
    public void saveOrUpdate(Stock stock) {
        // save stock
        boolean exist = stockDao.selectById(stock.getId()) == null;
        StockPO stockPO = convertFromEntity(stock);
        if (exist) {
            stockDao.insert(stockPO);
        } else {
            stockDao.updateById(stockPO);
        }

        //handle locks
        stockLockDao.deleteByStockId(stock.getId());
        List<StockLock> locks = stock.getStockLocks();
        if(!CollectionUtils.isEmpty(locks)) {
            for (StockLock lock : locks) {
                stockLockDao.insert(lock);
            }
        }
    }

    @Override
    public Stock findById(String id) {
        StockPO stockPO = stockDao.selectById(id);
        Stock stock = convertFromPO(stockPO);

        return stock;
    }

    private Stock convertFromPO(StockPO stockPO) {
        StockAttribute stockAttribute = new StockAttribute();
        BeanUtils.copy(stockPO, stockAttribute);
        List<StockLock> locks = stockLockDao.getsByStockId(stockPO.getId());
        Stock stock = new Stock(stockPO.getId(), stockPO.getSku(), stockPO.getNumber()
                , stockPO.getWeight(), stockPO.getPrice(), stockPO.getBinId()
                , stockAttribute, new Sn(stockPO.getSn(), stockPO.getId()), locks);
        return stock;
    }

    private StockPO convertFromEntity(Stock stock) {
        StockAttribute stockAttribute = stock.getStockAttribute();
        StockPO stockPO = new StockPO();
        BeanUtils.copy(stock, stockPO);
        BeanUtils.copy(stockAttribute, stockPO);
        return stockPO;
    }
}
