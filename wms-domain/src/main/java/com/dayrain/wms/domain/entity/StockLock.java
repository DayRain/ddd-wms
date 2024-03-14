package com.dayrain.wms.domain.entity;

import com.dayrain.wms.domain.enums.LockReason;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockLock {

    private String id;

    private String stockId;

    private BigDecimal lockNumber;

    private String orderCode;

    private LocalDateTime lockTime;

    private String lockedBy;

    private LockReason lockReason;
}
