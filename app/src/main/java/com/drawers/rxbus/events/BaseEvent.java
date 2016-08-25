package com.drawers.rxbus.events;

/**
 * Created by harshitbangar on 25/08/16.
 */

public class BaseEvent {
    public final long timestamp;

    public BaseEvent(long timestamp) {
        this.timestamp = timestamp;
    }
}
