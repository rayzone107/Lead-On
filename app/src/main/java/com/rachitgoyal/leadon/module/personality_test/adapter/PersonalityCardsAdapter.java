package com.rachitgoyal.leadon.module.personality_test.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.InputQuestion;
import com.rachitgoyal.leadon.model.MultiSelectQuestion;
import com.rachitgoyal.leadon.model.Question;
import com.rachitgoyal.leadon.model.RadioQuestion;
import com.rachitgoyal.leadon.model.SliderQuestion;
import com.rachitgoyal.leadon.util.Constants;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class PersonalityCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> mQuestions;
    private OnInteractionListener mListener;

    public PersonalityCardsAdapter(List<Question> questions, OnInteractionListener listener) {
        mQuestions = questions;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constants.TEST_QUESTION_TYPE.INPUT:
                View inputLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_input, parent, false);
                return new InputViewHolder(inputLayout, mListener);
            case Constants.TEST_QUESTION_TYPE.SLIDER:
                View sliderLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_slider, parent, false);
                return new SliderViewHolder(sliderLayout, mListener);
            case Constants.TEST_QUESTION_TYPE.MULTISELECT:
                View multiSelectLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_multi_select, parent, false);
                return new MultiSelectViewHolder(multiSelectLayout, mListener);
            default:
                View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_radio, parent, false);
                return new RadioViewHolder(layout, mListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (mQuestions.get(position) instanceof InputQuestion) {
            ((InputViewHolder) viewHolder).bind((InputQuestion) mQuestions.get(position));
        } else if (mQuestions.get(position) instanceof SliderQuestion) {
            ((SliderViewHolder) viewHolder).bind((SliderQuestion) mQuestions.get(position));
        } else if (mQuestions.get(position) instanceof MultiSelectQuestion) {
            ((MultiSelectViewHolder) viewHolder).bind((MultiSelectQuestion) mQuestions.get(position));
        } else {
            ((RadioViewHolder) viewHolder).bind((RadioQuestion) mQuestions.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mQuestions == null || mQuestions.isEmpty() ? 0 : mQuestions.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mQuestions.get(position) instanceof InputQuestion) {
            return Constants.TEST_QUESTION_TYPE.INPUT;
        } else if (mQuestions.get(position) instanceof SliderQuestion) {
            return Constants.TEST_QUESTION_TYPE.SLIDER;
        } else if (mQuestions.get(position) instanceof MultiSelectQuestion) {
            return Constants.TEST_QUESTION_TYPE.MULTISELECT;
        } else {
            return Constants.TEST_QUESTION_TYPE.RADIO;
        }
    }
}
