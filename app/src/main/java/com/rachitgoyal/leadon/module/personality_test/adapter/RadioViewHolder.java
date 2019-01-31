package com.rachitgoyal.leadon.module.personality_test.adapter;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.RadioQuestion;
import com.rachitgoyal.leadon.module.personality_test.adapter.options_adapter.OptionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
class RadioViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_iv)
    ImageView mImageIV;

    @BindView(R.id.title_tv)
    TextView mTitleTV;

    @BindView(R.id.description_tv)
    TextView mDescriptionTV;

    @BindView(R.id.options_rv)
    RecyclerView mOptionsRV;

    private OnInteractionListener mListener;

    RadioViewHolder(@NonNull View itemView, OnInteractionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    void bind(final RadioQuestion question) {
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

        int orientation = mOptionsRV.getContext().getResources().getConfiguration().orientation;
        int span = orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;

        mOptionsRV.setLayoutManager(new GridLayoutManager(mOptionsRV.getContext(), span));
        mOptionsRV.setAdapter(new OptionsAdapter(question.getOptions(), mListener));
    }
}
