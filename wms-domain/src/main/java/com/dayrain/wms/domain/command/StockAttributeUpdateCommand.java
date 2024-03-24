package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.stock.StockAttribute;
import com.dayrain.wms.domain.stock.StockAttributeUpdateReason;
import lombok.Data;

@Data
public class StockAttributeUpdateCommand {
    private String stockId;

    private StockAttribute stockAttribute;

    private String createBy;

    private StockAttributeUpdateReason reason;
}
