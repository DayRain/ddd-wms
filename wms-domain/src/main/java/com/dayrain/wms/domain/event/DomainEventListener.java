package com.dayrain.wms.domain.event;

public interface DomainEventListener {
    void onEvent(DomainEvent event);
}
