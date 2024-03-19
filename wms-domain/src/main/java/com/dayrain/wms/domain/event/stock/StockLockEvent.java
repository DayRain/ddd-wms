package com.dayrain.wms.domain.event.stock;

import com.dayrain.wms.domain.enums.LockReason;
import com.dayrain.wms.domain.event.DomainEvent;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class StockLockEvent implements DomainEvent {
    private String lockId;

    private String stockId;

    private BigDecimal lockNumber;

    private String orderCode;

    private LocalDateTime lockTime;

    private String lockedBy;

    private LockReason lockReason;
}
