package com.dayrain.wms.common.utils;

import java.math.BigDecimal;

public class DecimalUtils {

    public static DecimalWrapper is(BigDecimal current) {
        return new DecimalWrapper(current);
    }
}
