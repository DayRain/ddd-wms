package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.stock.StockMinusReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockOutCommand {

    private String stockId;

    private String binId;

    private String sku;

    private BigDecimal number;

    private BigDecimal weight = BigDecimal.ZERO;

    private BigDecimal price = BigDecimal.ZERO;

    private StockMinusReason stockMinusReason = StockMinusReason.OUT;

    public StockOutCommand(String stockId, BigDecimal number) {
        this.stockId = stockId;
        this.number = number;
    }

    public StockOutCommand(String stockId, BigDecimal number, BigDecimal weight, BigDecimal price) {
        this.stockId = stockId;
        this.number = number;
        this.weight = weight;
        this.price = price;
    }
}
