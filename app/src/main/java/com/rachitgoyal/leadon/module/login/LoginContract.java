package com.rachitgoyal.leadon.module.login;

import android.support.annotation.StringRes;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public interface LoginContract {
    interface View {

        void showInputError(int type, @StringRes int errorMessage);
    }

    interface Presenter {
        boolean validateEmailAndPassword(String email, String password);

        boolean validateEmail(String email);
    }
}
