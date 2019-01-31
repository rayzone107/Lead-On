package com.rachitgoyal.leadon.module.home.personal_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.module.personality_test.PersonalityTestActivity;

public class TasksFragment extends Fragment {

    @BindView(R.id.root_view)
    View mRootView;

    @BindView(R.id.personality_test_cv)
    CardView mPersonalityTestCV;

    @BindView(R.id.survey_cv)
    CardView mSurveyCV;

    @BindView(R.id.sweepstakes_cv)
    CardView mSweepstakesCV;

    @BindView(R.id.trivia_cv)
    CardView mTriviaCV;

    public TasksFragment() {
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        mPersonalityTestCV.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonalityTestActivity.class);
            startActivity(intent);
        });

        mSurveyCV.setOnClickListener(v -> Snackbar.make(mRootView, R.string.no_survey_available, Snackbar.LENGTH_SHORT).show());

        mSweepstakesCV.setOnClickListener(v -> Snackbar.make(mRootView, R.string.no_sweepstakes_available, Snackbar.LENGTH_SHORT).show());

        mTriviaCV.setOnClickListener(v -> Snackbar.make(mRootView, R.string.no_trivia_available, Snackbar.LENGTH_SHORT).show());
        return view;
    }
}
