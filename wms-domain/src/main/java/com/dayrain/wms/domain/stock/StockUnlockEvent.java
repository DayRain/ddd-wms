package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockUnlockEvent implements DomainEvent {
    private String unlockId;

    private String stockId;

    private BigDecimal unlockNumber;

    private String orderCode;

    private LocalDateTime unlockTime;

    private String lockedBy;

    private LockReason lockReason;
}
