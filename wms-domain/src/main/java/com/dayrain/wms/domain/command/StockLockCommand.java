package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.enums.LockReason;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockLockCommand {

    private String stockId;

    private String orderCode;

    private BigDecimal lockNumber;

    private String lockedBy;

    private LockReason lockReason;
}
