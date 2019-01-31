package com.rachitgoyal.leadon.module.sign_up;

import android.support.annotation.StringRes;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public interface SignUpContract {
    interface View {

        void showInputError(int errorType, @StringRes int errorMessage);

        void goToLoginActivity();

        void showSendingEmailFailed();
    }
    interface Presenter {

        boolean validateEmailAndPassword(String email, String password);

        void sendAuthenticationEmail();
    }
}
