package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.entity.StockAttribute;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
}
