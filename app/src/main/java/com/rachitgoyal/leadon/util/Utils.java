package com.rachitgoyal.leadon.util;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rachitgoyal.leadon.model.InputQuestion;
import com.rachitgoyal.leadon.model.MultiSelectQuestion;
import com.rachitgoyal.leadon.model.Option;
import com.rachitgoyal.leadon.model.Question;
import com.rachitgoyal.leadon.model.RadioQuestion;
import com.rachitgoyal.leadon.model.SliderQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class Utils {

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static List<Question> generateQuestionsList() {

        List<Question> questions = new ArrayList<>();

        SliderQuestion ageQuestion = new SliderQuestion();
        ageQuestion.setTitle("What is your age?");
        ageQuestion.setImageURL("https://img-d02.moneycontrol.co.in/news_image_files/2017/356x200/A/Age-1_356.jpg");
        ageQuestion.setDescription("Your age will help us figure out a better course fit for you. Different age groups require different levels of " +
                "maturity in the courses that are best suited to them.");
        ageQuestion.setStart(10);
        ageQuestion.setEnd(80);
        ageQuestion.setChunkSize(1);
        questions.add(ageQuestion);

        InputQuestion designationQuestion = new InputQuestion();
        designationQuestion.setTitle("What is your designation?");
        designationQuestion.setImageURL("https://www.cmmonline.com/images/content/articles/Hello-My-Name-Is.jpg");
        designationQuestion.setHint("Ex: Software Engineer, Doctor, etc.");
        designationQuestion.setInputType(InputType.TYPE_CLASS_TEXT);
        questions.add(designationQuestion);

        SliderQuestion experienceQuestion = new SliderQuestion();
        experienceQuestion.setTitle("How many years of experience do you have?");
        experienceQuestion.setDescription("");
        experienceQuestion.setImageURL("https://cdn-images-1.medium.com/max/863/1*c_hvji8LXxqf0Iewdh4uSA.jpeg");
        experienceQuestion.setStart(0);
        experienceQuestion.setEnd(40);
        experienceQuestion.setChunkSize(5);
        questions.add(experienceQuestion);

        MultiSelectQuestion fieldQuestion = new MultiSelectQuestion();
        fieldQuestion.setTitle("Which of these areas are you interested in? (Can select multiple)");

        List<Option> fieldOptions = new ArrayList<>();
        Option sdOption = new Option(1, "Software Development", "", "https://code.fb.com/wp-content/themes/code-fb-com/img/facebook-developers" +
                ".png", false);
        fieldOptions.add(sdOption);
        Option doctorOption = new Option(2, "Medicine", "", "https://pbs.twimg.com/profile_images/853612004694609920/4W8yRDJA_400x400.jpg", false);
        fieldOptions.add(doctorOption);
        Option scienceOption = new Option(3, "Science", "", "https://www.class-central.com/bundles/classcentralsite/images/icon-science.png", false);
        fieldOptions.add(scienceOption);
        Option danceOption = new Option(4, "Dance", "", "https://www.dancecenterofspokane.com/wp-content/uploads/2017/08/contact_dcos.jpg", false);
        fieldOptions.add(danceOption);
        fieldQuestion.setOptions(fieldOptions);
        questions.add(fieldQuestion);

        RadioQuestion politicsQuestion = new RadioQuestion();
        politicsQuestion.setTitle("Which political party do you support? (Select one)");
        List<Option> politicsOptions = new ArrayList<>();
        Option bjbOption = new Option(1, "BJP", "", "https://pbs.twimg.com/profile_images/812603870153097216/exfOAb1p_400x400.jpg", false);
        politicsOptions.add(bjbOption);
        Option congressOption = new Option(2, "Congress", "", "https://pbs.twimg.com/profile_images/928652764976103424/RxLRRZNu_400x400.jpg", false);
        politicsOptions.add(congressOption);
        Option aapOption = new Option(3, "Aam Aadmi Party", "", "https://pbs.twimg.com/profile_images/601661373488111616/N0JTdGTn_400x400.jpg",
                false);
        politicsOptions.add(aapOption);
        Option othersOption = new Option(4, "Others", "", "https://pbs.twimg.com/profile_images/1568837163/politics_bw_400x400.jpg", true);
        politicsOptions.add(othersOption);
        politicsQuestion.setOptions(politicsOptions);
        questions.add(politicsQuestion);

        return questions;
    }
}
