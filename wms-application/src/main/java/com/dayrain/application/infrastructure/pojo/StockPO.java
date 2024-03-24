package com.dayrain.application.infrastructure.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.dayrain.wms.domain.stock.StockAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StockPO extends StockAttribute{

    @TableId
    private String id;

    private String sku;

    private BigDecimal number;

    private BigDecimal weight;

    private BigDecimal price;

    private String binId;

    private LocalDateTime createTime;

    private String sn;
}
