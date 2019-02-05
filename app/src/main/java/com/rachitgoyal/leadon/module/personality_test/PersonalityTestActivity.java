package com.rachitgoyal.leadon.module.personality_test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rachitgoyal.leadon.R;
import com.rachitgoyal.leadon.model.Answer;
import com.rachitgoyal.leadon.model.InputQuestion;
import com.rachitgoyal.leadon.model.MultiSelectQuestion;
import com.rachitgoyal.leadon.model.Question;
import com.rachitgoyal.leadon.model.RadioQuestion;
import com.rachitgoyal.leadon.model.SliderQuestion;
import com.rachitgoyal.leadon.module.base.BaseActivity;
import com.rachitgoyal.leadon.module.personality_test.adapter.OnInteractionListener;
import com.rachitgoyal.leadon.module.personality_test.adapter.PersonalityCardsAdapter;
import com.rachitgoyal.leadon.util.StringUtils;
import com.rachitgoyal.leadon.util.Utils;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalityTestActivity extends BaseActivity implements OnInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cards_csv)
    CardStackView mCardsCSV;

    @BindView(R.id.skip_btn)
    Button mSkipBtn;

    @BindView(R.id.continue_btn)
    Button mContinueBtn;

    @BindView(R.id.end_tv)
    TextView mEndTV;

    private PersonalityCardsAdapter mAdapter;
    private int mCurrentQuestionCount = 0;

    private Set<Integer> mSelectedOptionsForMultiSelect = new HashSet<>();
    private int mSelectedOptionForSingleSelect;
    private String mCurrentAnswerForInput;
    private int mCurrentValueInSlider;
    private List<Question> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mQuestions = Utils.generateQuestionsList();
        mAdapter = new PersonalityCardsAdapter(mQuestions, this);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
            }

            @Override
            public void onCardSwiped(Direction direction) {
                mCurrentQuestionCount++;
                if (mCurrentQuestionCount >= mQuestions.size()) {
                    mContinueBtn.setEnabled(false);
                    mSkipBtn.setEnabled(false);
                    mEndTV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCardRewound() {
            }

            @Override
            public void onCardCanceled() {
            }

            @Override
            public void onCardAppeared(View view, int position) {
            }

            @Override
            public void onCardDisappeared(View view, int position) {
            }
        });
        cardStackLayoutManager.setStackFrom(StackFrom.Bottom);
        cardStackLayoutManager.scrollToPosition(mCurrentQuestionCount);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        mCardsCSV.setLayoutManager(cardStackLayoutManager);
        mCardsCSV.setAdapter(mAdapter);

        mSkipBtn.setOnClickListener(v -> {
            mCardsCSV.smoothScrollToPosition(mCurrentQuestionCount + 1);
        });

        mContinueBtn.setOnClickListener(v -> {
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            if (user != null) {
                Answer answer = null;
                boolean shouldProceed = true;
                Question currentQuestion = mQuestions.get(mCurrentQuestionCount);
                if (currentQuestion instanceof SliderQuestion) {
                    answer = new Answer(currentQuestion.getTitle(), String.valueOf(mCurrentValueInSlider));
                } else if (currentQuestion instanceof InputQuestion) {
                    if (StringUtils.isEmpty(mCurrentAnswerForInput)) {
                        shouldProceed = false;
                        Toast.makeText(this, "Please provide a valid input", Toast.LENGTH_SHORT).show();
                    } else {
                        answer = new Answer(currentQuestion.getTitle(), mCurrentAnswerForInput);
                    }
                } else if (currentQuestion instanceof MultiSelectQuestion) {
                    String answerText = "";
                    for (int value : mSelectedOptionsForMultiSelect) {
                        answerText = answerText.concat(((MultiSelectQuestion) currentQuestion).getOptions().get(value).getTitle()).concat(", ");
                    }
                    if (answerText.isEmpty()) {
                        shouldProceed = false;
                        Toast.makeText(this, "Please select at least 1 option", Toast.LENGTH_SHORT).show();
                    } else {
                        answer = new Answer(currentQuestion.getTitle(), answerText);
                    }
                } else if (currentQuestion instanceof RadioQuestion) {
                    answer = new Answer(currentQuestion.getTitle(),
                            ((RadioQuestion) currentQuestion).getOptions().get(mSelectedOptionForSingleSelect).getTitle());
                }
                if (answer != null) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("personality_test");
                    String name = user.getDisplayName();
                    name = name == null ? "" : name;
                    myRef.child(user.getUid() + name).child("Question " + (mCurrentQuestionCount + 1)).setValue(answer);
                }
                if (shouldProceed) {
                    mCardsCSV.smoothScrollToPosition(mCurrentQuestionCount + 1);
                }
            }
        });
    }

    @Override
    public void inputFieldValueChanged(String value) {
        mCurrentAnswerForInput = value;
    }

    @Override
    public void sliderValueChanged(int currentValue) {
        mCurrentValueInSlider = currentValue;
    }

    @Override
    public void onOptionClicked(int position) {
        Question currentQuestion = mQuestions.get(mCurrentQuestionCount);
        if (currentQuestion instanceof MultiSelectQuestion) {
            if (((MultiSelectQuestion) currentQuestion).getOptions().get(position).isSelected()) {
                mSelectedOptionsForMultiSelect.remove(position);
                ((MultiSelectQuestion) currentQuestion).getOptions().get(position).setSelected(false);
            } else {
                mSelectedOptionsForMultiSelect.add(position);
                ((MultiSelectQuestion) currentQuestion).getOptions().get(position).setSelected(true);
            }
        } else if (currentQuestion instanceof RadioQuestion) {
            ((RadioQuestion) currentQuestion).getOptions().get(position).setSelected(true);
            mSelectedOptionForSingleSelect = position;
            for (int i = 0; i < ((RadioQuestion) currentQuestion).getOptions().size(); i++) {
                if (i != position) {
                    ((RadioQuestion) currentQuestion).getOptions().get(i).setSelected(false);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        mCardsCSV.scrollToPosition(mCurrentQuestionCount);
    }
}
