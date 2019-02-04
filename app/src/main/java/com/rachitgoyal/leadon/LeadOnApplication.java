package com.rachitgoyal.leadon;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class LeadOnApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    public FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }
}
