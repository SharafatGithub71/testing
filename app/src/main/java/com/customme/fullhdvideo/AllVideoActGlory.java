package com.customme.fullhdvideo;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customme.fullhdvideo.dataGlory.Itemjamshaid;
import com.customme.fullhdvideo.dataGlory.Queryjamshaid;
import com.customme.fullhdvideo.database.VideoDatabase;
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideo;
import com.customme.fullhdvideo.database.historyVideos.VideosHistory;
import com.customme.fullhdvideo.view_controllersGlory.VideoAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.List;

public class AllVideoActGlory extends AppCompatActivity {

    String pos2;
    public static RecyclerView rvVideolist;
    private Queryjamshaid mediaQuery;
    public List<Itemjamshaid> videoItemList;
    private SearchView searchView;
    public VideoAdapter videoAdapter;

    public static final int NUMBER_OF_ADS = 1;
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    private AdLoader adLoader;
    Context context;

    private FrameLayout adContainerView;
    private AdView adView;
    public VideoDatabase database;
    public List<FavoriteVideo> listFavoriteVideos = null;
    public List<VideosHistory> listHistoryVideos = null;

    String listType = "all";
    private InterstitialAd interstitialAd;
    public com.google.android.gms.ads.InterstitialAd interstitialAdm;

    public void InterstitialAdmob_Load() {
        interstitialAdm = new com.google.android.gms.ads.InterstitialAd(this);
        interstitialAdm.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        requestNewInterstitial();
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAdm.loadAd(adRequest);
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allvideoactivity_glory);


        context = this;
        videoItemList = new ArrayList<>();

        mediaQuery = new Queryjamshaid(AllVideoActGlory.this);
        listType = getIntent().getExtras().getString("value", "all");
        database = VideoDatabase.Companion.getInstance(getApplication());
        getAllVideos();

        LiveData<List<FavoriteVideo>> listFavoriteVideo = database.getFavoriteVideoDAO().getAllFavoriteVideos();
        listFavoriteVideo.observe(this, videos -> {
            listFavoriteVideos = videos;
            List<Itemjamshaid> list;
            videoAdapter.listFavoriteVideos = videos;

            list = getFavoriteVideoList();
            if (listType.equals("favorite")) {
                videoAdapter.videoItemList = list;
                videoAdapter.videoListFiltered = list;
            }
            videoAdapter.notifyDataSetChanged();
            Log.e("AllVideoDatabaseTest", "videosFavorite: " + videos.size());
        });
        LiveData<List<VideosHistory>> getListHistoryVideos = database.getVideosHistoryDAO().getAllHistoryVideos();
        getListHistoryVideos.observe(this, videos -> {
            listHistoryVideos = videos;
            videoAdapter.listHistoryVideos = videos;
            List<Itemjamshaid> list;
            list = getHistoryVideoList();
            if (listType.equals("history")) {
                videoAdapter.videoItemList = list;
                videoAdapter.videoListFiltered = list;

                Log.e("historyTest", "(listType.equals(\"history\"))");
            }

            videoAdapter.notifyDataSetChanged();
            Log.e("historyTest", "videosHistory: " + videos.size());


        });


//        loadNativeAds();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adContainerView = findViewById(R.id.ad_view_container);

        if (!SplashActivity.isPurchased) {
            adContainerView.post(this::loadBanner);
//            loadFbAd();
            InterstitialAdmob_Load();
        }
    }

//    private void loadFbAd() {
//        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.video_interstitial_fb));
//        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//                // Interstitial ad displayed callback
//                //  Log.e(TAG, "Interstitial ad displayed.");
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                // Interstitial dismissed callback
//                //  Log.e(TAG, "Interstitial ad dismissed.");
//
//                finish();
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Ad error callback
//                //  Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Interstitial ad is loaded and ready to be displayed
//                // Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
//                // Show the ad
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Ad clicked callback
//                // Log.d(TAG, "Interstitial ad clicked!");
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // Ad impression logged callback
//                // Log.d(TAG, "Interstitial ad impression logged!");
//            }
//        };
//
//        // For auto play video ads, it's recommended to load the ad
//        // at least 30 seconds before it is shown
//        interstitialAd.loadAd(
//                interstitialAd.buildLoadAdConfig()
//                        .withAdListener(interstitialAdListener)
//                        .build());
//
//    }

    private void showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        // Handler handler = new Handler();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // Check if interstitialAd has been loaded successfully
