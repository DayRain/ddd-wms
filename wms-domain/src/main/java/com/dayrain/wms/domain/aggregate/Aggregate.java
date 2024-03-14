package com.dayrain.wms.domain.aggregate;

public interface Aggregate<T> {

    T getRoot();
}
