package com.rachitgoyal.leadon.module.login;

import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.util.Constants;
import com.rachitgoyal.leadon.util.StringUtils;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    @Override
    public boolean validateEmailAndPassword(String email, String password) {
        boolean isValid = validateEmail(email);

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
    public boolean validateEmail(String email) {
        if (email.isEmpty()) {
            mView.showInputError(Constants.ERROR_EMAIL, R.string.email_cannot_be_blank);
            return false;
        } else if (!StringUtils.isValidEmail(email)) {
            mView.showInputError(Constants.ERROR_EMAIL, R.string.please_input_a_valid_email_address);
            return false;
        }
        return true;
    }
}
