package com.rachitgoyal.leadon.module.personality_test.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.InputQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
class InputViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_iv)
    ImageView mImageIV;

    @BindView(R.id.title_tv)
    TextView mTitleTV;

    @BindView(R.id.description_tv)
    TextView mDescriptionTV;

    @BindView(R.id.input_et)
    EditText mInputET;

    private OnInteractionListener mListener;

    InputViewHolder(@NonNull View itemView, OnInteractionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    void bind(InputQuestion question) {
        if (question.getImageURL() != null && !question.getImageURL().isEmpty()) {
            Glide.with(mImageIV).load(question.getImageURL()).into(mImageIV);
        } else {
            mImageIV.setVisibility(View.GONE);
        }
        mTitleTV.setText(question.getTitle());
        if (question.getDescription() != null && !question.getDescription().isEmpty()) {
            mDescriptionTV.setText(question.getDescription());
        } else {
            mDescriptionTV.setVisibility(View.GONE);
        }

        mInputET.setInputType(question.getInputType());
        mInputET.setHint(question.getHint());

        mInputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                mListener.inputFieldValueChanged(text.toString());
            }
        });
    }
}
