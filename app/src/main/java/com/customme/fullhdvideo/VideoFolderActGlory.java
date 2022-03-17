package com.customme.fullhdvideo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.customme.fullhdvideo.dataGlory.MediaFolder;
import com.customme.fullhdvideo.view_controllersGlory.FolderAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.Iterator;

public class VideoFolderActGlory extends AppCompatActivity {

    int count;
    ListView folderList;
    String folderType;
    Bundle getBundle;

    private Cursor mediaCursor;
    int resumePosition = 0;
    int snowDensity;


    Handler handler = null;
    private AdView mAdView;

//    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
//    private NativeBannerAd nativeBannerAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videofolderactivity_glory);
        handler = new Handler();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.snowDensity = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("SNOW_DENSITY", 0);
        this.getBundle = getIntent().getExtras();
        this.folderType = this.getBundle.getString("MEDIA_TYPE", "video");


        if (!SplashActivity.isPurchased) {
//            mAdView = findViewById(R.id.adView);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mAdView.loadAd(adRequest);
//            loadNativeAd();
        }

        this.folderList = findViewById(R.id.folderListView);
    }

    private void init_phone_music_grid() {
        System.gc();
        this.mediaCursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data", "_display_name", "_size"}, null, null, null);
        this.count = this.mediaCursor.getCount();
        setMediaFolderData();
    }

    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
//        nativeBannerAd = new NativeBannerAd(this, getString(R.string.fb_native_banner_folder_audio));

//        nativeBannerAd.setAdListener(new NativeAdListener() {
//            @Override
//            public void onMediaDownloaded(Ad ad) {
//                // Native ad finished downloading all assets
////                Log.e(TAG, "Native ad finished downloading all assets.");
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Native ad failed to load
////                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Race condition, load() called again before last ad was displayed
//                if (nativeBannerAd == null || nativeBannerAd != ad) {
//                    return;
//                }
//                // Inflate Native Banner Ad into Container
//                inflateAd(nativeBannerAd);
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Native ad clicked
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // Native ad impression
//
//            }
//        });

        // Request an ad
//        nativeBannerAd.loadAd();
    }

    private void init_phone_video_grid() {
        System.gc();
        this.mediaCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data", "_display_name", "_size", "duration"}, null, null, null);
        this.count = this.mediaCursor.getCount();
        setMediaFolderData();
    }

    private void setMediaFolderData() {
        ArrayList<MediaFolder> mediaFolders = new ArrayList();
        if (this.mediaCursor != null && this.count > 0) {
            int i = 0;
            while (this.mediaCursor.moveToNext()) {
                String data;
                String fileName;
                MediaFolder mediaFolder = new MediaFolder();
                if (this.folderType.contains("video")) {
                    data = this.mediaCursor.getString(this.mediaCursor.getColumnIndex("_data"));
                    fileName = this.mediaCursor.getString(this.mediaCursor.getColumnIndex("_display_name"));
                } else {
                    data = this.mediaCursor.getString(this.mediaCursor.getColumnIndex("_data"));
                    fileName = this.mediaCursor.getString(this.mediaCursor.getColumnIndex("_display_name"));
                }
                mediaFolder.setPath(data.replace("/" + fileName, ""));
                mediaFolder.setDisplayName(mediaFolder.getPath().substring(mediaFolder.getPath().lastIndexOf("/") + 1));
                boolean check = false;
                Iterator it = mediaFolders.iterator();
                while (it.hasNext()) {
                    MediaFolder media_Folder = (MediaFolder) it.next();
                    if (media_Folder.getPath().equals(mediaFolder.getPath())) {
                        media_Folder.setNumberOfMediaFiles(media_Folder.getNumberOfMediaFiles() + 1);
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    i++;
                    mediaFolders.add(mediaFolder);
                }
            }
        }
        if (mediaFolders == null || mediaFolders.size() <= 0) {
            this.folderList.setVisibility(View.GONE);
            findViewById(R.id.empty_message).setVisibility(View.VISIBLE);
            return;
        }
        findViewById(R.id.empty_message).setVisibility(View.GONE);
        this.folderList.setVisibility(View.VISIBLE);
        this.folderList.setAdapter(new FolderAdapter(this, R.layout.folder_list_item_glory, mediaFolders, this.folderType));
        if (this.resumePosition > 0 && this.resumePosition < mediaFolders.size()) {
            this.folderList.setSelection(this.resumePosition);
        }
    }
//    private void inflateAd(NativeBannerAd nativeAd) {
//
//        nativeBannerAd.unregisterView();
//
//        // Add the Ad view into the ad container.
//        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
//        LayoutInflater inflater = LayoutInflater.from(VideoFolderActGlory.this);
//        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
//        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
//        nativeAdLayout.addView(adView);
//
//        // Add the AdChoices icon
//        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
//        AdOptionsView adOptionsView = new AdOptionsView(VideoFolderActGlory.this, nativeBannerAd, nativeAdLayout);
//        adChoicesContainer.removeAllViews();
//        adChoicesContainer.addView(adOptionsView, 0);
//
//        // Create native UI using the ad metadata.
//        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
//        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
//        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
//        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
//        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
//
//        // Set the Text.
//        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
//        nativeAdCallToAction.setVisibility(
//                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
//        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
//        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
//        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());
//
//        // Register the Title and CTA button to listen for clicks.
//        List<View> clickableViews = new ArrayList<>();
//        clickableViews.add(nativeAdTitle);
//        clickableViews.add(nativeAdCallToAction);
//        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
//
//    }
    public void initiateNativeExpressAd() {
        final NativeExpressAdView mAdView = (NativeExpressAdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            public void onAdClosed() {
                super.onAdClosed();
                mAdView.loadAd(new AdRequest.Builder().build());
            }

            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        if (this.folderType.contains("video")) {
            init_phone_video_grid();
        } else {
            init_phone_music_grid();
        }
    }

    protected void onPause() {
        super.onPause();
        this.resumePosition = this.folderList.getFirstVisiblePosition();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == -1) {
            this.resumePosition = this.folderList.getFirstVisiblePosition();
            if (this.folderType.contains("video")) {
                init_phone_video_grid();
            } else {
                init_phone_music_grid();
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Music Folders");
                getSupportActionBar().setTitle("Music Folders");
            }
        }
    }
}
