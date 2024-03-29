package com.dayrain.wms.domain.common;

import java.util.List;

public interface EventQueue {
    void enqueue(DomainEvent domainEvent);

    List<DomainEvent> queue();
}
