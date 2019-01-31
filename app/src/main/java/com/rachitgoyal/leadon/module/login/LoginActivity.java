package com.rachitgoyal.leadon.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.module.base.BaseActivity;
import com.rachitgoyal.leadon.module.home.HomeActivity;
import com.rachitgoyal.leadon.module.sign_up.SignUpActivity;
import com.rachitgoyal.leadon.util.Constants;
import com.rachitgoyal.leadon.util.Utils;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.root_view)
    ScrollView mRootView;

    @BindView(R.id.email_et)
    EditText mEmailET;

    @BindView(R.id.password_et)
    EditText mPasswordET;

    @BindView(R.id.log_in_btn)
    Button mLoginBtn;

    @BindView(R.id.login_pb)
    ProgressBar mLoginPB;

    @BindView(R.id.sign_up_btn)
    Button mSignUpButton;

    @BindView(R.id.forgot_password_tv)
    TextView mForgotPasswordTV;

    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            goToHomeActivity();
        }

        mSignUpButton.setOnClickListener(v -> goToSignUpActivity());

        mLoginBtn.setOnClickListener(v -> {
            Utils.hideKeyboard(mSignUpButton);
            final String email = mEmailET.getText().toString();
            String password = mPasswordET.getText().toString();

            if (mPresenter.validateEmailAndPassword(email, password)) {
                showLoginProgress(true);
                mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    showLoginProgress(false);
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, task.getException() == null ? getString(R.string.invalid_credentials) :
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        checkIfEmailVerified();
                    }
                });
            }
        });

        mForgotPasswordTV.setOnClickListener(v -> {
            final String email = mEmailET.getText().toString();
            if (mPresenter.validateEmail(email)) {
                Utils.hideKeyboard(mForgotPasswordTV);
                mFirebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.password_reset_email_has_been_sent, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException() == null ? getString(R.string.invalid_email_id) :
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void showLoginProgress(boolean showProgress) {
        mLoginBtn.setVisibility(showProgress ? View.INVISIBLE : View.VISIBLE);
        mLoginPB.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            goToHomeActivity();
        } else {
            Toast.makeText(LoginActivity.this, R.string.email_not_verified, Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showInputError(int errorType, @StringRes int errorMessage) {
        switch (errorType) {
            case Constants.ERROR_EMAIL:
                mEmailET.setError(getString(errorMessage));
                break;
            case Constants.ERROR_PASSWORD:
                mPasswordET.setError(getString(errorMessage));
                break;
        }
    }
}

