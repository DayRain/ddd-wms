package com.dayrain.wms.domain.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockOutCommand {

    private String stockId;

    private String binId;

    private String sku;

    private BigDecimal number;

    private BigDecimal weight;

    private BigDecimal price;
}
