package com.dayrain.wms.common.utils;

import java.util.Collection;

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collections) {
        return collections == null || collections.isEmpty();
    }
}
