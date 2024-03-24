package com.dayrain.wms.domain.stock;

import com.dayrain.wms.common.exception.*;
import com.dayrain.wms.domain.common.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockAttribute implements ValueObject, Cloneable {

    private String wmsBatch;

    private String productBatch;

    private String asnCode;

    private StockStatus stockStatus;

    private LocalDate madeDate;

    private LocalDate inDate;

    private LocalDate expireDate;

    public StockAttribute(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ServerException(CommonErrorCode.SERIAL_ERROR);
        }
    }
}
