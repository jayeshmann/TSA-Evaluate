package com.tsa.exam;

import android.app.Application;
import android.content.Context;

import com.tsa.exam.receivers.ConnectivityReceiver;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ravi Tamada on 15/06/16.
 * www.androidhive.info
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = MyApplication.this;

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
