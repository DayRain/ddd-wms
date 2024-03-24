package com.dayrain.wms.domain.stock;

import com.dayrain.wms.common.exception.DomainErrorCode;
import com.dayrain.wms.common.exception.DomainException;
import com.dayrain.wms.domain.command.StockAttributeUpdateCommand;
import com.dayrain.wms.domain.command.StockInCommand;
import com.dayrain.wms.domain.command.StockOutCommand;
import com.dayrain.wms.domain.common.DomainService;
import com.dayrain.wms.domain.common.EventQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.dayrain.wms.common.utils.DecimalUtils.is;

@Slf4j
@Service
public class StockService implements DomainService {

    @Autowired
    private StockFactory stockFactory;

    @Autowired
    private StockRepository stockRepository;

    public void addStock(EventQueue eventQueue, StockInCommand stockInCommand) {
        Stock stock = stockRepository.findById(stockInCommand.getStockId());
        if (stock == null) {
            stock = stockFactory.buildInitStock(stockInCommand.getSku()
                    , stockInCommand.getStockAttribute(), stockInCommand.getBinId());
            log.info("add new stock, {}", stock);
        }
        stock.stockIn(eventQueue, stockInCommand);
        stockRepository.saveOrUpdate(stock);
    }

    public void updateAttribute(EventQueue eventQueue, StockAttributeUpdateCommand stockAttributeUpdateCommand) {
        String stockId = stockAttributeUpdateCommand.getStockId();
        Stock stock = stockRepository.findById(stockId);
        if(stock == null) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存不存在");
        }

        BigDecimal updateNumber = stockAttributeUpdateCommand.getUpdateNumber();
        BigDecimal updatePrice = stockAttributeUpdateCommand.getUpdatePrice();
        BigDecimal updateWeight = stockAttributeUpdateCommand.getUpdateWeight();
        if(is(updateNumber).lt(stock.getValidNumber())) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "可以更改属性的库存不足");
        }

        StockOutCommand stockOutCommand = new StockOutCommand(stockId, updateNumber, updateWeight, updatePrice);
        stockOutCommand.setStockMinusReason(StockMinusReason.ATTRIBUTE_UPDATE);
        stock.stockOut(eventQueue, stockOutCommand);

        Stock toStock = stockFactory.buildInitStock(stock.getSku()
                , stock.getStockAttribute(), stock.getBinId());
        StockInCommand stockInCommand = new StockInCommand(toStock.getId(), updateNumber, updateWeight, updatePrice);
        stock.stockIn(eventQueue, stockInCommand);
    }

}
