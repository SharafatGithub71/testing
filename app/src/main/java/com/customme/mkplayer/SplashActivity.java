package com.customme.mkplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.customme.fullhdvideo.MainActivityGlory;
import com.customme.fullhdvideo.R;

public class SplashActivity extends AppCompatActivity {

    //    public InterstitialAd mInterstitialAd;
    Activity activity;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        setContentView(R.layout.activity_splash);

        TextView tvTitle = findViewById(R.id.tvTitleSplash);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Silent Asia - Personal Use.otf");
        tvTitle.setTypeface(custom_font);

//        InterstitialAdmob();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                    mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdClosed() {
//                            super.onAdClosed();
//
//                            Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//
//                } else {
                Intent intent = new Intent(SplashActivity.this, MainActivityGlory.class);
                startActivity(intent);
                finish();
//                }

            }
        }, 5000);

//        if (kxUtils.isMarshmallow()) checkPermissionAndThenLoad();
//        else {
//            AsyncTaskRunner runner = new AsyncTaskRunner();
//            runner.execute("");
//        }
    }

//    @Override
//    protected void loadEverything() {
//        AsyncTaskRunner runner = new AsyncTaskRunner();
//        runner.execute("");
//    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

//    public void InterstitialAdmob() {
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ad_interstitial));
//        mInterstitialAd.setAdListener(new AdListener() {
//
//            @Override
//            public void onAdLoaded() {
//                // TODO Auto-generated method stub
//                super.onAdLoaded();
//            }
//        });
//        requestNewInterstitial();
//    }
//
//    public void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);
//    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
//            VideoLoader videoLoader = new VideoLoader(SplashActivity.this);
//
//            videoLoader.loadDeviceVideos(new VideoLoadListener() {
//                @Override
//                public void onVideoLoaded(final ArrayList<VideoItem> items) {
//                    // first getting all videos list from memory
//                    GlobalVar.getInstance().allVideoItems = items;
//                    // send all videos list to FolderLoader for getting Folders List for that videos list
////                    folderItems = FolderLoader.getFolderList(items);
////                    // update folder adapter with folders list
////                    folderAdapter.updateData(folderItems);
//                }
//
//                @Override
//                public void onFailed(Exception e) {
//                    e.printStackTrace();
//                }
//            });
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
//            progressDialog.dismiss();
//            finalResult.setText(result);

            Intent intent = new Intent(SplashActivity.this, MainActivityGlory.class);
            startActivity(intent);
            finish();
        }


        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(SplashActivity.this,
//                    "Loading Data!",
//                    "Please wait!");
        }


        @Override
        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);

        }
    }

}