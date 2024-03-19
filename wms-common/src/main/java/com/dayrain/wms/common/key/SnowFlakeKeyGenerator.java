package com.dayrain.wms.common.key;

public class SnowFlakeKeyGenerator implements KeyGenerator {

    private static final Snowflake SNOW_FLAKE = new Snowflake(0, 0);

    @Override
    public String getKey() {
        return String.valueOf(SNOW_FLAKE.nextId());
    }
}
