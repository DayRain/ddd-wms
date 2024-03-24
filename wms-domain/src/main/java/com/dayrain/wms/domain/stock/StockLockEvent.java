package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
