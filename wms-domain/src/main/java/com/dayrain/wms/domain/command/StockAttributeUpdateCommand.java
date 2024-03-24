package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.stock.StockAttribute;
import com.dayrain.wms.domain.stock.StockAttributeUpdateReason;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockAttributeUpdateCommand {
    private String stockId;

    private StockAttribute toAttribute;

    private BigDecimal updateNumber;

    private BigDecimal updateWeight;

    private BigDecimal updatePrice;

    private String createBy;

    private StockAttributeUpdateReason reason;
}
