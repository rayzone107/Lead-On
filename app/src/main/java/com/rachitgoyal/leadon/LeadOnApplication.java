package com.rachitgoyal.leadon;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class LeadOnApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }
}
