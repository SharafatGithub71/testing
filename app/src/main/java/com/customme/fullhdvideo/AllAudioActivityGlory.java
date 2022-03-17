package com.customme.fullhdvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.customme.fullhdvideo.helperClassesGlory.FileDataHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class AllAudioActivityGlory extends AppCompatActivity {

    ListView musiclist;
    Cursor musiccursor;

    int music_column_index;
    int count;
    static MediaPlayer mMediaPlayer;

    String filename;

    int duration;
    int name;
    private AdView mAdView;
    Handler handler = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allaudioactivity_glory);

        handler = new Handler();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (!SplashActivity.isPurchased) {
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        musiclist = (ListView) findViewById(R.id.audio_list);

        musiclist.setVisibility(View.VISIBLE);
        musiclist.setTextFilterEnabled(true);
        final ListAdapter listAdapter = musiclist.getAdapter();


        init_phone_music_grid();

    }


    @SuppressWarnings("deprecation")
    private void init_phone_music_grid() {

        // String MEDIA_PATH = new String("/sdcard/Music/");
        System.gc();
        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
        musiccursor.moveToFirst();
        count = musiccursor.getCount();

        musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
        //
        musiclist.setOnItemClickListener(musicgridlistener);
        mMediaPlayer = new MediaPlayer();
    }

    private AdapterView.OnItemClickListener musicgridlistener = new AdapterView.OnItemClickListener() {
        @SuppressWarnings("rawtypes")
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
    };


    /* Music Player Adapter*/

    public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            System.gc();

            // TextView tc=(TextView)findViewById(R.id.list_content);
            TextView tc = new TextView(this.mContext.getApplicationContext());
            tc.setTextColor(Color.parseColor("#8BC34A"));
            tc.setTextSize(12.0f);
            tc.setBackgroundColor(0);
            tc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.audio1, 0, 0, 0);
            tc.setGravity(16);
            String id = null;
            convertView = null;
            if (convertView == null) {
                music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                musiccursor.moveToPosition(position);
                id = musiccursor.getString(music_column_index);
                music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                musiccursor.moveToPosition(position);
                /*id += " Size(KB):" + musiccursor.getString(music_column_index);
                tc.setText(id);*/
                tc.setText(id + "\n \n Size: " + FileDataHelper.getFileSize(AllAudioActivityGlory.this.musiccursor.getLong(AllAudioActivityGlory.this.music_column_index)));
            } else
                tc = (TextView) convertView;
            return tc;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onBackPressed() {
        super.onBackPressed();
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

}
