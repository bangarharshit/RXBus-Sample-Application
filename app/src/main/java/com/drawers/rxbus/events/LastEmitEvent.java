package com.drawers.rxbus.events;

/**
 * Created by harshitbangar on 25/08/16.
 */

public class LastEmitEvent extends BaseEvent{
    public final long timestamp;

    public LastEmitEvent(long timestamp) {
        this.timestamp = timestamp;
    }
}
