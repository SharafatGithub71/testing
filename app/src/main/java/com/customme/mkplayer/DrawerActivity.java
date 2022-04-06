package com.customme.mkplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.customme.fullhdvideo.MainActivityGlory;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public InterstitialAd mInterstitialAd;
    Activity activity;

    private FrameLayout adContainerView;
    private AdView adView;

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        adView = new AdView(this);
        adView.setAdUnitId((getResources().getString(R.string.ad_banner)));
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest =
                new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        activity = this;

        TextView tvTitle = findViewById(R.id.tvTitleMainScreen);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Silent Asia - Personal Use.otf");
        tvTitle.setTypeface(custom_font);

        InterstitialAdmob();
//        findViewById(R.id.btn_start);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        adContainerView = findViewById(R.id.ad_view_container);
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
        }
    }

    public void showExitDialog() {

        new MaterialAlertDialogBuilder(DrawerActivity.this, R.style.MaterialAlertDialog_App)
                .setTitle("Exit?")
                .setMessage("App will be close.")
                .setCancelable(false)
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("Exit", (dialogInterface, i) -> {
                    finish();
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_video_player) {
            forwardClick(findViewById(R.id.btnFolders));
        } else if (id == R.id.nav_privacy) {
            DrawerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://docs.google.com/document/d/1cxlmPAVUJlEhRV9OdH84zXy1IrTlfEjoYGN2crLayDs/edit")));
        } else if (id == R.id.nav_moreapps) {
            AdsId adsUtill = new AdsId(DrawerActivity.this);
            AdsId.moreApp();
        } else if (id == R.id.nav_share) {
            AdsId adsUtill = new AdsId(DrawerActivity.this);
            AdsId.shareApp();
        } else if (id == R.id.nav_rate) {
            AdsId adsUtill = new AdsId(DrawerActivity.this);
            AdsId.rateUs();
            

        }
        else if (id == R.id.nav_setting) {
//            Intent mainAct = new Intent(DrawerActivity.this, SettingsActivity.class);
//            startActivity(mainAct);
        }
        else if (id == R.id.nav_barcode) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.qr.bar.code.scanner.bar.code.reader.prices.pro.free")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.qr.bar.code.scanner.bar.code.reader.prices.pro.free")));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void forwardClick(View v) {
        showAdmob("folder");
        //        if (mInterstitialAd.isLoaded()) {
        //            mInterstitialAd.show();
        //            mInterstitialAd.setAdListener(new AdListener() {
        //                @Override
        //                public void onAdClosed() {
        //                    super.onAdClosed();
        //                    requestNewInterstitial();
        //
        //                }
        //            });
        //
        //        } else {
        //
        //            Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
        //            mainAct.putExtra("value", "folder");
        //            startActivity(mainAct);
        //        }
    }

    public void gridClick(View v) {
        showAdmob("grid");
        //        if (mInterstitialAd.isLoaded()) {
        //            mInterstitialAd.show();
        //            mInterstitialAd.setAdListener(new AdListener() {
        //                @Override
        //                public void onAdClosed() {
        //                    super.onAdClosed();
        //                    requestNewInterstitial();
        //                    Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
        //                    mainAct.putExtra("value", "grid");
        //                    startActivity(mainAct);
        //                }
        //            });
        //
        //        } else {
        //
        //            Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
        //            mainAct.putExtra("value", "grid");
        //            startActivity(mainAct);
        //        }

    }

    public void listClick(View v) {
        showAdmob("list");
        //        if (mInterstitialAd.isLoaded()) {
        //            mInterstitialAd.show();
        //            mInterstitialAd.setAdListener(new AdListener() {
        //                @Override
        //                public void onAdClosed() {
        //                    super.onAdClosed();
        //                    requestNewInterstitial();
        //                    Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
        //                    mainAct.putExtra("value", "list");
        //                    startActivity(mainAct);
        //                }
        //            });
        //
        //        } else {
        //
        //            Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
        //            mainAct.putExtra("value", "list");
        //            startActivity(mainAct);
        //        }

    }


    public void InterstitialAdmob() {


        AdRequest adRequest = new AdRequest.Builder().build();

//        InterstitialAd.load(this, getResources().getString(R.string.ad_interstitial), adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i("TAG", "onAdLoaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.i("TAG", loadAdError.getMessage());
//                        mInterstitialAd = null;
//                    }
//                });

    }

    public void showAdmob(String value) {
//        if (mInterstitialAd != null) {
//            Log.i("TAG", "if (mInterstitialAd != null)");
//            mInterstitialAd.show(DrawerActivity.this);
//            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    InterstitialAdmob();
//                    // Called when fullscreen content is dismissed.
//                    Intent mainAct = new Intent(DrawerActivity.this, FirstActivity.class);
//                    mainAct.putExtra("value", value);
//                    startActivity(mainAct);
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    // Called when fullscreen content failed to show.
//                    mInterstitialAd = null;
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    // Called when fullscreen content is shown.
//                    // Make sure to set your reference to null so you don't
//                    // show it a second time.
//                    mInterstitialAd = null;
//
//                }
//            });
//        } else {
            Log.i("TAG", "else (mInterstitialAd != null): " + value);
            Intent mainAct = new Intent(DrawerActivity.this, MainActivityGlory.class);
            mainAct.putExtra("value", value);
            startActivity(mainAct);
//        }


    }

}
