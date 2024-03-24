package com.dayrain.wms.domain.stock;

import com.dayrain.wms.common.key.KeyGeneratorFactory;
import com.dayrain.wms.domain.command.StockInCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockFactory {

    public Stock buildInitStock(String sku, StockAttribute stockAttribute, String binId) {
        Stock stock = Stock.builder().id(KeyGeneratorFactory.getUniqueKey())
                .stockAttribute(stockAttribute)
                .sku(sku)
                .price(BigDecimal.ZERO)
                .number(BigDecimal.ZERO)
                .weight(BigDecimal.ZERO)
                .binId(binId)
                .build();
        return stock;
    }
}
