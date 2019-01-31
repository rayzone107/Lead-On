package com.rachitgoyal.leadon.module.personality_test.adapter;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public interface OnInteractionListener {

    void inputFieldValueChanged(String value);

    void sliderValueChanged(int currentValue);

    void onOptionClicked(int position);
}
