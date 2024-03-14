package com.dayrain.wms.common.utils;

import java.math.BigDecimal;

public class DecimalWrapper {

    private final BigDecimal current;

    public DecimalWrapper(BigDecimal current) {
        this.current = current;
    }

    public boolean eq(BigDecimal other) {
        return current.equals(other);
    }

    public boolean gt(BigDecimal other) {
        return current.compareTo(other) > 0;
    }

    public boolean lt(BigDecimal other) {
        return current.compareTo(other) < 0;
    }

    public boolean gte(BigDecimal other) {
        return current.compareTo(other) >= 0;
    }

    public boolean lte(BigDecimal other) {
        return current.compareTo(other) <= 0;
    }
}
