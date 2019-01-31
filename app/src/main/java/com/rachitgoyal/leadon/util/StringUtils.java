package com.rachitgoyal.leadon.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Rachit Goyal on 29/01/19.
 */
public class StringUtils {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
