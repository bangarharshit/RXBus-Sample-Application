package com.drawers.rxbus;

import android.app.IntentService;
import android.content.Intent;

import com.drawers.EventBus;
import com.drawers.rxbus.annotation.PerService;
import com.drawers.rxbus.events.BaseEvent;
import com.drawers.rxbus.events.BufferEvent;
import com.drawers.rxbus.events.LastEmitEvent;
import com.drawers.rxbus.events.SingleEvent;

import javax.inject.Inject;

import dagger.Component;

public class MainService extends IntentService {

    @Inject EventBus<BaseEvent> mBaseEventEventBus;
    public MainService(String name) {
        super(name);
    }

    public MainService() {
        super("arbit");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MainApplication mainApplication = (MainApplication) getApplication();
        MainActivityComponent mainActivityComponent = DaggerMainService_MainActivityComponent.builder()
                .appComponent(mainApplication.getAppComponent()).build();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mBaseEventEventBus.post(new BufferEvent(4));
        mBaseEventEventBus.post(new BufferEvent(5));
        mBaseEventEventBus.post(new LastEmitEvent(6));
        mBaseEventEventBus.post(new SingleEvent(4));
    }

    @PerService
    @Component(dependencies = MainApplication.AppComponent.class)
    interface MainActivityComponent {
        void inject(MainService mainService);
    }
}
