package com.drawers.rxbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.drawers.EventBus;
import com.drawers.rxbus.annotation.PerActivity;
import com.drawers.rxbus.events.BaseEvent;
import com.drawers.rxbus.events.BufferEvent;
import com.drawers.rxbus.events.LastEmitEvent;
import com.drawers.rxbus.events.SingleEvent;

import javax.inject.Inject;

import dagger.Component;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @Inject EventBus<BaseEvent> mBaseEventEventBus;
    @NonNull private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainApplication mainApplication = (MainApplication) getApplication();
        MainActivityComponent mainActivityComponent =
                DaggerMainActivity_MainActivityComponent.builder()
                .appComponent(mainApplication.getAppComponent()).build();
        mainActivityComponent.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainService.class);
                MainActivity.this.startService(intent);
            }
        });
        mCompositeSubscription.add(mBaseEventEventBus
                .observeEvents(LastEmitEvent.class)
                .map(new Func1<LastEmitEvent, String>() {
                    @Override
                    public String call(LastEmitEvent lastEmitEvent) {
                        return String.format("Last Emit Event %s", lastEmitEvent.timestamp);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String lastEmitEvent) {
                        Toast.makeText(MainActivity.this, lastEmitEvent, Toast.LENGTH_LONG).show();
                    }
                }));
        mCompositeSubscription.add(mBaseEventEventBus
                .observeEvents(BufferEvent.class, SingleEvent.class)
                .subscribe(new Action1<BaseEvent>() {
                    @Override
                    public void call(BaseEvent baseEvent) {
                        Toast.makeText(MainActivity.this, "Either buffer or single - base event", Toast.LENGTH_LONG).show();
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();
    }

    @PerActivity
    @Component(dependencies = MainApplication.AppComponent.class)
    interface MainActivityComponent {
        void inject(MainActivity mainActivity);
    }
}
