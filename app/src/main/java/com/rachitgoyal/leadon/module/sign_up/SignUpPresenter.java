package com.rachitgoyal.leadon.module.sign_up;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.util.Constants;
import com.rachitgoyal.leadon.util.StringUtils;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;

    SignUpPresenter(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public boolean validateEmailAndPassword(String email, String password) {
        boolean isValid = true;
        if (email.isEmpty()) {
            mView.showInputError(Constants.ERROR_EMAIL, R.string.email_cannot_be_blank);
            isValid = false;
        } else if (!StringUtils.isValidEmail(email)) {
            mView.showInputError(Constants.ERROR_EMAIL, R.string.please_input_a_valid_email_address);
            isValid = false;
        }

        if (password.isEmpty()) {
            mView.showInputError(Constants.ERROR_PASSWORD, R.string.password_cannot_be_blank);
            isValid = false;
        } else if (password.length() < 8) {
            mView.showInputError(Constants.ERROR_PASSWORD, R.string.password_must_be_more_than_8_characters);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void sendAuthenticationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().signOut();
                    mView.goToLoginActivity();
                } else {
                    mView.showSendingEmailFailed();
                }
            });
        }
    }
}
