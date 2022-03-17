package com.customme.fullhdvideo.privacy_screen;

import android.content.Context;
import android.content.SharedPreferences;

public class SharPreferences {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public SharPreferences(Context context) {
        prefs = context.getSharedPreferences("VideoPlayer", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }


    public void setPrivacy(boolean isTrue) {
        editor.putBoolean("isprivacy", isTrue);
        editor.commit();
    }

    public boolean getPrivacy() {
        return prefs.getBoolean("isprivacy", false);

    }
}
