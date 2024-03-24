package com.dayrain.wms.domain.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sn {

    private String snCode;

    private String stockId;

    public Sn(String snCode, String stockId) {
        this.snCode = snCode;
        this.stockId = stockId;
    }
}
