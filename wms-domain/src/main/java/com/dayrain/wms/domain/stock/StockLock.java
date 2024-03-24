package com.dayrain.wms.domain.stock;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dayrain.wms.common.utils.StringUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
public class StockLock {

    @TableId
    private String id;

    private String stockId;

    private BigDecimal lockNumber;

    private String orderCode;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String lockedBy;

    private LockReason lockReason;

    public StockLock(String id, String stockId, BigDecimal lockNumber, String orderCode
            , LocalDateTime createTime, String lockedBy, LockReason lockReason) {
        this.id = id;
        this.stockId = stockId;
        this.lockNumber = lockNumber;
        this.orderCode = orderCode;
        this.createTime = createTime;
        this.lockedBy = lockedBy;
        this.lockReason = lockReason;
    }

    public boolean isMatch(String orderCode, LockReason lockReason) {
        if(!StringUtils.isEmpty(this.orderCode)) {
            return Objects.equals(orderCode, this.orderCode) && this.lockReason.equals(lockReason);
        }

        return this.lockReason.equals(lockReason);
    }
}
