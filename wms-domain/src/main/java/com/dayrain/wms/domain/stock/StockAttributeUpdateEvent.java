package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAttributeUpdateEvent implements DomainEvent {

    private String stockId;

    private StockAttribute fromAttribute;

    private StockAttribute toAttribute;

    private String sku;

    private BigDecimal number;
}
