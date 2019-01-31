package com.rachitgoyal.leadon.module.home.fragment_discount;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.Lead;
import com.rachitgoyal.leadon.util.StringUtils;
import com.rachitgoyal.leadon.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rachit Goyal on 31/01/19.
 */
public class DiscountDialogFragment extends DialogFragment {

    @BindView(R.id.user_name_et)
    TextInputEditText mUserNameET;

    @BindView(R.id.mobile_et)
    TextInputEditText mMobileET;

    @BindView(R.id.email_et)
    TextInputEditText mEmailET;

    @BindView(R.id.cancel_btn)
    Button mCancelBtn;

    @BindView(R.id.submit_btn)
    Button mSubmitBtn;

    private OnDialogClickListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnDialogClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        ButterKnife.bind(this, view);
        mCancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        mSubmitBtn.setOnClickListener(v -> {
            boolean isValid = true;
            if (mUserNameET.getText() == null || StringUtils.isEmpty(mUserNameET.getText().toString())) {
                mUserNameET.setError("Cannot be empty");
                isValid = false;
            }
            if (mMobileET.getText() == null || StringUtils.isEmpty(mMobileET.getText().toString())) {
                mMobileET.setError("Cannot be empty");
                isValid = false;
            }

            if (mEmailET.getText() == null || StringUtils.isEmpty(mEmailET.getText().toString())) {
                mEmailET.setError("Cannot be empty");
                isValid = false;
            }

            if (isValid) {
                String name = mUserNameET.getText().toString();
                String mobile = mMobileET.getText().toString();
                String email = mEmailET.getText().toString();
                Lead lead = new Lead(name, mobile, email);
                mListener.onSubmitClicked(lead);
                Utils.hideKeyboard(mSubmitBtn);
                dismiss();
            }
        });
        return view;
    }

    public interface OnDialogClickListener {
        void onSubmitClicked(Lead lead);
    }
}
