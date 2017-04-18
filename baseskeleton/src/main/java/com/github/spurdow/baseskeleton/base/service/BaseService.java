package com.github.spurdow.baseskeleton.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.huemedscience.dsr.DSRApplication;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


/**
 * Created by Android on 11/15/2014.
 */
public abstract class BaseService extends Service {


    protected DSRApplication app;

    @Inject
    protected EventBus mBus;


    @Override
    public void onCreate() {
        super.onCreate();
        app = DSRApplication.get();
        app.inject(this);
        if(!mBus.isRegistered(this)){
            mBus.register(this);
        }
        provideInit();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handleOnStart(intent, flags, startId);
            }
        } , "BaseService").start();
        return startCommand();
    }

    @Override
    public void onDestroy() {
        disconnect();
        if(mBus.isRegistered(this)){
            mBus.unregister(this);
        }
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onEvent(String TAG){

    }

    public abstract void provideInit();

    public abstract void disconnect();

    public abstract int startCommand();

    public abstract void handleOnStart(Intent intent, int flags , int startId);

}
