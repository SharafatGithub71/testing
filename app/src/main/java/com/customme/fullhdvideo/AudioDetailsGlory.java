package com.customme.fullhdvideo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.customme.fullhdvideo.audioVisualizerGlory.VisualizerView;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioDetailsGlory extends AppCompatActivity {


    private String filename;
    MediaPlayer mp;
    ImageView playy, pausee, rewind_btn, forward_btn,music_bg;
    String durations, name;
    TextView starttime, final_timing, tittle_name;
    private Handler durationHandler = new Handler();
    SeekBar music_seek;
    public InterstitialAd interstitialAd;
    private double startTime = 0;
    private double finalTime = 0.0D;
    public double timeElapsed = 0.0D;
    int currentPosition;
    public LinearLayout linearLayoutPlayer;
    public VisualizerView mVisualizerView;
    NativeExpressAdView adView;
    private static final float VISUALIZER_HEIGHT_DIP = 60f;
    public static Visualizer mVisualizer=null;
    private AdView mAdView;
    private Runnable updateSeekBarTime = new Runnable() {
        @SuppressLint({ "NewApi" })
        public void run() {
            AudioDetailsGlory.this.timeElapsed = AudioDetailsGlory.this.mp.getCurrentPosition();
            AudioDetailsGlory.this.music_seek.setProgress((int) AudioDetailsGlory.this.timeElapsed);
            double d = AudioDetailsGlory.this.finalTime - AudioDetailsGlory.this.timeElapsed;
            TextView localTextView = AudioDetailsGlory.this.starttime;
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Long.valueOf(TimeUnit.MILLISECONDS.toMinutes((long) d));
            arrayOfObject[1] = Long.valueOf(TimeUnit.MILLISECONDS.toSeconds((long) d)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) d)));
            localTextView.setText(String.format("%d:%d", arrayOfObject));
            AudioDetailsGlory.this.durationHandler.postDelayed(this, 100L);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_details_glory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!SplashActivity.isPurchased) {
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            InterstitialAdmob_Load();
        }


        playy = (ImageView) findViewById(R.id.playy);
        pausee = (ImageView) findViewById(R.id.pausee);
        starttime = (TextView) findViewById(R.id.starttime);
        final_timing = (TextView) findViewById(R.id.final_timing);
        tittle_name = (TextView) findViewById(R.id.tittle_name);
        music_seek = (SeekBar) findViewById(R.id.music_seek);
        forward_btn = (ImageView) findViewById(R.id.forward_btn);
        rewind_btn = (ImageView) findViewById(R.id.rewind_btn);

        linearLayoutPlayer = (LinearLayout) findViewById(R.id.linearLayoutPlayer);
        music_seek.setEnabled(false);
        pausee.setVisibility(View.INVISIBLE);
        playy.setVisibility(View.VISIBLE);
        mp = new MediaPlayer();
        mp.seekTo(100);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        filename = extras.getString("videofilename");
        durations = extras.getString("durations");
        name = extras.getString("name");
        final_timing.setText(durations);
      //  setupVisualizerFxAndUI();
        tittle_name.setSelected(true);

        tittle_name.setText(name);
        File file = new File(filename);


        playy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                music_seek.setEnabled(true);
                finalTime = mp.getDuration();
                AudioDetailsGlory.this.music_seek.setMax((int) finalTime);
                pausee.setVisibility(View.VISIBLE);
                playy.setVisibility(View.INVISIBLE);
                mp.start();


               // mVisualizer.setEnabled(true);
                AudioDetailsGlory.this.timeElapsed = AudioDetailsGlory.this.mp.getCurrentPosition();
                AudioDetailsGlory.this.music_seek.setProgress((int) AudioDetailsGlory.this.timeElapsed);
                AudioDetailsGlory.this.durationHandler.postDelayed(AudioDetailsGlory.this.updateSeekBarTime, 100L);

                // TODO Auto-generated method stub

            }
        });
        pausee.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               // mVisualizer.setEnabled(false);

                pausee.setVisibility(View.INVISIBLE);
                playy.setVisibility(View.VISIBLE);
                mp.pause();
                // TODO Auto-generated method stub

            }
        });
        forward_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                currentPosition = mp.getCurrentPosition();
                currentPosition = mp.getCurrentPosition() + 10000;
                mp.seekTo(currentPosition);
                // TODO Auto-generated method stub

            }
        });
        rewind_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                currentPosition = mp.getCurrentPosition();
                currentPosition = mp.getCurrentPosition() - 10000;
                mp.seekTo(currentPosition);
                // TODO Auto-generated method stub

            }
        });
        music_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                // int totalDuration = vv.getDuration();
                // int currentPosition =
                // utils.progressToTimer(seekbar_vplay.getProgress(),
                // totalDuration);
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                // TODO Auto-generated method stub

            }

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {
                if ((AudioDetailsGlory.this.mp != null) && (fromUser)) {

                    this.progress = progressval;
                    AudioDetailsGlory.this.mp.seekTo(this.progress);
                }
                // if(fromUser){
                //
                // seekBar.setProgress(progressval);
                // // seekbar_vplay.setProgress(progressval);
                // vv.seekTo(progressval);
                // seekbar_vplay.setProgress(progressval);
                // }
                //
                // currentPosition = vv.getCurrentPosition();
                // if(vv != null && fromUser){
                // vv.seekTo(progress * 1000);
                // }

                // vv.seekTo(currentPosition);
                // int duration = vv.getDuration();
                //
                // int amoungToupdate = duration / 100;
                //
                // if (vv != null && fromUser) {
                // vv.seekTo(progress * amoungToupdate);
                // // TODO Auto-generated method stub
                // }
            }
        });
        try {
            mp.setDataSource(filename);
            mp.prepare();
            mp.seekTo(100);
            // mp.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new OnCompletionListener() {

            // @Override
            public void onCompletion(MediaPlayer arg0) {
                playy.setVisibility(View.VISIBLE);
                pausee.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void onBackPressed() {
        //	InterstitialAdmob();
        super.onBackPressed();
        if ((this.mp != null) && (this.mp.isPlaying())) {
           // mVisualizer.setEnabled(false);
            this.mp.stop();
            finish();


        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        if ((this.mp != null) && (this.mp.isPlaying())) {
            // mVisualizer.setEnabled(false);
            this.mp.stop();



        }
        super.onDestroy();
    }

    private void setupVisualizerFxAndUI() {
        // Create a VisualizerView (defined below), which will render the simplified audio
        // wave form to a Canvas.

        //You need to have something where to show Audio WAVE - in this case Canvas
        mVisualizerView = new VisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                (int)(VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)));
       // linearLayoutPlayer.addView(mVisualizerView);

        // Create the Visualizer object and attach it to our media player.
        //YOU NEED android.permission.RECORD_AUDIO for that in AndroidManifest.xml
        mVisualizer = new Visualizer(mp.getAudioSessionId());


        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                mVisualizerView.updateVisualizer(bytes);


            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {}
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

    }
    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }


}
