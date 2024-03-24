package com.dayrain.wms.domain.common;

public interface DomainEventListener {
    void onEvent(DomainEvent event);
}
