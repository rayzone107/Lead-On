package com.rachitgoyal.leadon.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.util.Constants;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class OfferPagerAdapter extends FragmentPagerAdapter {

    public OfferPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = OfferFragment.newInstance(R.drawable.referral, Constants.OFFER_TYPE.REFERRAL);
                break;
            case 1:
                fragment = OfferFragment.newInstance(R.drawable.discount, Constants.OFFER_TYPE.DISCOUNT);
                break;
            case 2:
                fragment = OfferFragment.newInstance(R.drawable.referral, Constants.OFFER_TYPE.REFERRAL);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
