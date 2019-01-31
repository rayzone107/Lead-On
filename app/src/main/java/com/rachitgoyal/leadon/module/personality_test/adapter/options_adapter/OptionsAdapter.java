package com.rachitgoyal.leadon.module.personality_test.adapter.options_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.Option;
import com.rachitgoyal.leadon.module.personality_test.adapter.OnInteractionListener;

import java.util.List;

/**
 * Created by Rachit Goyal on 31/01/19.
 */
public class OptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Option> mOptions;
    private OnInteractionListener mListener;

    public OptionsAdapter(List<Option> options, OnInteractionListener listener) {
        mOptions = options;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View optionsLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
        return new OptionsViewHolder(optionsLayout, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((OptionsViewHolder) viewHolder).bind(mOptions.get(position));
    }

    @Override
    public int getItemCount() {
        return mOptions == null || mOptions.isEmpty() ? 0 : mOptions.size();
    }
}
