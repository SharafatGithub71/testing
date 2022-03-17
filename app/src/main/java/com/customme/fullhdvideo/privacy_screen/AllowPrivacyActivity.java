package com.customme.fullhdvideo.privacy_screen;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.customme.fullhdvideo.R;
import com.customme.mkplayer.SplashActivity;

public class AllowPrivacyActivity extends Activity {
    boolean isPrivacy = false;
    SharPreferences sharedpref_obj;

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("PrivacyScreenTest","on Pause IsPrivacy: "+isPrivacy);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("PrivacyScreenTest","Resume IsPrivacy: "+isPrivacy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("PrivacyScreenTest"," On Create IsPrivacy: "+isPrivacy);
//        AdSettings.addTestDevice("5c36bb57-99a4-4d6e-85aa-bb33af4d63ad");
        sharedpref_obj = new SharPreferences(this);
        isPrivacy= sharedpref_obj.getPrivacy();
        if (isPrivacy) {
            startActivity(new Intent(AllowPrivacyActivity.this, SplashActivity.class));
            finish();

        }

        setContentView(R.layout.activity_allow_privacy);

//        final Button btnCheck=findViewById(R.id.btn_check);
//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isPrivacy)
//                {
//                 isPrivacy=false;
//                    btnCheck.setBackgroundResource(R.drawable.cb_off);
//                }
//                else
//                {
//                    isPrivacy=true;
//                    btnCheck.setBackgroundResource(R.drawable.cb_on);
//                }
//            }
//        });

        TextView tvPrivacy = (TextView) findViewById(R.id.tv_privacy);
        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1OrPVszqRCq_0Qik4O3tYh9qq5wdmum6XwAV2uvL5Ktg/edit?usp=sharing"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void StartPriv(View v) {

        {
            sharedpref_obj.setPrivacy(true);
            startActivity(new Intent(AllowPrivacyActivity.this, SplashActivity.class));
            finish();
        }


    }
}
