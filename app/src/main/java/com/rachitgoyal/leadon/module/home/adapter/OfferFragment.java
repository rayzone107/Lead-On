package com.rachitgoyal.leadon.module.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachitgoyal.leadon.R;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class OfferFragment extends Fragment {

    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    @BindView(R.id.offer_iv)
    ImageView mOfferIV;

    private @DrawableRes
    int mImageRes;
    private String mType;
    private OnOfferClickedListener mListener;

    public static Fragment newInstance(@DrawableRes int image, String type) {
        OfferFragment offerFragment = new OfferFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_IMAGE, image);
        bundle.putString(EXTRA_TYPE, type);
        offerFragment.setArguments(bundle);
        return offerFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnOfferClickedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mImageRes = bundle.getInt(EXTRA_IMAGE);
            mType = bundle.getString(EXTRA_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.bind(this, root);

        Glide.with(mOfferIV).load(mImageRes).into(mOfferIV);

        mOfferIV.setOnClickListener(v -> mListener.onOfferClicked(mType));

        return root;
    }

    public interface OnOfferClickedListener {
        void onOfferClicked(String type);
    }
}
