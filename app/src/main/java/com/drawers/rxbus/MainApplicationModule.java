package com.drawers.rxbus;

import android.support.annotation.NonNull;

import com.drawers.EventBus;
import com.drawers.rxbus.annotation.PerApp;
import com.drawers.rxbus.events.BaseEvent;
import com.drawers.rxbus.events.BufferEvent;
import com.drawers.rxbus.events.LastEmitEvent;

import dagger.Module;
import dagger.Provides;

@Module
public class MainApplicationModule {

    @Provides
    @PerApp
    @NonNull
    EventBus<BaseEvent> provideEventBus() {
       return new EventBus.Builder<BaseEvent>(BaseEvent.class)
               .addSubjectWithBuffer(BufferEvent.class, 3)
               .addSubjectWithBuffer(LastEmitEvent.class, 1)
               .build();
    }
}
