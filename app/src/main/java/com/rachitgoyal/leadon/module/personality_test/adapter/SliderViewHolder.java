package com.rachitgoyal.leadon.module.personality_test.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.SliderQuestion;
import com.ramotion.fluidslider.FluidSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
class SliderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_iv)
    ImageView mImageIV;

    @BindView(R.id.title_tv)
    TextView mTitleTV;

    @BindView(R.id.description_tv)
    TextView mDescriptionTV;

    @BindView(R.id.slider_fs)
    FluidSlider mSliderFS;

    private OnInteractionListener mListener;

    SliderViewHolder(@NonNull View itemView, OnInteractionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    void bind(final SliderQuestion question) {
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

        int max = question.getEnd();
        final int min = question.getStart();
        final int total = max - min;

        mSliderFS.setStartText(String.valueOf(min));
        mSliderFS.setEndText(String.valueOf(max));

        mSliderFS.setPositionListener(current -> {
            mSliderFS.setBubbleText(String.valueOf((int) (min + (current * total))));
            return Unit.INSTANCE;
        });

        mSliderFS.setEndTrackingListener(() -> {
            mListener.sliderValueChanged((int) (min + (mSliderFS.getPosition() * total)));
            return Unit.INSTANCE;
        });
    }
}
