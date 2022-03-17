package com.customme.mkplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import com.customme.fullhdvideo.R;

public class PrivacyPolicyActivity extends Activity {

    WebView webView;
    String privacyPolicy = "file:///android_asset/privacy.html";

    ScrollView scrollView;
    CheckBox checkBoxPrivacy;
    Button btnPrivacyAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        TextView tvTitle = findViewById(R.id.tvTitlePrivacyScreen);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Silent Asia - Personal Use.otf");
        tvTitle.setTypeface(custom_font);

        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);

        boolean privacyPolicyAccepted = sh.getBoolean("accepted",false);
        if (privacyPolicyAccepted) {
            Intent intent = new Intent(PrivacyPolicyActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }


        btnPrivacyAccept = findViewById(R.id.PrivacyAcceptButton);
        btnPrivacyAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences
                        = getSharedPreferences("MySharedPref",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();
                myEdit.putBoolean("accepted", true);
                myEdit.apply();

                Intent intent = new Intent(PrivacyPolicyActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        checkBoxPrivacy = findViewById(R.id.PrivacyCheckBox);
//        checkBoxPrivacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    btnPrivacyAccept.setEnabled(true);
//                    btnPrivacyAccept.setTextColor(getResources().getColor(R.color.white));
//                    btnPrivacyAccept.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                } else {
//                    btnPrivacyAccept.setEnabled(false);
//                    btnPrivacyAccept.setTextColor(getResources().getColor(R.color.black));
//                    btnPrivacyAccept.setBackgroundColor(getResources().getColor(R.color.white));
//                }
//            }
//        });


        webView = findViewById(R.id.webView);
        webView.loadUrl(privacyPolicy);
//        scrollView = findViewById(R.id.webScrolView);
//        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (scrollView.getChildAt(0).getBottom()
//                        <= (scrollView.getHeight() + scrollView.getScrollY())) {
//                    Log.i("ScrolledView", "Down:");
//                    //scroll view is at bottom
//                    checkBoxPrivacy.setChecked(true);
//                    btnPrivacyAccept.setEnabled(true);
//                    btnPrivacyAccept.setTextColor(getResources().getColor(R.color.white));
//                    btnPrivacyAccept.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                } else {
////                    Log.i("ScrolledView","is:");
//                    //scroll view is not at bottom
////                    checkBoxPrivacy.setChecked(false);
////                    btnPrivacyAccept.setEnabled(false);
////                    btnPrivacyAccept.setTextColor(getResources().getColor(R.color.black));
////                    btnPrivacyAccept.setBackgroundColor(getResources().getColor(R.color.white));
//                }
////                if (!scrollView.canScrollVertically(1)) {
////                    Log.i("ScrolledView","is:");
////                    // bottom of scroll view
////                }
////                if (!scrollView.canScrollVertically(-1)) {
////                    Log.i("ScrolledView","is:");
////                    // top of scroll view
////                }
//            }
//        });


    }
}
