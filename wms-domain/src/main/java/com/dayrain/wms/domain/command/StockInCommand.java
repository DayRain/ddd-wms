package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.stock.StockAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockInCommand {
    private String stockId;

    private String sku;

    private String binId;

    private String createBy;

    private LocalDateTime createTime;

    private StockAttribute stockAttribute;

    private BigDecimal number;

    private BigDecimal weight;

    private BigDecimal price;

    private String sn;

    public StockInCommand(String stockId, BigDecimal number, BigDecimal weight, BigDecimal price) {
        this.stockId = stockId;
        this.number = number;
        this.weight = weight;
        this.price = price;
    }
}
