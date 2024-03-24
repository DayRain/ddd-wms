package com.dayrain.wms.domain.stock;

import com.dayrain.wms.common.exception.DomainErrorCode;
import com.dayrain.wms.common.exception.DomainException;
import com.dayrain.wms.common.key.KeyGeneratorFactory;
import com.dayrain.wms.common.utils.CollectionUtils;
import com.dayrain.wms.domain.command.*;
import com.dayrain.wms.domain.common.EventQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dayrain.wms.common.utils.DecimalUtils.is;

@Slf4j
@Getter
public class Stock {

    private String id;

    private String sku;

    private BigDecimal number;

    private BigDecimal weight;

    private BigDecimal price;

    private String binId;

    private LocalDateTime createTime;

    private StockAttribute stockAttribute;

    private Sn sn;

    private List<StockLock> stockLocks;

    public Stock(String id, String sku, BigDecimal number
            , BigDecimal weight, BigDecimal price, String binId
            , StockAttribute stockAttribute, Sn sn, List<StockLock> stockLocks) {
        this.id = id;
        this.sku = sku;
        this.number = number;
        this.weight = weight;
        this.price = price;
        this.binId = binId;
        this.stockAttribute = stockAttribute;
        this.sn = sn;
        this.stockLocks = stockLocks;
        this.createTime = LocalDateTime.now();
    }

    public void stockIn(EventQueue queue, StockInCommand stockInCommand) {

        BigDecimal number = stockInCommand.getNumber();
        BigDecimal price = stockInCommand.getPrice();
        BigDecimal weight = stockInCommand.getWeight();

        addNumber(number);
        addPrice(price);
        addWeight(weight);

        StockMinusEvent stockMinusEvent = StockMinusEvent.builder()
                .stockId(id)
                .sku(sku)
                .stockAttribute(stockAttribute)
                .fromNumber(number)
                .binId(binId)
                .fromPrice(price)
                .fromWeight(weight)
                .minusNumber(number)
                .minusPrice(price)
                .minusWeight(weight)
                .build();
        queue.enqueue(stockMinusEvent);
    }

    public void stockOut(EventQueue queue, StockOutCommand stockOutCommand) {
        BigDecimal outNumber = stockOutCommand.getNumber();
        BigDecimal outPrice = stockOutCommand.getPrice();
        BigDecimal outWeight = stockOutCommand.getWeight();

        BigDecimal formNumber = stockOutCommand.getNumber();
        BigDecimal fromPrice = stockOutCommand.getPrice();
        BigDecimal fromWeight = stockOutCommand.getWeight();

        minusNumber(outNumber);
        minusPrice(outPrice);
        minusWeight(outWeight);

        StockAddedEvent stockAddedEvent = StockAddedEvent.builder()
                .stockId(id)
                .sku(stockOutCommand.getSku())
                .stockAttribute(stockAttribute)
                .fromNumber(formNumber)
                .fromPrice(fromPrice)
                .fromWeight(fromWeight)
                .addedNumber(outNumber)
                .addedPrice(outPrice)
                .addedWeight(outWeight)
                .build();
        queue.enqueue(stockAddedEvent);
    }

    public boolean tryLock(EventQueue queue, StockLockCommand stockLockCommand) {
        BigDecimal lockedNumber = getLockedNumber();
        log.info("try lock stock, stockId:{}, lockNumber:{}, exist number:{}", id, stockLockCommand.getLockNumber(), number);
        if (is(number).lt(stockLockCommand.getLockNumber().add(lockedNumber))) {
            log.error("stock is not enough, stock id {}", id);
            return false;
        }

        LocalDateTime lockTime = LocalDateTime.now();
        StockLock stockLock = new StockLock(KeyGeneratorFactory.getUniqueKey(), id, stockLockCommand.getLockNumber(), stockLockCommand.getOrderCode()
                , lockTime, stockLockCommand.getLockedBy(), stockLockCommand.getLockReason());

        if (CollectionUtils.isEmpty(stockLocks)) {
            stockLocks = new ArrayList<>();
        }
        stockLocks.add(stockLock);

        StockLockEvent stockLockEvent = StockLockEvent.builder()
                .lockId(stockLock.getId())
                .stockId(id)
                .lockNumber(stockLockCommand.getLockNumber())
                .orderCode(stockLockCommand.getOrderCode())
                .lockTime(lockTime)
                .lockedBy(stockLockCommand.getLockedBy()).build();
        queue.enqueue(stockLockEvent);
        return true;
    }

    public void stockUnlock(EventQueue queue, StockUnlockCommand stockUnlockCommand) {
        BigDecimal unlockNumber = stockUnlockCommand.getUnlockNumber();
        String orderCode = stockUnlockCommand.getOrderCode();
        LockReason lockReason = stockUnlockCommand.getLockReason();

        if (!CollectionUtils.isEmpty(stockLocks)) {
            for (StockLock stockLock : stockLocks) {
                boolean matchSuccess = stockLock.isMatch(orderCode, lockReason);
                log.debug("stock lock {}, matches with commands {}, and the result is {}", stockLock, stockUnlockCommand, matchSuccess);

                if (matchSuccess) {
                    if (is(stockLock.getLockNumber()).lt(unlockNumber)) {
                        throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存解锁失败， 被锁定数量不足");
                    }
                    stockLock.setLockNumber(stockLock.getLockNumber().subtract(unlockNumber));

                    //构建解锁的领域事件
                    StockUnlockEvent stockUnlockEvent = StockUnlockEvent.builder()
                            .unlockId(stockLock.getId())
                            .stockId(id)
                            .unlockNumber(unlockNumber)
                            .orderCode(orderCode)
                            .unlockTime(LocalDateTime.now())
                            .lockedBy(stockUnlockCommand.getUnlockedBy()).build();
                    queue.enqueue(stockUnlockEvent);
                    return;
                }
            }
        }
        throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存解锁失败");
    }

    public void attributeUpdate(EventQueue queue, StockAttributeUpdateCommand attributeUpdateCommand) {
        StockAttribute fromAttribute = this.stockAttribute;
        this.stockAttribute = attributeUpdateCommand.getStockAttribute();

        StockAttributeUpdateEvent attributeUpdateEvent = StockAttributeUpdateEvent.builder()
                .stockId(id)
                .sku(sku)
                .fromAttribute(fromAttribute)
                .toAttribute(this.stockAttribute)
                .number(number)
                .build();
        queue.enqueue(attributeUpdateEvent);
    }

    private void addNumber(BigDecimal addedNumber) {
        number = number.add(addedNumber);
    }

    private void addWeight(BigDecimal addedWeight) {
        weight = weight.add(addedWeight);
    }

    private void addPrice(BigDecimal addedPrice) {
        price = price.add(addedPrice);
    }

    private void minusNumber(BigDecimal minusNumber) {
        if (is(minusNumber).gt(number)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存数量不足");
        }
        number = number.subtract(minusNumber);
    }

    private void minusWeight(BigDecimal minusWeight) {
        if (is(minusWeight).gt(weight)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存重量不足");
        }
        weight = weight.subtract(minusWeight);
    }

    private void minusPrice(BigDecimal minusPrice) {
        if (is(minusPrice).gt(price)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存价格不足");
        }
        price = price.subtract(minusPrice);
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
}
