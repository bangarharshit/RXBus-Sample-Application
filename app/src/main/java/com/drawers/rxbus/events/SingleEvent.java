package com.drawers.rxbus.events;

public class SingleEvent extends BaseEvent {
    public final long timestamp;

    public SingleEvent(long timestamp) {
        super(timestamp);
        this.timestamp = timestamp;
    }
}
