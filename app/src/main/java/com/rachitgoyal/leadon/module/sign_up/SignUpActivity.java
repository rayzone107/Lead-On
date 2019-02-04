package com.rachitgoyal.leadon.module.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.module.base.BaseActivity;
import com.rachitgoyal.leadon.module.home.HomeActivity;
import com.rachitgoyal.leadon.module.login.LoginActivity;
import com.rachitgoyal.leadon.util.Constants;
import com.rachitgoyal.leadon.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity implements SignUpContract.View {

    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.root_view)
    ScrollView mRootView;

    @BindView(R.id.email_et)
    EditText mEmailET;

    @BindView(R.id.password_et)
    EditText mPasswordET;

    @BindView(R.id.sign_up_btn)
    Button mSignUpButton;

    @BindView(R.id.sign_up_pb)
    ProgressBar mSignUpPB;

    @BindView(R.id.already_signed_in_tv)
    TextView mAlreadySignedInTV;

    @BindView(R.id.google_sign_in)
    SignInButton mGoogleSignInButton;

    private SignUpContract.Presenter mPresenter;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mPresenter = new SignUpPresenter(this);
        mSignUpButton.setOnClickListener(v -> {
            Utils.hideKeyboard(mSignUpButton);
            final String email = mEmailET.getText().toString();
            String password = mPasswordET.getText().toString();

            if (mPresenter.validateEmailAndPassword(email, password)) {
                showSignUpProgress(true);
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, task -> {
                            showSignUpProgress(false);
                            if (task.isSuccessful()) {
                                mPresenter.sendAuthenticationEmail();
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException() == null ? getString(R.string.sign_in_failed) :
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mEmailET.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                mPasswordET.requestFocus();
            }
            return false;
        });

        mPasswordET.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                mSignUpButton.performClick();
            }
            return false;
        });

        mAlreadySignedInTV.setOnClickListener(v -> goToLoginActivity());

        mGoogleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void showSignUpProgress(boolean showProgress) {
        mSignUpButton.setVisibility(showProgress ? View.INVISIBLE : View.VISIBLE);
        mSignUpPB.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                Toast.makeText(SignUpActivity.this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goToHomeActivity();
                    } else {
                        Toast.makeText(SignUpActivity.this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToLoginActivity() {
        Toast.makeText(this, getString(R.string.authentication_email_sent), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSendingEmailFailed() {
        Toast.makeText(this, R.string.unable_to_send_authentication_email, Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
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
