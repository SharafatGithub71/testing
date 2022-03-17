package com.customme.fullhdvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.customme.fullhdvideo.helperClassesGlory.FileDataHelper;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class AudioListActivityGlory extends AppCompatActivity {

    static MediaPlayer mMediaPlayer;
    private int backwardTime = 2000;
    int count;
    int duration;
    private Handler durationHandler = new Handler();
    String filename;
    private double finalTime = 0.0d;
    String folderPath = null;
    private int forwardTime = 2000;
    Bundle getBundle;
    private InterstitialAd interstitialAd;
    int music_column_index;
    Cursor musiccursor;
    private AdapterView.OnItemClickListener musicgridlistener = new C13351();
    ListView musiclist;
    int name;
    private AdView mAdView;

    class C13351 implements AdapterView.OnItemClickListener {
        C13351() {
        }

        public void onItemClick(AdapterView parent, View v, int position, long id) {
            music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            duration = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            name = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            musiccursor.moveToPosition(position);
            int totalcount = musiclist.getAdapter().getCount();

            SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("allsongs", totalcount);

            editor.putInt("currentpos", position);
            editor.commit();
            int dur = musiccursor.getInt(duration);
            int hrs = (dur / 3600000);
            int mns = (dur / 60000) % 60000;
            int scs = dur % 60000 / 1000;
            String songTime = String.format("%2d:%2d", mns, scs);
            // songsbar.setVisibility(View.VISIBLE);
            filename = musiccursor.getString(music_column_index);
            String namess = musiccursor.getString(name);
            Intent intent = new Intent(getApplicationContext(), AudioDetailsGlory.class);
            intent.putExtra("videofilename", filename);
            intent.putExtra("durations", songTime);
            intent.putExtra("name", namess);
            startActivity(intent);
        }
    }

//    class C13373 extends AdListener {
//        C13373() {
//        }
//
//        public void onAdLoaded() {
//            if (AudioListActivityGlory.this.interstitialAd.isLoaded()) {
//                AudioListActivityGlory.this.interstitialAd.show();
//            }
//        }
//    }

    public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
            this.mContext = c;
        }

        public int getCount() {
            return AudioListActivityGlory.this.count;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            System.gc();
            TextView tc = new TextView(this.mContext.getApplicationContext());
            tc.setTextColor(Color.parseColor("#8BC34A"));
            tc.setTextSize(12.0f);
            tc.setBackgroundColor(0);
            tc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.audio1, 0, 0, 0);
            tc.setGravity(16);
            if (convertView != null) {
                return (TextView) convertView;
            }
            AudioListActivityGlory.this.music_column_index = AudioListActivityGlory.this.musiccursor.getColumnIndexOrThrow("_display_name");
            AudioListActivityGlory.this.musiccursor.moveToPosition(position);
            String id = " " + AudioListActivityGlory.this.musiccursor.getString(AudioListActivityGlory.this.music_column_index);
            AudioListActivityGlory.this.music_column_index = AudioListActivityGlory.this.musiccursor.getColumnIndexOrThrow("_size");
            AudioListActivityGlory.this.musiccursor.moveToPosition(position);
            tc.setText(id + "\n \n Size: " + FileDataHelper.getFileSize(AudioListActivityGlory.this.musiccursor.getLong(AudioListActivityGlory.this.music_column_index)));
            return tc;
        }
    }
    NativeExpressAdView adView;
    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list_glory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        if (!SplashActivity.isPurchased) {
            InterstitialAdmob();

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
//            initiateNativeExpressAd();
        }
        this.getBundle = getIntent().getExtras();
        this.folderPath = this.getBundle.getString("FOLDER_PATH", null);
        this.musiclist = (ListView) findViewById(R.id.PhoneMusicList);
//        0 is for VISIBLE
//        4 is for INVISIBLE
//        8 is for GONE
        this.musiclist.setVisibility(View.VISIBLE);
        init_phone_music_grid();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init_phone_music_grid() {
        System.gc();
        String selection = null;
        String[] selectionArgs = null;
        if (this.folderPath != null) {
            selection = "_data like?";
            selectionArgs = new String[]{this.folderPath + "%"};
        }
        String[] proj = new String[]{"_id", "_data", "_display_name", "_size", "duration"};
        Cursor musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, selection, selectionArgs, null);
        MatrixCursor newCursor = new MatrixCursor(proj);
        if (musiccursor.moveToFirst()) {
            do {
                if (musiccursor.getString(musiccursor.getColumnIndex("_data")).equals(this.folderPath + "/" + musiccursor.getString(musiccursor.getColumnIndex("_display_name")))) {
                    newCursor.addRow(new Object[]{musiccursor.getString(musiccursor.getColumnIndex("_id")), musiccursor.getString(musiccursor.getColumnIndex("_data")), musiccursor.getString(musiccursor.getColumnIndex("_display_name")), musiccursor.getString(musiccursor.getColumnIndex("_size")), musiccursor.getString(musiccursor.getColumnIndex("duration"))});
                }
            } while (musiccursor.moveToNext());
        }
        this.musiccursor = newCursor;
        this.count = this.musiccursor.getCount();
        this.musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
        this.musiclist.setOnItemClickListener(this.musicgridlistener);
        mMediaPlayer = new MediaPlayer();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!SplashActivity.isPurchased) {
            InterstitialAdmob();
        }
    }

    public void initiateNativeExpressAd() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    public void InterstitialAdmob() {
        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        this.interstitialAd.loadAd(new AdRequest.Builder().build());
//        this.interstitialAd.setAdListener(new C13373());
    }


}

