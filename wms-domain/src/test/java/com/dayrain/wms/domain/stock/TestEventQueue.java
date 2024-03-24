package com.dayrain.wms.domain.stock;

import com.dayrain.wms.domain.common.DomainEvent;
import com.dayrain.wms.domain.common.EventQueue;

import java.util.ArrayList;
import java.util.List;

public class TestEventQueue implements EventQueue {

    private final List<DomainEvent> eventQueues = new ArrayList<>();

    @Override
    public void enqueue(DomainEvent domainEvent) {
        eventQueues.add(domainEvent);
    }

    @Override
    public List<DomainEvent> queue() {
        return eventQueues;
    }
}
