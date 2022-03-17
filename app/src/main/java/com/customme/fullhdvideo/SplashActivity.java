package com.customme.fullhdvideo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private InterstitialAd mInterstitialAd;
    TextView tvAppName;

//    public static com.facebook.ads.InterstitialAd interstitialAdS;
    Handler handler = null;

    public static boolean isPurchased = false;

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

//        AdSettings.addTestDevice("29675634-1327-4a89-be7d-12ed219fbd08");

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void init() {
        SharedPreferences prefs = getSharedPreferences(MainActivityGlory.MY_PREFS_NAME, MODE_PRIVATE);
        boolean restoredText = prefs.getBoolean("purchasedVP", false);
//        interstitialAdS = new com.facebook.ads.InterstitialAd(this, "_2238328326193685");

        if (!restoredText) {
            isPurchased = false;

//            interstitialAdS.loadAd();



            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));

            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            isPurchased = true;
        }

        handler = new Handler();


        tvAppName = findViewById(R.id.tv_appNameSplash);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LakeGiles.otf");

        tvAppName.setTypeface(custom_font);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (!isPurchased) {


//                    showAdWithDelay();  now fb ad has blocked that's why  its comment and only admob ad is showing
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
                        startActivity(i);
                        finish();
                    }
                    mInterstitialAd.setAdListener(new AdListener() {
                                                      @Override
                                                      public void onAdClosed() {
                                                          super.onAdClosed();
                                                          Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
                                                          startActivity(i);
                                                          finish();
                                                      }
                                                  }
                    );


                } else {
                    Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }

    private void showad() {
        if (!isPurchased) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();

            } else {

                Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
                startActivity(i);
                finish();
            }
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {

                    Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
                    startActivity(i);
                    finish();

                }
            });
        } else {

            Intent i = new Intent(SplashActivity.this, MainActivityGlory.class);
            startActivity(i);
            finish();
        }
    }

    private void showAdWithDelay() {
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
//                if (interstitialAdS == null || !interstitialAdS.isAdLoaded()) {
////                    showad();
//                    if (mInterstitialAd.isLoaded()) {
//                        mInterstitialAd.show();
//                    }
//                    return;
//                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
//                if (interstitialAdS.isAdInvalidated()) {
//                    return;
//                }
//                // Show the ad
//                interstitialAdS.show();
            }
        }, 300); // Show the ad after 15 minutes
    }

}
