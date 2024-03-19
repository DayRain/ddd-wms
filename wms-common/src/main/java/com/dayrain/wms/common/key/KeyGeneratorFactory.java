package com.dayrain.wms.common.key;

public class KeyGeneratorFactory {

    private static final KeyGenerator keyGenerator = new SnowFlakeKeyGenerator();

    public static String getUniqueKey() {
        return keyGenerator.getKey();
    }
}
