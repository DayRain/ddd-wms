package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockMinusEvent implements DomainEvent {
    private String stockId;

    private String binId;

    private String sku;

    private StockAttribute stockAttribute;

    private BigDecimal fromNumber;

    private BigDecimal fromWeight;

    private BigDecimal fromPrice;

    private BigDecimal minusNumber;

    private BigDecimal minusWeight;

    private BigDecimal minusPrice;
}
