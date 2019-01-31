package com.rachitgoyal.leadon.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.rachitgoyal.leadon.LeadOnApplication;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mFirebaseAuth = ((LeadOnApplication) getApplication()).getFirebaseAuthInstance();
    }
}
