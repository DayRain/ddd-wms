package com.dayrain.wms.domain.command;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StockMoveCommand {
    private String stockId;

    private BigDecimal number;

    private String fromBinId;

    private String toBinId;

    private String createBy;

    private LocalDateTime createTime;
}
