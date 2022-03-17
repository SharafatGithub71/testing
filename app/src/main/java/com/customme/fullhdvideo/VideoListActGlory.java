package com.customme.fullhdvideo;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customme.fullhdvideo.dataGlory.Itemjamshaid;
import com.customme.fullhdvideo.database.VideoDatabase;
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideo;
import com.customme.fullhdvideo.view_controllersGlory.VideoAdapter;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.customme.fullhdvideo.database.historyVideos.VideosHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoListActGlory extends AppCompatActivity {

    static final String TITLE = "item_title";
    public VideoAdapter videoAdapter;
    int count;
    List<HashMap<String, Object>> data = new ArrayList();
    String folderPath = null;
    Bundle getBundle;
    String pos2;
    int resumePosition = 0;
    int snowDensity;
    ListView videolist;
    private List<Itemjamshaid> videoItemList;
    private SearchView searchView;
    private AdView mAdView;
    Context context;
    public static RecyclerView rvVideolist;
    VideoDatabase database;
    List<FavoriteVideo> listFavoriteVideos = null;
    List<VideosHistory> listHistoryVideos = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list_glory);
        Log.e("folderAct", "in onCreate VideoListActivity");

        videoItemList = new ArrayList<>();
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        database = VideoDatabase.Companion.getInstance(getApplication());
        LiveData<List<FavoriteVideo>> listFavoriteVideo = database.getFavoriteVideoDAO().getAllFavoriteVideos();
        listFavoriteVideo.observe(this, videos -> {
            listFavoriteVideos = videos;
            videoAdapter.listFavoriteVideos=videos;
            videoAdapter.notifyDataSetChanged();
            Log.e("AllVideoDatabaseTest", "videosFavorite: " + videos.size());
        });
        LiveData<List<VideosHistory>> getListHistoryVideos = database.getVideosHistoryDAO().getAllHistoryVideos();
        getListHistoryVideos.observe(this, videos -> {
            listHistoryVideos = videos;
            videoAdapter.listHistoryVideos=videos;
            videoAdapter.notifyDataSetChanged();
            Log.e("historyTest", "videosHistory: " + videos.size());


        });


        this.getBundle = getIntent().getExtras();
        this.folderPath = this.getBundle.getString("FOLDER_PATH", null);
        init_phone_video_grid();
        //initiateNativeExpressAd();
        if (!SplashActivity.isPurchased) {
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

    }

    private void init_phone_video_grid() {
        System.gc();
        String selection = null;
        String[] selectionArgs = null;
        if (this.folderPath != null) {
            selection = "_data like?";
            selectionArgs = new String[]{this.folderPath + "%"};
        }
        String[] proj = new String[]{"_id", "_data", "_display_name", "_size", "duration"};
        Cursor videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, selection, selectionArgs, null);
        MatrixCursor newCursor = new MatrixCursor(proj);
        Itemjamshaid videoItem;
        if (videocursor.moveToFirst()) {
            do {
                if (videocursor.getString(videocursor.getColumnIndex("_data")).equals(this.folderPath + "/" + videocursor.getString(videocursor.getColumnIndex("_display_name")))) {

                    videoItem = new Itemjamshaid();
                    videoItem.set_ID(videocursor.getString(0));
                    videoItem.setDATA(videocursor.getString(1));
                    videoItem.setDISPLAY_NAME(videocursor.getString(2));
                    videoItem.setSIZE(videocursor.getLong(3));
                    videoItem.setDURATION(videocursor.getString(4));
                    videoItemList.add(videoItem);
                }
            } while (videocursor.moveToNext());
        }
        if (this.videoItemList != null) {

            this.count = this.videoItemList.size();
        } else {
            Toast.makeText(VideoListActGlory.this, "No Videos Found!", Toast.LENGTH_SHORT).show();
        }
        if (this.count <= 0) {
            setResult(-1);
            onBackPressed();
            return;
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
        videoAdapter = new VideoAdapter(context,videoItemList,database,listFavoriteVideos,listHistoryVideos);
        rvVideolist.setAdapter(videoAdapter);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != 0) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            moveTaskToBack(true);
        } else {
            // InterstitialAdmob();
        }
    }

    protected void onResume() {
        super.onResume();
//        init_phone_video_grid();
    }

    protected void onPause() {
        super.onPause();
//        this.resumePosition = this.videolist.getFirstVisiblePosition();
    }

    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            case R.id.action_search:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.video_menu, popup.getMenu());
        popup.show();
    }
}


