package com.dayrain.wms.domain.command;

import com.dayrain.wms.domain.stock.LockReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class StockLockCommand {

    private String stockId;

    private String orderCode;

    private BigDecimal lockNumber;

    private String lockedBy;

    private LockReason lockReason;
}
