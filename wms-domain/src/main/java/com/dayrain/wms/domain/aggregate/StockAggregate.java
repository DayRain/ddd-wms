package com.dayrain.wms.domain.aggregate;

import com.dayrain.wms.domain.command.*;
import com.dayrain.wms.domain.entity.Stock;
import com.dayrain.wms.domain.entity.StockLock;
import com.dayrain.wms.domain.event.EventQueue;
import com.dayrain.wms.domain.event.stock.StockAddedEvent;

import java.math.BigDecimal;
import java.util.List;

public class StockAggregate implements Aggregate<Stock> {

    private Stock stock;

    private List<StockLock> stockLocks;

    public void stockIn(EventQueue queue, StockInCommand stockInCommand) {

        BigDecimal addedNumber = stockInCommand.getNumber();
        BigDecimal price = stockInCommand.getPrice();
        BigDecimal weight = stockInCommand.getWeight();

        stock.addNumber(addedNumber);
        stock.addPrice(price);
        stock.addWeight(weight);

        StockAddedEvent stockAddedEvent = StockAddedEvent.builder()
                .stockId(stock.getId())
                .sku(stockInCommand.getSku())
                .stockAttribute(stock.getStockAttribute())
                .fromNumber(stock.getNumber())
                .fromPrice(stock.getPrice())
                .fromWeight(stock.getWeight())
                .addedNumber(addedNumber)
                .addedPrice(price)
                .addedWeight(weight)
                .build();
        queue.enqueue(stockAddedEvent);
    }

    public void stockOut(List<EventQueue> queues, StockOutCommand stockOutCommand) {

    }

    public void stockMove(List<EventQueue> queues, StockMoveCommand stockMoveCommand) {

    }

    public void stockLock(List<EventQueue> queues, StockLockCommand stockLockCommand) {

    }

    public void stockUnlock(List<EventQueue> queues, StockUnlockCommand stockUnlockCommand) {

    }

    public void attributeUpdate(List<EventQueue> queues, StockAttributeUpdateCommand attributeUpdateCommand) {

    }

    @Override
    public Stock getRoot() {
        return stock;
    }
}
