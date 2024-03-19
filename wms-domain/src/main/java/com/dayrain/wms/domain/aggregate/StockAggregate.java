package com.dayrain.wms.domain.aggregate;

import com.dayrain.wms.common.key.KeyGeneratorFactory;
import com.dayrain.wms.common.utils.CollectionUtils;
import com.dayrain.wms.domain.command.*;
import com.dayrain.wms.domain.entity.Stock;
import com.dayrain.wms.domain.entity.StockLock;
import com.dayrain.wms.domain.event.EventQueue;
import com.dayrain.wms.domain.event.stock.StockAddedEvent;
import com.dayrain.wms.domain.event.stock.StockLockEvent;
import com.dayrain.wms.domain.event.stock.StockMinusEvent;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.dayrain.wms.common.utils.DecimalUtils.is;

@Slf4j
public class StockAggregate implements Aggregate<Stock> {

    private final Stock stock;

    private List<StockLock> stockLocks;

    public StockAggregate(Stock stock, List<StockLock> stockLocks) {
        this.stock = stock;
        this.stockLocks = stockLocks;
    }

    public void stockIn(EventQueue queue, StockInCommand stockInCommand) {

        BigDecimal number = stockInCommand.getNumber();
        BigDecimal price = stockInCommand.getPrice();
        BigDecimal weight = stockInCommand.getWeight();

        stock.addNumber(number);
        stock.addPrice(price);
        stock.addWeight(weight);

        StockMinusEvent stockMinusEvent = StockMinusEvent.builder()
                .stockId(stock.getId())
                .sku(stockInCommand.getSku())
                .stockAttribute(stock.getStockAttribute())
                .fromNumber(stock.getNumber())
                .fromPrice(stock.getPrice())
                .fromWeight(stock.getWeight())
                .minusNumber(number)
                .minusPrice(price)
                .minusWeight(weight)
                .build();
        queue.enqueue(stockMinusEvent);
    }

    public void stockOut(EventQueue queue, StockOutCommand stockOutCommand) {
        BigDecimal number = stockOutCommand.getNumber();
        BigDecimal price = stockOutCommand.getPrice();
        BigDecimal weight = stockOutCommand.getWeight();

        stock.minusNumber(number);
        stock.minusPrice(price);
        stock.minusWeight(weight);

        StockAddedEvent stockAddedEvent = StockAddedEvent.builder()
                .stockId(stock.getId())
                .sku(stockOutCommand.getSku())
                .stockAttribute(stock.getStockAttribute())
                .fromNumber(stock.getNumber())
                .fromPrice(stock.getPrice())
                .fromWeight(stock.getWeight())
                .addedNumber(number)
                .addedPrice(price)
                .addedWeight(weight)
                .build();
        queue.enqueue(stockAddedEvent);
    }

    public boolean tryLock(EventQueue queue, StockLockCommand stockLockCommand) {
        BigDecimal lockedNumber = getLockedNumber();
        log.info("try lock stock, stockId:{}, lockNumber:{}, exist number:{}", stock.getId(), stockLockCommand.getLockNumber(), stock.getNumber());
        if (is(stock.getNumber()).lt(stockLockCommand.getLockNumber().add(lockedNumber))) {
            log.error("stock is not enough, stock info {}", stock);
            return false;
        }

        LocalDateTime lockTime = LocalDateTime.now();
        StockLock stockLock = new StockLock(KeyGeneratorFactory.getUniqueKey(), stock.getId(), stockLockCommand.getLockNumber(), stockLockCommand.getOrderCode()
                , lockTime, stockLockCommand.getLockedBy(), stockLockCommand.getLockReason());

        stockLocks.add(stockLock);

        StockLockEvent stockLockEvent = StockLockEvent.builder()
                .lockId(stockLock.getId())
                .stockId(stock.getId())
                .lockNumber(stockLockCommand.getLockNumber())
                .orderCode(stockLockCommand.getOrderCode())
                .lockTime(lockTime)
                .lockedBy(stockLockCommand.getLockedBy()).build();
        queue.enqueue(stockLockEvent);
        return true;
    }

    private BigDecimal getLockedNumber() {
        BigDecimal sum = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(stockLocks)) {
            for (StockLock stockLock : stockLocks) {
                sum = sum.add(stockLock.getLockNumber());
            }
        }
        return sum;
    }

    public void stockUnlock(EventQueue queue, StockUnlockCommand stockUnlockCommand) {

    }

    public void attributeUpdate(EventQueue queue, StockAttributeUpdateCommand attributeUpdateCommand) {

    }

    @Override
    public Stock getRoot() {
        return stock;
    }
}
