package com.dayrain.wms.domain.entity;

import com.dayrain.wms.domain.enums.LockReason;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class StockLock {

    private String id;

    private String stockId;

    private BigDecimal lockNumber;

    private String orderCode;

    private LocalDateTime lockTime;

    private String lockedBy;

    private LockReason lockReason;

    public StockLock(String id, String stockId, BigDecimal lockNumber, String orderCode
            , LocalDateTime lockTime, String lockedBy, LockReason lockReason) {
        this.id = id;
        this.stockId = stockId;
        this.lockNumber = lockNumber;
        this.orderCode = orderCode;
        this.lockTime = lockTime;
        this.lockedBy = lockedBy;
        this.lockReason = lockReason;
    }
}
