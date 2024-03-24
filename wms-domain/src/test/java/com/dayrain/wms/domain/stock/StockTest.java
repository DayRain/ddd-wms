package com.dayrain.wms.domain.stock;

import com.dayrain.wms.common.exception.DomainException;
import com.dayrain.wms.common.key.KeyGeneratorFactory;
import com.dayrain.wms.domain.command.*;
import com.dayrain.wms.domain.common.EventQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class StockTest {

    private Stock buildEmptyStock() {
        StockAttribute stockAttribute = new StockAttribute(StockStatus.NORMAL);
        Stock stock = new Stock(KeyGeneratorFactory.getUniqueKey(), "001", BigDecimal.ZERO
                , BigDecimal.ZERO, BigDecimal.ZERO, "B001", stockAttribute, null, null);
        return stock;
    }

    private Stock buildStockOfNumber(BigDecimal stockNumber, BigDecimal weight, BigDecimal price) {
        StockAttribute stockAttribute = new StockAttribute(StockStatus.NORMAL);
        Stock stock = new Stock(KeyGeneratorFactory.getUniqueKey(), "001", stockNumber
                , weight, price, "B001", stockAttribute, null, null);
        return stock;
    }

    @Test
    void testStockIn() {
        Stock stock = buildEmptyStock();

        StockInCommand stockInCommand = new StockInCommand("001", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        EventQueue eventQueue = new TestEventQueue();
        stock.stockIn(eventQueue, stockInCommand);

        Assertions.assertEquals(stock.getNumber(), BigDecimal.ZERO);
        Assertions.assertEquals(stock.getWeight(), BigDecimal.ZERO);
        Assertions.assertEquals(stock.getPrice(), BigDecimal.ZERO);
    }

    @Test
    void test_stockOut_enough() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();
        StockOutCommand stockOutCommand = new StockOutCommand("001", new BigDecimal(5));
        stock.stockOut(eventQueue, stockOutCommand);

        stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        eventQueue = new TestEventQueue();
        stockOutCommand = new StockOutCommand("001", new BigDecimal(10));
        stock.stockOut(eventQueue, stockOutCommand);
    }

    @Test
    void test_stockOut_number_above() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();
        StockOutCommand stockOutCommand = new StockOutCommand("001", new BigDecimal(11));
        Assertions.assertThrows(DomainException.class, () -> stock.stockOut(eventQueue, stockOutCommand));
    }

    @Test
    void test_stockOut_weight_above() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();
        StockOutCommand stockOutCommand = new StockOutCommand("001", new BigDecimal(10), new BigDecimal(1), new BigDecimal(0));
        Assertions.assertThrows(DomainException.class, () -> stock.stockOut(eventQueue, stockOutCommand));
    }

    @Test
    void test_stockOut_price_above() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();
        StockOutCommand stockOutCommand = new StockOutCommand("001", new BigDecimal(10), new BigDecimal(0), new BigDecimal(1));
        Assertions.assertThrows(DomainException.class, () -> stock.stockOut(eventQueue, stockOutCommand));
    }

    @Test
    void tryLock_success() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(10));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);

        Assertions.assertTrue(stock.tryLock(eventQueue, stockLockCommand));
    }

    @Test
    void tryLock_stock_shortage() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(11));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);

        Assertions.assertFalse(stock.tryLock(eventQueue, stockLockCommand));
    }

    @Test
    void stockUnlock_success() {
        //lock
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(5));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);
        Assertions.assertTrue(stock.tryLock(eventQueue, stockLockCommand));

        //unlock
        StockUnlockCommand stockUnlockCommand = new StockUnlockCommand();
        stockUnlockCommand.setStockId("001");
        stockUnlockCommand.setLockReason(LockReason.OUT_ORDER);
        stockUnlockCommand.setUnlockNumber(new BigDecimal(3));
        stock.stockUnlock(eventQueue, stockUnlockCommand);
    }

    @Test
    void stockUnlock_above_number_of_lock() {
        //lock
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(5));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);
        Assertions.assertTrue(stock.tryLock(eventQueue, stockLockCommand));

        //unlock
        StockUnlockCommand stockUnlockCommand = new StockUnlockCommand();
        stockUnlockCommand.setStockId("001");
        stockUnlockCommand.setLockReason(LockReason.OUT_ORDER);
        stockUnlockCommand.setUnlockNumber(new BigDecimal(6));
        Assertions.assertThrows(DomainException.class, () -> {
            stock.stockUnlock(eventQueue, stockUnlockCommand);
        });
    }

    @Test
    void stockUnlock_reason_does_not_match() {
        //lock
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(5));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);
        Assertions.assertTrue(stock.tryLock(eventQueue, stockLockCommand));

        //unlock
        StockUnlockCommand stockUnlockCommand = new StockUnlockCommand();
        stockUnlockCommand.setStockId("001");
        stockUnlockCommand.setLockReason(LockReason.FREEZE);
        stockUnlockCommand.setUnlockNumber(new BigDecimal(3));
        Assertions.assertThrows(DomainException.class, () -> {
            stock.stockUnlock(eventQueue, stockUnlockCommand);
        });
    }

    @Test
    void stockUnlock_orderCode_does_not_match() {
        //lock
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockLockCommand stockLockCommand = new StockLockCommand();
        stockLockCommand.setStockId("001");
        stockLockCommand.setStockId(KeyGeneratorFactory.getUniqueKey());
        stockLockCommand.setLockNumber(new BigDecimal(5));
        stockLockCommand.setLockReason(LockReason.OUT_ORDER);
        stockLockCommand.setOrderCode("PO00001");
        Assertions.assertTrue(stock.tryLock(eventQueue, stockLockCommand));

        //unlock
        StockUnlockCommand stockUnlockCommand = new StockUnlockCommand();
        stockUnlockCommand.setStockId("001");
        stockUnlockCommand.setLockReason(LockReason.FREEZE);
        stockUnlockCommand.setUnlockNumber(new BigDecimal(3));
        stockLockCommand.setOrderCode("PO00002");
        Assertions.assertThrows(DomainException.class, () -> {
            stock.stockUnlock(eventQueue, stockUnlockCommand);
        });
    }

    @Test
    void attributeUpdate_success() {
        Stock stock = buildStockOfNumber(new BigDecimal(10), new BigDecimal(0), new BigDecimal(0));
        EventQueue eventQueue = new TestEventQueue();

        StockAttributeUpdateCommand stockAttributeUpdateCommand = new StockAttributeUpdateCommand();
        StockAttribute stockAttribute = new StockAttribute();
        stockAttribute.setProductBatch("P001");
        stockAttributeUpdateCommand.setToAttribute(stockAttribute);

        stock.attributeUpdate(eventQueue, stockAttributeUpdateCommand);
        Assertions.assertEquals(stockAttribute.getProductBatch(), "P001");
    }
}