//                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
//                    if (interstitialAdm.isLoaded()){
//                        interstitialAdm.show();
//                    }
//                    finish();
//                    return;
//                }
//                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
//                if (interstitialAd.isAdInvalidated()) {
//                    if (interstitialAdm.isLoaded()){
//                        interstitialAdm.show();
//                    }
//                    finish();
//                    return;
//                }
//                // Show the ad
//                interstitialAd.show();
//            }
//        }, 1); // Show the ad after 15 minutes

        if (interstitialAdm.isLoaded()){
            interstitialAdm.show();
        }
        finish();
    }

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

        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

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

    public void getAllVideos() {
        System.gc();
        videoItemList.clear();
        if (listType.equals("all")) {

            videoItemList = mediaQuery.getAllVideo();
        } else if (listType.equals("favorite")) {
            videoItemList = getFavoriteVideoList();
        } else if (listType.equals("history")) {

            videoItemList = getHistoryVideoList();
        } else {
            videoItemList = mediaQuery.getAllVideo();
        }
        rvVideolist = findViewById(R.id.rv_allvideos);
        rvVideolist.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVideolist.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemDecor.setDrawable(getDrawable(R.drawable.divider));
            rvVideolist.addItemDecoration(itemDecor);
        }
        videoAdapter = new VideoAdapter(context, videoItemList, database, listFavoriteVideos, listHistoryVideos);
        rvVideolist.setAdapter(videoAdapter);
        Log.e("AllVideoDatabaseTest", " getAllVideo()");
//        rvVideolist.setOnItemClickListener(videogridlistener);
//        rvVideolist.addOnItemTouchListener(new RecyclerItemClickListener(this, rvVideolist, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                int id = view.getId();
//                switch (id) {
//                    case R.id.imgBtnListMore:
//                        showPopup(view);
//                        break;
//                    default:
////                        onItemClickPerform(position);
//                }
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
    }

    public List<Itemjamshaid> getFavoriteVideoList() {
        List<Itemjamshaid> listVideoItems = new ArrayList<>();
        Log.e("AllVideoDatabaseTest", " getFavoriteVideoList()");
        Itemjamshaid videoItem;
        if (listFavoriteVideos == null || listFavoriteVideos.size() < 1) {
            Log.e("AllVideoDatabaseTest", " (listFavoriteVideos == null || listFavoriteVideos.size() < 1)");
            return null;
        }

        for (FavoriteVideo favoriteVideo : listFavoriteVideos) {
            videoItem = new Itemjamshaid();
            videoItem.setDATA(favoriteVideo.getVideoPath());
            videoItem.setDISPLAY_NAME(favoriteVideo.getVideoName());
            videoItem.setDURATION(favoriteVideo.getVideoDuration());
            videoItem.setSIZE(favoriteVideo.getVideoSize());
            listVideoItems.add(videoItem);
        }
        Log.e("AllVideoDatabaseTest", " listVideoItems size: " + listVideoItems.size());

        return listVideoItems;
    }

    public List<Itemjamshaid> getHistoryVideoList() {
        List<Itemjamshaid> listVideoItems = new ArrayList<>();
        if (listHistoryVideos == null || listHistoryVideos.size() < 1) {
            Log.e("AllVideoDatabaseTest", " (listHistoryVideos == null || listHistoryVideos.size() < 1)");
            return null;
        }

        Itemjamshaid videoItem;
        for (VideosHistory historyVideo : listHistoryVideos) {
            videoItem = new Itemjamshaid();
            videoItem.setDATA(historyVideo.getPath());
            videoItem.setDISPLAY_NAME(historyVideo.getVideoName());
            videoItem.setDURATION(historyVideo.getVideoDuration());
            videoItem.setSIZE(historyVideo.getVideoSize());
            listVideoItems.add(videoItem);
        }
        Log.e("AllVideoDatabaseTest", " history listVideoItems size: " + listVideoItems.size());
        return listVideoItems;
    }

    public void onBackPressed() {
//        super.onBackPressed();
        showAdWithDelay();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
//            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_all_videos, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                videoAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                videoAdapter.filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            case R.id.action_search:
                return true;
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//        if (id == R.id.home) {
//
//
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.native_ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                            videoAdapter.notifyDataSetChanged();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }

    private void insertAdsInMenuItems() {
        int index = 3;
        if (mNativeAds.size() <= 0) {
            return;
        } else if (videoItemList.size() <= 0) {
//            videoItemList.add(0, mNativeAds.get(0));
        } else if (videoItemList.size() >= 4) {


            int offset = (videoItemList.size() / mNativeAds.size()) + 1;

            for (UnifiedNativeAd ad : mNativeAds) {
//                videoItemList.add(index, ad);
                index = index + offset;
            }
        }
//        loadMenu();
    }
}
