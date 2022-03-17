package com.customme.fullhdvideo;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.customme.fullhdvideo.exitglories.MyDialogFragmentGlories;
import com.customme.fullhdvideo.exitglories.RecyclerViewAdapterGlories;
import com.customme.fullhdvideo.exitglories.mediaPlayerCallback;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;

public class MainActivityGlory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, mediaPlayerCallback {

    private static final String PRODUCT_ID = "remove_ads_video_player";
    //    private static final String PRODUCT_ID = "android.test.purchased";
    //    private static final String PRODUCT_ID = "android.test.purchased";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsN/0TcuIeTRToHBkuDSz5ZbwPtSDxUNc3zaapeAx1XWY/rMZHLatCWQN93RLFYp8NgNTlq3sOD4px87Q43bdFEj1PT92R+tBtPPGJlSY19h9Z9z1fy5prfiGpKk0CRa32CPyvsmMaqBj1kw2dzXj+UbCvFRupAM8KVRSfMqEYIF0k9f8t1ctUUA0XfCqyK7IhZJ/mP6AGVzyWSuZtenH2dhijlYUup22ue7rRNw1wwBRSibwIKn79vq71/3W4KR+hN8D1rufEDXFfegIHuNxrj3EFWIQe8gxX9CPDAE6DAwE0BbCERaWm5rSC4FdE7fPdMSjg75fKlANmLhqs1klIQIDAQAB"; // PUT YOUR MERCHANT KEY HERE;

    private static final String MERCHANT_ID = "04835063771372997340";
//    private BillingProcessor bp;
    private boolean readyToPurchase = false;
    private static final String LOG_TAG = "iabv3";
    public static final String MY_PREFS_NAME = "PurchasedHistoryVP";


    LinearLayout lv_videoall, lv_video_folder, lv_allaudio, lv_audiofolder,ll_history,ll_favorite;

    public InterstitialAd interstitialAd;
    int adchooser;
    RecyclerViewAdapterGlories rcAdapter;
    private LinearLayout nativeAdContainer;

    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    /////////////////////////////////////////

    /////////////////////////////////  Permissions
    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public final int PERMISSION_ALL = 1;
    /////////////////////////////////  Permissions

    private FrameLayout adContainerView;
    private AdView adView;
    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        adView = new AdView(this);
        adView.setAdUnitId((getResources().getString(R.string.banner_ad_unit_id)));
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

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

        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_glory);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("FC92B750E76B5A127AA430DA676FC998"));
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        if (!BillingProcessor.isIabServiceAvailable(this)) {
//            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
//        }

//        bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
//            @Override
//            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
////                showToast("onProductPurchased: " + productId);
//                Log.e("removeAds", "onPurchased");
//
//                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                editor.putBoolean("purchasedVP", true);
//                editor.apply();
//                SplashActivity.isPurchased = true;
//            }
//
//            @Override
//            public void onBillingError(int errorCode, @Nullable Throwable error) {
//
////                showToast("onBillingError: " + Integer.toString(errorCode));
//            }
//
//            @Override
//            public void onBillingInitialized() {
////                showToast("onBillingInitialized");
//                readyToPurchase = true;
//
//            }
//
//            @Override
//            public void onPurchaseHistoryRestored() {
////                showToast("onPurchaseHistoryRestored");
//                for (String sku : bp.listOwnedProducts())
//                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
//                for (String sku : bp.listOwnedSubscriptions())
//                    Log.d(LOG_TAG, "Owned Subscription: " + sku);
//
//            }
//        });
        //////////////////////////////////////////////////////////////////////////////////////
        adContainerView = findViewById(R.id.ad_view_container);
        if (!SplashActivity.isPurchased) {
            InterstitialAdmob_Load();
                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBanner();
                    }
                });

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    switch (adchooser) {
                        case 1: {
                            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                startAllVideoActivity("all");
                            }
                        }
                        break;
                        case 2: {
                            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                                in.putExtra("MEDIA_TYPE", "video");
                                startActivity(in);
                            }
                        }
                        break;
                        case 3: {
                            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                Intent in = new Intent(getApplicationContext(), AllAudioActivityGlory.class);
                                startActivity(in);
                            }
                        }
                        break;
                        case 4: {
                            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                                in.putExtra("MEDIA_TYPE", "audio");
                                startActivity(in);
                            }
                        }
                        break;


                    }
                    super.onAdClosed();
                }
            });

        }

        lv_videoall = findViewById(R.id.all_video);
        lv_video_folder = findViewById(R.id.all_video_folder);
        lv_allaudio = findViewById(R.id.all_audio);
        lv_audiofolder = findViewById(R.id.all_audio_folder);
        ll_history = findViewById(R.id.ll_history);
        ll_favorite = findViewById(R.id.ll_favorite);


        lv_videoall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adchooser = 1;

//                if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
//                    ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
//                } else {
//
//
//                    Intent in = new Intent(getApplicationContext(), AllVideoActGlory.class);
//                    startActivity(in);
//
//                }

                if (!SplashActivity.isPurchased) {
//                    if (interstitialAd.isLoaded()) {
//                        interstitialAd.show();
//                    } else {
                        if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                        } else {

                            startAllVideoActivity("all");

                        }
