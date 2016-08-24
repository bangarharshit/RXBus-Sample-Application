package com.drawers.rxbus;

import android.app.Application;

import com.drawers.EventBus;
import com.drawers.rxbus.annotation.PerApp;
import com.drawers.rxbus.events.BaseEvent;

import dagger.Component;


public class MainApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerMainApplication_AppComponent.builder()
                .mainApplicationModule(new MainApplicationModule()).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @PerApp
    @Component(modules = {MainApplicationModule.class})
    public interface AppComponent {
        void inject(MainApplication mainApplication);

        EventBus<BaseEvent> provideEventBus();
    }
}
