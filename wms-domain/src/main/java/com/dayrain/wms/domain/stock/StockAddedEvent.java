package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockAddedEvent implements DomainEvent {

    private String stockId;

    private String sku;

    private StockAttribute stockAttribute;

    private BigDecimal fromNumber;

    private BigDecimal fromWeight;

    private BigDecimal fromPrice;

    private BigDecimal addedNumber;

    private BigDecimal addedWeight;

    private BigDecimal addedPrice;

    private StockAddReason stockAddReason = StockAddReason.IN;
}
