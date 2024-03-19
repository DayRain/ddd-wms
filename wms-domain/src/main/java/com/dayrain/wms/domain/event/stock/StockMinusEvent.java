package com.dayrain.wms.domain.event.stock;

import com.dayrain.wms.domain.entity.StockAttribute;
import com.dayrain.wms.domain.event.DomainEvent;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class StockMinusEvent implements DomainEvent {
    private String stockId;

    private String sku;

    private StockAttribute stockAttribute;

    private BigDecimal fromNumber;

    private BigDecimal fromWeight;

    private BigDecimal fromPrice;

    private BigDecimal minusNumber;

    private BigDecimal minusWeight;

    private BigDecimal minusPrice;
}
