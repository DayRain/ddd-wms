package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.enums.LockReason;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockUnlockCommand {

    private String stockId;

    private String orderCode;

    private BigDecimal unlockNumber;

    private String unlockedBy;

    private LockReason lockReason;
}
