package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.entity.StockAttribute;
import com.dayrain.wms.domain.entity.StockAttributeUpdateReason;
import lombok.Data;

@Data
public class StockAttributeUpdateCommand {
    private String stockId;

    private StockAttribute stockAttribute;

    private String createBy;

    private StockAttributeUpdateReason reason;
}