//                    }
                } else {
                    if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                    } else {


                        startAllVideoActivity("all");

                    }
                }

            }
        });
        lv_video_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    adchooser = 2;
                    if (!SplashActivity.isPurchased) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                            in.putExtra("MEDIA_TYPE", "video");
                            startActivity(in);
                        }
                    } else {
                        Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                        in.putExtra("MEDIA_TYPE", "video");
                        startActivity(in);
                    }
                }
            }
        });

        lv_allaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adchooser = 3;


                if (!SplashActivity.isPurchased) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                        } else {

                            Intent in = new Intent(getApplicationContext(), AllAudioActivityGlory.class);
                            startActivity(in);

                        }
                    }
                } else {
                    if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                    } else {

                        Intent in = new Intent(getApplicationContext(), AllAudioActivityGlory.class);
                        startActivity(in);

                    }
                }
            }
        });

        lv_audiofolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adchooser = 4;
                if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    if (!SplashActivity.isPurchased) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                            in.putExtra("MEDIA_TYPE", "audio");
                            startActivity(in);
                        }
                    } else {
                        Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                        in.putExtra("MEDIA_TYPE", "audio");
                        startActivity(in);
                    }
                }
            }
        });

        ll_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
//                    ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
//                } else {
//
//
//                    Intent in = new Intent(getApplicationContext(), AllVideoActGlory.class);
//                    startActivity(in);
//
//                }
                        if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                        } else {
                            startAllVideoActivity("favorite");
                        }

            }
        });
        ll_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
//                    ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
//                } else {
//
//
//                    Intent in = new Intent(getApplicationContext(), AllVideoActGlory.class);
//                    startActivity(in);
//
//                }


                        if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
                        } else {

                            startAllVideoActivity("history");

                        }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void startAllVideoActivity(String extra) {
        Intent in = new Intent(getApplicationContext(), AllVideoActGlory.class);
        in.putExtra("value",extra);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_remove_ads) {

            if (!readyToPurchase) {
//                showToast("Billing not initialized.");

            } else {
//                bp.purchase(this, PRODUCT_ID);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            showExitDialog();
            Intent intent = new Intent(MainActivityGlory.this, ExitActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_allvideo) {

            adchooser = 1;
            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
            } else {
//                if (!SplashActivity.isPurchased) {
//                    if (interstitialAd.isLoaded()) {
//                        interstitialAd.show();
//                    } else {
//                        startAllVideoActivity("all");
//                    }
//                } else {
                    startAllVideoActivity("all");
//                }
            }

        } else if (id == R.id.nav_videofolder) {

            adchooser = 2;
            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
            } else {

                Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                in.putExtra("MEDIA_TYPE", "video");
                startActivity(in);
            }

        } else if (id == R.id.nav_allaudio) {

            adchooser = 3;
            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
            } else {
                if (!SplashActivity.isPurchased) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent in = new Intent(getApplicationContext(), AllAudioActivityGlory.class);
                        startActivity(in);
                    }
                } else {
                    Intent in = new Intent(getApplicationContext(), AllAudioActivityGlory.class);
                    startActivity(in);
                }
            }

        } else if (id == R.id.nav_audiofolder) {

            adchooser = 4;
            if (!hasPermissions(MainActivityGlory.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivityGlory.this, PERMISSIONS, PERMISSION_ALL);
            } else {

                Intent in = new Intent(getApplicationContext(), VideoFolderActGlory.class);
                in.putExtra("MEDIA_TYPE", "audio");
                startActivity(in);

            }

        } else if (id == R.id.nav_privacy) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1OrPVszqRCq_0Qik4O3tYh9qq5wdmum6XwAV2uvL5Ktg/edit?usp=sharing"));
                startActivity(browserIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Play your music videos of all format.\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_more) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("market://search?q=pub:GloryApps")));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/developer?id=GloryApps")));
            }

        } else if (id == R.id.nav_rate) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://play.google.com/store/apps/details?id="
                                + getPackageName())));
            }

        } else if (id == R.id.nav_remove_ads) {
            if (!readyToPurchase) {
//                showToast("Billing not initialized.");

            } else {
//                bp.purchase(this, PRODUCT_ID);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        requestNewInterstitial();
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }


    @Override
    public void mediaPlayerPosition(int position) {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse(appsUrls[position]));
//        startActivity(browserIntent);
    }

    private void showExitDialog() {
        if (!SplashActivity.isPurchased) {
            android.app.FragmentManager fm = getFragmentManager();
            MyDialogFragmentGlories dialogFragment = new MyDialogFragmentGlories(rcAdapter);
            dialogFragment.show(fm, "");
        } else {
            showExitDiloge();
        }
    }

    public void showExitDiloge() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exit_dialoge_gloree);

        TextView resumeVideoTitle = (TextView) dialog.findViewById(R.id.tvSongTitle);

        Button btnStartOver = (Button) dialog.findViewById(R.id.btnYes);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // if button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });


        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {

//        if (bp != null)
//            bp.release();


        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = getSharedPreferences(MainActivityGlory.MY_PREFS_NAME, MODE_PRIVATE);
        boolean restoredText = prefs.getBoolean("purchasedVP", false);
        if (!restoredText) {
            SplashActivity.isPurchased = false;


        } else {
            SplashActivity.isPurchased = true;
        }

        super.onResume();
    }
}
