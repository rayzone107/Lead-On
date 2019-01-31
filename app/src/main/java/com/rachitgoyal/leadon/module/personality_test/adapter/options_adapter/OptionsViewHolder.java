package com.rachitgoyal.leadon.module.personality_test.adapter.options_adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.Option;
import com.rachitgoyal.leadon.module.personality_test.adapter.OnInteractionListener;
import com.rachitgoyal.leadon.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rachit Goyal on 31/01/19.
 */
public class OptionsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.option_cv)
    CardView mOptionCV;

    @BindView(R.id.option_rl)
    RelativeLayout mOptionRL;

    @BindView(R.id.option_image_iv)
    ImageView mOptionImageIV;

    @BindView(R.id.option_title_tv)
    TextView mOptionTitleTV;

    @BindView(R.id.option_description_tv)
    TextView mOptionDescriptionTV;

    private OnInteractionListener mListener;

    OptionsViewHolder(@NonNull View itemView, OnInteractionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    public void bind(Option option) {
        Glide.with(mOptionImageIV).load(option.getImageURL()).into(mOptionImageIV);

        mOptionTitleTV.setText(option.getTitle());
        if (StringUtils.isEmpty(option.getDescription())) {
            mOptionDescriptionTV.setVisibility(View.GONE);
        } else {
            mOptionDescriptionTV.setText(option.getDescription());
        }

        if (option.isSelected()) {
            mOptionRL.setBackground(ContextCompat.getDrawable(mOptionRL.getContext(), R.drawable.selected_bg));
        } else {
            mOptionRL.setBackgroundColor(ContextCompat.getColor(mOptionRL.getContext(), R.color.white));
        }

        mOptionCV.setOnClickListener(v -> mListener.onOptionClicked(option.getPosition() - 1));
    }
}
