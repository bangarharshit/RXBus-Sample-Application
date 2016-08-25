package com.drawers.rxbus.events;

/**
 * Created by harshitbangar on 25/08/16.
 */

public class BufferEvent extends BaseEvent {
    public final long timestamp;

    public BufferEvent(long timestamp) {
        super(timestamp);
        this.timestamp = timestamp;
    }
}
