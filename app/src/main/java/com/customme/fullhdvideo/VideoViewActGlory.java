package com.customme.fullhdvideo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.IdRes;
import androidx.core.app.NotificationManagerCompat;

import com.customme.fullhdvideo.servicesGlory.FloatingViewService;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class VideoViewActGlory extends Activity implements GestureDetectionGlory.SimpleGestureListener {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public static int oneTimeOnly = 0;
    static int vari = 0;
    AdView Banner;
    public InterstitialAd interstitialAd;
    boolean aa = true;
    AdRequest adRequest;
    List<Integer> audioTracksIndexes;
    List<String> audioTracksList;
    private SeekBar brightbar;
    private int brightness;
    private ContentResolver cResolver;
    //    private GoogleApiClient client;
    Context context;
    int count;
    int current;
    int currentPosition;
    TextView current_position;
    int duration;
    private Handler durationHandler = new Handler();
    String durations;
    TextView durationss;
    Bundle extras;
    private String filename;
    private double finalTime = 0.0d;
    ImageView forward_btn;

    private Runnable hideScreenControllsRunnable = new C13721();
    private Runnable horizontalScrollRunnable = new C13732();
    ImageView hundred_screensize;

    boolean isPlaying = false;
    ImageView ivg;
    ImageButton land;
    Long lastSeekUpdateTime = null;
    Long lastVolumeUpdateTime = null;
    LinearLayout laylock;
    SeekBar left_press;
    ImageView lock;
    boolean lock_click = false;
    ImageView locked;
    private AudioManager mAudioManager;
    private Activity mContext;
    private float mCurBrightness = -1.0f;
    private int mCurVolume = -1;
    Handler mHandlerss;
    private boolean mIsScrolling = false;
    private int mMaxVolume;
    MediaPlayer mediaPlayer;
    View music_controls;
    ImageView open_pop_up_video;
    ImageView pause_btn;
    ImageView play_btn;
    ImageButton playbutton;
    ImageButton portrat;
    int pos;
    String pos2;
    View rel_bar;
    ImageView rewind_btn;
    SeekBar right_press;
    TextView screen_sizes;
    TextView scroll_position;
    SeekBar seekbar_vplay;
    int selectedAudioTrack;


    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @SuppressLint({"NewApi"})
        @TargetApi(11)
        public boolean onDoubleTap(MotionEvent e) {
            if (VideoViewActGlory.this.videoview.isPlaying()) {
                VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
                VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
                VideoViewActGlory.this.getWindow().clearFlags(2048);
                VideoViewActGlory.this.getWindow().addFlags(1024);
                VideoViewActGlory.vari = 1;
            }
            return true;
        }

        public boolean isFullScreen() {
            if ((VideoViewActGlory.this.getWindow().getAttributes().flags & 1024) == 1024) {
                return true;
            }
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
            if (!VideoViewActGlory.this.lock_click && VideoViewActGlory.this.videoview.isPlaying()) {
                if (VideoViewActGlory.this.music_controls.getVisibility() == View.GONE) {
                    VideoViewActGlory.this.music_controls.setVisibility(View.VISIBLE);
                } else {
                    VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
                }
                if (VideoViewActGlory.this.video_header_controls.getVisibility() == View.GONE) {
                    VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
                } else {
                    VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
                }
                VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 5000);
                if (isFullScreen()) {
                    VideoViewActGlory.this.getWindow().clearFlags(1024);
                    VideoViewActGlory.this.getWindow().addFlags(2048);
                    VideoViewActGlory.vari = 0;
                } else {
                    VideoViewActGlory.this.getWindow().clearFlags(2048);
                    VideoViewActGlory.this.getWindow().addFlags(1024);
                    VideoViewActGlory.vari = 1;
                }
            }
            return super.onSingleTapConfirmed(e);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float deltaX = e1.getRawX() - e2.getRawX();
            float deltaY = e1.getRawY() - e2.getRawY();
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            VideoViewActGlory.this.setGestureListener();
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (Math.abs(deltaX) > 20.0f && currentTime.longValue() >= VideoViewActGlory.this.lastVolumeUpdateTime.longValue() + 1000) {
                    boolean z;
                    VideoViewActGlory.this.lastSeekUpdateTime = currentTime;
                    VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
                    if (deltaX < 0.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    videoViewActivity.onHorizontalScroll(z);
                }
            } else if (Math.abs(deltaY) > 60.0 && currentTime >= VideoViewActGlory.this.lastSeekUpdateTime + 1000) {
                if (((double) e1.getX()) < ((double) VideoViewActGlory.getDeviceWidth(VideoViewActGlory.this.context)) * 0.5d) {
                    VideoViewActGlory.this.lastVolumeUpdateTime = currentTime;
                    VideoViewActGlory.this.onVerticalScroll(deltaY / ((float) VideoViewActGlory.getDeviceHeight(VideoViewActGlory.this.context)), 1);
                } else if (((double) e1.getX()) > ((double) VideoViewActGlory.getDeviceWidth(VideoViewActGlory.this.context)) * 0.5d) {
                    VideoViewActGlory.this.lastVolumeUpdateTime = currentTime;
                    VideoViewActGlory.this.onVerticalScroll(deltaY / ((float) VideoViewActGlory.getDeviceHeight(VideoViewActGlory.this.context)), 2);
                }
            }
            return true;
        }
    };
    @SuppressWarnings("deprecation")
    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);

    ImageView size_screen;
    ImageView size_screenback;
    int stopPosition = -1;
    TextView textbrightness;
    TextView textvolume;
    public double timeElapsed = 0.0d;
    private Runnable updateSeekBarTime = new C13743();
    private String videoDisplayName;
    private int video_column_index;
    View video_header_controls;
    TextView video_title;
    private Cursor videocursor;
    VideoView videoview;
    private SeekBar volumebar;
    private Window window;

    class C13721 implements Runnable {
        C13721() {
        }

        public void run() {
            if (VideoViewActGlory.this.music_controls.getVisibility() == View.VISIBLE) {
                VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
            }
            if (VideoViewActGlory.this.video_header_controls.getVisibility() == View.VISIBLE) {
                VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
            }
            VideoViewActGlory.this.getWindow().clearFlags(2048);
            VideoViewActGlory.this.getWindow().addFlags(1024);
            VideoViewActGlory.vari = 1;
            VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
        }
    }

    class C13732 implements Runnable {
        C13732() {
        }

        public void run() {
            if (Long.valueOf(System.currentTimeMillis()).longValue() >= VideoViewActGlory.this.lastSeekUpdateTime.longValue() + 1000) {
                Log.e("Scroll", "Stopped");
                VideoViewActGlory.this.mAudioManager.setStreamMute(3, false);
                VideoViewActGlory.this.scroll_position.setVisibility(View.GONE);
                if (VideoViewActGlory.this.videoview.isPlaying()) {
                    VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
                }
                VideoViewActGlory.this.videoview.removeCallbacks(VideoViewActGlory.this.horizontalScrollRunnable);
                return;
            }
            VideoViewActGlory.this.videoview.postDelayed(VideoViewActGlory.this.horizontalScrollRunnable, 1000);
        }
    }

    class C13743 implements Runnable {
        C13743() {
        }

        @SuppressLint({"NewApi"})
        public void run() {
            String songTime;
            VideoViewActGlory.this.durationHandler.removeCallbacks(VideoViewActGlory.this.updateSeekBarTime);
            VideoViewActGlory.this.timeElapsed = (double) VideoViewActGlory.this.videoview.getCurrentPosition();
            if (VideoViewActGlory.this.videoview.getCurrentPosition() > 0) {
                VideoViewActGlory.this.seekbar_vplay.setMax(VideoViewActGlory.this.videoview.getDuration());
                VideoViewActGlory.this.seekbar_vplay.setProgress(VideoViewActGlory.this.videoview.getCurrentPosition());
            }
            double d = VideoViewActGlory.this.timeElapsed;
            TextView localTextView = VideoViewActGlory.this.current_position;
            int mns = (int) ((d % 3600000.0d) / 60000.0d);
            int scs = (int) (((d % 3600000.0d) % 60000.0d) / 1000.0d);
            if (((int) (d / 3600000.0d)) > 0) {
                songTime = String.format("%02d:%02d:%02d",  (int)(d / 3600000.0d),(mns),(scs));
            } else {
                songTime = String.format("%1$02d:%2$02d", (mns), (scs));
            }
            localTextView.setText(songTime);
            VideoViewActGlory.this.scroll_position.setText(songTime);
            VideoViewActGlory.this.durationHandler.postDelayed(this, 100);
        }
    }

    class C13754 implements View.OnClickListener {
        C13754() {
        }

        public void onClick(View v) {
            if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(VideoViewActGlory.this)) {
                VideoViewActGlory.this.durationHandler.removeCallbacks(VideoViewActGlory.this.updateSeekBarTime);
                Intent intent = new Intent(VideoViewActGlory.this, FloatingViewService.class);
                intent.putExtra("VIDEO_PATH", VideoViewActGlory.this.filename);
                intent.putExtra("START_FROM", VideoViewActGlory.this.videoview.getCurrentPosition());
                intent.putExtra("durations", VideoViewActGlory.this.durations);
                intent.putExtra("VideoDisplayName", VideoViewActGlory.this.videoDisplayName);
                intent.addCategory("android.intent.category.HOME");
                VideoViewActGlory.this.startService(intent);
                Intent startMain = new Intent("android.intent.action.MAIN");
                startMain.addCategory("android.intent.category.HOME");
                startMain.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                VideoViewActGlory.this.startActivity(startMain);
                VideoViewActGlory.this.finish();
                return;
            }
            VideoViewActGlory.this.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + VideoViewActGlory.this.getPackageName())), VideoViewActGlory.CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }

    class C13765 implements View.OnClickListener {
        C13765() {
        }

        public void onClick(View v) {
            VideoViewActGlory.this.aa = false;
            VideoViewActGlory.this.lock_click = true;
            VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
            VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
            VideoViewActGlory.this.lock.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.laylock.setEnabled(false);
            VideoViewActGlory.this.size_screenback.setEnabled(false);
            VideoViewActGlory.this.hundred_screensize.setEnabled(false);
            VideoViewActGlory.this.screen_sizes.setEnabled(false);
            VideoViewActGlory.this.left_press.setEnabled(false);
            VideoViewActGlory.this.right_press.setEnabled(false);
            VideoViewActGlory.this.volumebar.setEnabled(false);
            VideoViewActGlory.this.brightbar.setEnabled(false);
            VideoViewActGlory.this.seekbar_vplay.setEnabled(false);
            VideoViewActGlory.this.rewind_btn.setEnabled(false);
            VideoViewActGlory.this.play_btn.setEnabled(false);
            VideoViewActGlory.this.forward_btn.setEnabled(false);
            VideoViewActGlory.this.locked.setTag("a");
            VideoViewActGlory.this.music_controls.setEnabled(false);
        }
    }

    class C13786 implements View.OnClickListener {

        class C13771 implements Runnable {
            C13771() {
            }

            public void run() {
                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
            }
        }

        C13786() {
        }

        public void onClick(View v) {
            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.screen_sizes.setText("100%");
            VideoViewActGlory.this.screen_sizes.postDelayed(new C13771(), 2000);
            VideoViewActGlory.this.size_screenback.setVisibility(View.GONE);
            VideoViewActGlory.this.hundred_screensize.setVisibility(View.GONE);
            VideoViewActGlory.this.size_screen.setVisibility(View.VISIBLE);
            DisplayMetrics metrics = new DisplayMetrics();
            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
            params.width = (int) (1000.0f * metrics.density);
            params.leftMargin = 150;
            params.rightMargin = 150;
            params.topMargin = 0;
            params.bottomMargin = 0;
            VideoViewActGlory.this.videoview.setLayoutParams(params);
        }
    }

    class C13807 implements View.OnClickListener {

        class C13791 implements Runnable {
            C13791() {
            }

            public void run() {
                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
            }
        }

        C13807() {
        }

        public void onClick(View v) {
            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.screen_sizes.setText("FIT TO SCREEN");
            VideoViewActGlory.this.screen_sizes.postDelayed(new C13791(), 2000);
            VideoViewActGlory.this.size_screenback.setVisibility(View.GONE);
            VideoViewActGlory.this.size_screen.setVisibility(View.GONE);
            VideoViewActGlory.this.hundred_screensize.setVisibility(View.VISIBLE);
            DisplayMetrics metrics = new DisplayMetrics();
            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            params.rightMargin = 0;
            params.topMargin = 0;
            params.bottomMargin = 0;
            VideoViewActGlory.this.videoview.setLayoutParams(params);
        }
    }

    class C13828 implements View.OnClickListener {

        class C13811 implements Runnable {
            C13811() {
            }

            public void run() {
                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
            }
        }

        C13828() {
        }

        public void onClick(View v) {
            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.screen_sizes.setText("CROP");
            VideoViewActGlory.this.screen_sizes.postDelayed(new C13811(), 2000);
            VideoViewActGlory.this.size_screenback.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.size_screen.setVisibility(View.GONE);
            VideoViewActGlory.this.hundred_screensize.setVisibility(View.GONE);
            DisplayMetrics metrics = new DisplayMetrics();
            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
            params.width = (int) (400.0f * metrics.density);
            params.height = (int) (300.0 * metrics.density);
            params.leftMargin = 150;
            params.rightMargin = 150;
            params.topMargin = 150;
            params.bottomMargin = 150;
            VideoViewActGlory.this.videoview.setLayoutParams(params);
        }
    }

    class C13839 implements View.OnClickListener {
        C13839() {
        }

        public void onClick(View v) {
            VideoViewActGlory.this.aa = true;
            VideoViewActGlory.this.laylock.setEnabled(true);
            VideoViewActGlory.this.lock.setVisibility(View.GONE);
            VideoViewActGlory.this.left_press.setEnabled(true);
            VideoViewActGlory.this.right_press.setEnabled(true);
            VideoViewActGlory.this.volumebar.setEnabled(true);
            VideoViewActGlory.this.brightbar.setEnabled(true);
            VideoViewActGlory.this.music_controls.setEnabled(true);
            VideoViewActGlory.this.seekbar_vplay.setEnabled(true);
            VideoViewActGlory.this.rewind_btn.setEnabled(true);
            VideoViewActGlory.this.play_btn.setEnabled(true);
            VideoViewActGlory.this.forward_btn.setEnabled(true);
            VideoViewActGlory.this.lock_click = false;
            VideoViewActGlory.this.lock.setTag("b");
            VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.rewind_btn.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.forward_btn.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.music_controls.setVisibility(View.VISIBLE);
            VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
            if (VideoViewActGlory.this.videoview.isPlaying()) {
                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
                return;
            }
            VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
            VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Long valueOf = System.currentTimeMillis();
        this.lastVolumeUpdateTime = valueOf;
        this.lastSeekUpdateTime = valueOf;
        setContentView(R.layout.activity_video_view_glory);
        this.Banner = (AdView) findViewById(R.id.adView);
        if (!SplashActivity.isPurchased) {
            InterstitialAdmob_Load();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    finish();
                    super.onAdClosed();
                }
            });

            this.adRequest = new AdRequest.Builder().build();
        }

        startService(new Intent(this, FloatingViewService.class));
        this.mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        initComponents();
        setUpComponents();
        this.playbutton.performClick();
//        this.client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void backArrowListener(View v) {
        onBackPressed();
    }

    private void setUpComponents() {
        getWindowManager().getDefaultDisplay();
        init_phone_video_grid();
        this.open_pop_up_video.setOnClickListener(new C13754());
        this.music_controls.postDelayed(this.hideScreenControllsRunnable, 3000);
        this.locked.setOnClickListener(new C13765());
        this.hundred_screensize.setOnClickListener(new C13786());
        this.size_screenback.setOnClickListener(new C13807());
        this.size_screen.setOnClickListener(new C13828());
        this.lock.setOnClickListener(new C13839());
        this.pause_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
                VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
                VideoViewActGlory.this.videoview.pause();
            }
        });
        this.play_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
                VideoViewActGlory.this.Banner.setVisibility(View.GONE);
                VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
                VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 1000);
                VideoViewActGlory.vari = 1;
                VideoViewActGlory.this.videoview.start();
            }
        });
        this.forward_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.aa = false;
                if (VideoViewActGlory.this.current + 1 == VideoViewActGlory.this.count) {
                    VideoViewActGlory.this.current = 0;
                } else if (VideoViewActGlory.this.current + 1 < VideoViewActGlory.this.count) {
                    VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
                    videoViewActivity.current++;
                }
                VideoViewActGlory.this.videoview.stopPlayback();
                VideoViewActGlory.this.get_video(VideoViewActGlory.this.current);
                if (VideoViewActGlory.oneTimeOnly == 0) {
                    VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                    VideoViewActGlory.oneTimeOnly = 1;
                }
                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.videoview.setZOrderOnTop(false);
                VideoViewActGlory.this.Banner.setVisibility(View.GONE);
                VideoViewActGlory.this.videoview.start();
                VideoViewActGlory.this.timeElapsed = (double) VideoViewActGlory.this.videoview.getCurrentPosition();
                VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
                VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 100);
                VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 3000);
            }
        });
        this.rewind_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.aa = false;
                if (VideoViewActGlory.this.current - 1 == 0) {
                    VideoViewActGlory.this.current = VideoViewActGlory.this.count;
                }
                if (VideoViewActGlory.this.current - 1 < VideoViewActGlory.this.count && VideoViewActGlory.this.current - 1 > 0) {
                    VideoViewActGlory.this.videoview.stopPlayback();
                    VideoViewActGlory.this.get_video(VideoViewActGlory.this.current - 1);
                    VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
                    videoViewActivity.current--;
                }
                if (VideoViewActGlory.oneTimeOnly == 0) {
                    VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                    VideoViewActGlory.oneTimeOnly = 1;
                }
                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.videoview.setZOrderOnTop(false);
                VideoViewActGlory.this.Banner.setVisibility(View.GONE);
                VideoViewActGlory.this.videoview.start();
                VideoViewActGlory.this.timeElapsed = (double) VideoViewActGlory.this.videoview.getCurrentPosition();
                VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
                VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 100);
                VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 3000);
            }
        });
        this.seekbar_vplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {
                if (VideoViewActGlory.this.videoview != null && fromUser) {
                    this.progress = progressval;
                    VideoViewActGlory.this.videoview.seekTo(this.progress);
                }
            }
        });
        this.videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                if (VideoViewActGlory.this.current + 1 == VideoViewActGlory.this.count) {
                    VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
                    VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
                    VideoViewActGlory.this.playbutton.setVisibility(View.VISIBLE);
                    VideoViewActGlory.this.music_controls.post(VideoViewActGlory.this.hideScreenControllsRunnable);
                    return;
                }
                VideoViewActGlory.this.forward_btn.performClick();
            }
        });
        this.mHandlerss = new Handler();
        this.videoview.setKeepScreenOn(true);
        this.portrat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.land.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.portrat.setVisibility(View.GONE);
                VideoViewActGlory.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        this.land.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoViewActGlory.this.land.setVisibility(View.GONE);
                VideoViewActGlory.this.portrat.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.setRequestedOrientation(0);
            }
        });
        this.land.performClick();
        this.window = getWindow();
        this.brightbar.setMax(100);
        this.right_press.setMax(100);
        this.volumebar.setMax(this.mAudioManager.getStreamMaxVolume(3));
        this.volumebar.setProgress(this.mAudioManager.getStreamVolume(3));
        this.volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {

                    VideoViewActGlory.this.mAudioManager.setStreamVolume(3, progress, 0);
                    if (progress > 0) {
                        progress = (progress * 100) / VideoViewActGlory.this.mMaxVolume;
                    }
                    VideoViewActGlory.this.textvolume.setText(String.valueOf(progress));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.volumebar.setKeyProgressIncrement(1);
        this.brightbar.setKeyProgressIncrement(1);
        try {
            this.brightness = Settings.System.getInt(this.cResolver, "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        this.videoview.requestFocus();
        this.music_controls.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.playbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VideoViewActGlory.this.videoview.isPlaying()) {
                    VideoViewActGlory.this.videoview.stopPlayback();
                    VideoViewActGlory.this.videoview.setZOrderOnTop(true);
                    return;
                }
                VideoViewActGlory.this.finalTime = (double) VideoViewActGlory.this.videoview.getDuration();
                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
                VideoViewActGlory.this.Banner.setVisibility(View.GONE);
                VideoViewActGlory.this.getWindow().clearFlags(2048);
                VideoViewActGlory.this.getWindow().addFlags(1024);
                VideoViewActGlory.vari = 1;
                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.videoview.setZOrderOnTop(false);
                VideoViewActGlory.this.Banner.setVisibility(View.GONE);
                VideoViewActGlory.this.videoview.start();
                if (VideoViewActGlory.this.getIntent().hasExtra("START_FROM")) {
                    VideoViewActGlory.this.videoview.seekTo(VideoViewActGlory.this.extras.getInt("START_FROM"));
                    VideoViewActGlory.this.getIntent().removeExtra("START_FROM");
                }
                VideoViewActGlory.this.timeElapsed = (double) VideoViewActGlory.this.videoview.getCurrentPosition();
                VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
                VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 10);
                VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
                VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
                if (VideoViewActGlory.oneTimeOnly == 0) {
                    VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
                    VideoViewActGlory.oneTimeOnly = 1;
                }
            }
        });
        this.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                VideoViewActGlory.this.videoview.setBackgroundColor(View.VISIBLE);
                VideoViewActGlory.this.audioTracksList = new ArrayList();
                VideoViewActGlory.this.audioTracksIndexes = new ArrayList();
                VideoViewActGlory.this.selectedAudioTrack = -1;
                if (Build.VERSION.SDK_INT >= 16) {
                    VideoViewActGlory.this.findViewById(R.id.ic_audio_tracks).setVisibility(View.VISIBLE);
                    MediaPlayer.TrackInfo[] trackInfoArray = mp.getTrackInfo();
                    int j = 0;
                    for (int i = 0; i < trackInfoArray.length; i++) {
                        if (trackInfoArray[i].getTrackType() ==  MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO) {
                            String language_name = trackInfoArray[i].getLanguage();
                            if (language_name.equals("und") || language_name.isEmpty()) {
                                j++;
                                language_name = "Audio track #" + j;
                            } else {
                                Locale loc = new Locale(language_name);
                                language_name = loc.getDisplayLanguage(loc);
                            }
                            VideoViewActGlory.this.audioTracksList.add(language_name);
                            VideoViewActGlory.this.audioTracksIndexes.add(Integer.valueOf(i));
                            Log.d("AudioTrack", i + " : " + language_name);
                        }
                    }
                    if (!VideoViewActGlory.this.audioTracksIndexes.isEmpty()) {
                        VideoViewActGlory.this.selectedAudioTrack = ((Integer) VideoViewActGlory.this.audioTracksIndexes.get(View.VISIBLE)).intValue();
                    }
                    VideoViewActGlory.this.mediaPlayer = mp;
                }
            }
        });
    }

    public void enlistAudioTracks(View view) {
        if (Build.VERSION.SDK_INT < 16 || this.audioTracksList == null || this.audioTracksList.size() <= 0) {
            Toast.makeText(this, "No Audio track found", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.audiotracks_dialog_glory);
        onPause();
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        for (int i = 0; i < this.audioTracksList.size(); i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText((CharSequence) this.audioTracksList.get(i));
            rb.setTextColor(getResources().getColor(R.color.white));
            if (((Integer) this.audioTracksIndexes.get(i)).intValue() == this.selectedAudioTrack) {
                rb.setChecked(true);
                rb.setButtonDrawable(R.drawable.ic_radio_button_checked);
            } else {
                rb.setButtonDrawable(R.drawable.ic_radio_button_unchecked);
            }
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) rg.findViewById(checkedId);
                int index = rg.indexOfChild(radioButton);
                VideoViewActGlory.this.selectedAudioTrack = ((Integer) VideoViewActGlory.this.audioTracksIndexes.get(index)).intValue();
                radioButton.setButtonDrawable(R.drawable.ic_radio_button_checked);
                if (Build.VERSION.SDK_INT >= 16) {
                    VideoViewActGlory.this.mediaPlayer.selectTrack(VideoViewActGlory.this.selectedAudioTrack);
                }
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                VideoViewActGlory.this.onResume();
            }
        });
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == -1) {
            this.open_pop_up_video.performClick();
        } else {
            Toast.makeText(this, "Draw over other app permission not available. Closing the application", Toast.LENGTH_SHORT).show();
        }
    }

    private void initComponents() {
        this.ivg = (ImageView) findViewById(R.id.ivg);
        this.video_header_controls = findViewById(R.id.video_header);
        this.video_title = (TextView) findViewById(R.id.video_title);
        this.open_pop_up_video = (ImageView) findViewById(R.id.open_pop_up_video);
        this.context = this;
        this.mContext = this;
        this.extras = getIntent().getExtras();
        this.filename = this.extras.getString("videofilename");
        this.videoDisplayName = this.extras.getString("VideoDisplayName");

        this.durations = this.extras.getString("durations");

        this.scroll_position = (TextView) findViewById(R.id.scroll_position);
        this.current_position = (TextView) findViewById(R.id.current_position);
        this.durationss = (TextView) findViewById(R.id.left_time);
        this.durationss.setText(this.durations);
        this.videoview = (VideoView) findViewById(R.id.videoView);
        this.playbutton = (ImageButton) findViewById(R.id.play_button);
        this.left_press = (SeekBar) findViewById(R.id.left_press);
        this.right_press = (SeekBar) findViewById(R.id.right_press);
        this.portrat = (ImageButton) findViewById(R.id.switch_to_portrait);
        this.land = (ImageButton) findViewById(R.id.switch_to_landscape);
        this.textvolume = (TextView) findViewById(R.id.textvolume);
        this.textbrightness = (TextView) findViewById(R.id.textbrightness);
        this.pause_btn = (ImageView) findViewById(R.id.pause_btn);
        this.play_btn = (ImageView) findViewById(R.id.play_btn);
        this.play_btn.setVisibility(View.INVISIBLE);
        this.pause_btn.setVisibility(View.VISIBLE);
        this.rel_bar = findViewById(R.id.music_controls);
        this.rel_bar.setVisibility(View.GONE);
        this.forward_btn = (ImageView) findViewById(R.id.forward_btn);
        this.rewind_btn = (ImageView) findViewById(R.id.rewind_btn);
        this.seekbar_vplay = (SeekBar) findViewById(R.id.video_seekbar);
        this.lock = (ImageView) findViewById(R.id.lock);
        this.locked = (ImageView) findViewById(R.id.locked);
        this.laylock = (LinearLayout) findViewById(R.id.laylock);
        this.music_controls = findViewById(R.id.music_controls);
        this.videoview.setVideoPath(this.filename);
        try {
            this.video_title.setText(this.videoDisplayName.substring(0, this.videoDisplayName.lastIndexOf(46)));
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            this.video_title.setText("");
        }
        this.brightbar = (SeekBar) findViewById(R.id.brightness_seekbar);
        this.volumebar = (SeekBar) findViewById(R.id.volume_seekbar);
        this.cResolver = getContentResolver();
        this.brightbar.setVisibility(View.GONE);
        this.volumebar.setVisibility(View.GONE);
        this.textvolume.setVisibility(View.GONE);
        this.textbrightness.setVisibility(View.GONE);
        this.land.setVisibility(View.VISIBLE);
        this.portrat.setVisibility(View.GONE);
        this.lock.setVisibility(View.GONE);
        this.seekbar_vplay.setVisibility(View.GONE);
        setVolumeControlStream(3);
        this.size_screen = (ImageView) findViewById(R.id.size_screen);
        this.size_screenback = (ImageView) findViewById(R.id.size_screenback);
        this.screen_sizes = (TextView) findViewById(R.id.screen_sizes);
        this.size_screen.setVisibility(View.VISIBLE);
        this.screen_sizes.setVisibility(View.GONE);
        this.size_screenback.setVisibility(View.GONE);
        this.hundred_screensize = (ImageView) findViewById(R.id.hundred_screensize);
        this.hundred_screensize.setVisibility(View.GONE);
    }

    public void onPause() {
        Log.d("VideoView", "onPause called");
        super.onPause();
        this.stopPosition = this.videoview.getCurrentPosition();
        if (this.videoview.isPlaying()) {
            this.videoview.pause();
            this.isPlaying = true;
            return;
        }
        this.isPlaying = false;
    }

    public void onResume() {
        super.onResume();
        Log.d("VideoView", "onResume called");
        if (this.stopPosition > 0) {
            this.videoview.seekTo(this.stopPosition);
            if (this.isPlaying) {
                this.videoview.start();
            }
        }
    }

    public boolean checkInternetConenction() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            this.Banner.setVisibility(View.GONE);
            return false;
        }
        this.Banner.setVisibility(View.VISIBLE);
        this.Banner.loadAd(this.adRequest);
        return true;
    }

    private void get_video(int position) {
        String songTime;
        this.video_column_index = this.videocursor.getColumnIndexOrThrow("_data");
        this.videocursor.moveToPosition(position);
        this.filename = this.videocursor.getString(this.video_column_index);
        this.videoDisplayName = this.videocursor.getString(this.videocursor.getColumnIndex("_display_name"));
        this.video_column_index = this.videocursor.getColumnIndexOrThrow("_display_name");
        this.videocursor.moveToPosition(position);
        this.duration = this.videocursor.getColumnIndexOrThrow("duration");
        this.durations = this.videocursor.getString(this.duration);
        int dur = this.videocursor.getInt(this.duration);
        this.finalTime = this.videocursor.getDouble(this.duration);
        int mns = (dur % 3600000) / 60000;
        int scs = ((dur % 3600000) % 60000) / 1000;
        if (dur / 3600000 > 0) {
            songTime = String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(dur / 3600000), Integer.valueOf(mns), Integer.valueOf(scs)});
        } else {
            songTime = String.format("%02d:%02d", new Object[]{Integer.valueOf(mns), Integer.valueOf(scs)});
        }
        this.durations = songTime;
        this.videocursor.moveToPosition(position);

        this.video_column_index = this.videocursor.getColumnIndexOrThrow("_size");
        this.videocursor.moveToPosition(position);
        this.videocursor.moveToPosition(position);
        this.volumebar.setEnabled(false);
        this.brightbar.setEnabled(false);
        this.aa = true;
        this.videoview.stopPlayback();
        this.videoview.setVideoPath(this.filename);
        this.video_title.setText(this.videoDisplayName.substring(0, this.videoDisplayName.lastIndexOf(46)));
        this.videoview.seekTo(100);
        this.current_position.setText("00:00");
        this.durationss.setText(songTime);
        this.current_position.setText(songTime);
        this.scroll_position.setText(songTime);
    }

    private void init_phone_video_grid() {
        this.videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data", "_display_name", "_size", "duration"}, null, null, null);
        this.count = this.videocursor.getCount();
        if (this.videocursor != null && this.count > 0) {
            int i = 0;
            while (this.videocursor.moveToNext()) {
                if (this.videocursor.getString(this.videocursor.getColumnIndex("_data")).equals(this.filename)) {
                    this.current = i;
                    return;
                }
                i++;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                this.mCurVolume = -1;
                this.mCurBrightness = -1.0f;
                this.volumebar.postDelayed(new Runnable() {
                    public void run() {
                        VideoViewActGlory.this.volumebar.setVisibility(View.GONE);
                    }
                }, 3000);
                this.textvolume.postDelayed(new Runnable() {
                    public void run() {
                        VideoViewActGlory.this.textvolume.setVisibility(View.GONE);
                    }
                }, 3000);
                this.brightbar.postDelayed(new Runnable() {
                    public void run() {
                        VideoViewActGlory.this.brightbar.setVisibility(View.GONE);
                    }
                }, 3000);
                this.textbrightness.postDelayed(new Runnable() {
                    public void run() {
                        VideoViewActGlory.this.textbrightness.setVisibility(View.GONE);
                    }
                }, 3000);
                break;
        }
        this.gestureDetector.onTouchEvent(event);
        return true;
    }

    public void onSwipe(int direction) {
        switch (direction) {
            case 1:
                Log.d("111-SWIPE_UP-111", "111-SWIPE_UP-111");
                return;
            case 2:
                Log.d("111-SWIPE_DOWN-111", "111-SWIPE_DOWN-111");
                return;
            case 3:
                Log.d("111-SWIPE_LEFT-111", "111-SWIPE_LEFT-111");
                this.currentPosition = this.videoview.getCurrentPosition();
                this.currentPosition = this.videoview.getCurrentPosition() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                this.videoview.seekTo(this.currentPosition);
                return;
            case 4:
                Log.d("111-SWIPE_RIGHT-111", "111-SWIPE_RIGHT-111");
                this.currentPosition = this.videoview.getCurrentPosition();
                this.currentPosition = this.videoview.getCurrentPosition() + 1000;
                this.videoview.seekTo(this.currentPosition);
                return;
            default:
                return;
        }
    }

    public void onStart(Boolean bol) {
    }

    public void setGestureListener() {
        this.mMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }

    public void onVerticalScroll(float percent, int direction) {
        if (direction == 1) {
            changeBrightness(percent * 2.0f);
        } else {
            changeVolume(percent * 2.0f);
        }
    }

    public void onHorizontalScroll(boolean seekForward) {
        if (((seekForward && this.videoview.canSeekForward()) || (!seekForward && this.videoview.canSeekBackward())) && this.aa) {
            if (this.music_controls.getVisibility() == View.GONE) {
                this.music_controls.setVisibility(View.VISIBLE);
            }
            this.mAudioManager.setStreamMute(3, true);
            this.videoview.removeCallbacks(this.horizontalScrollRunnable);
            if (this.scroll_position.getVisibility() == View.GONE) {
                this.scroll_position.setVisibility(View.VISIBLE);
            }
            this.videoview.postDelayed(this.horizontalScrollRunnable, 1000);
            if (seekForward) {
                Log.i("ViewGestureListener", "Forwarding");
                this.currentPosition = this.videoview.getCurrentPosition();
                this.currentPosition = this.videoview.getCurrentPosition() + 700;
                this.videoview.seekTo(this.currentPosition);
                return;
            }
            Log.i("ViewGestureListener", "Rewinding");
            this.currentPosition = this.videoview.getCurrentPosition();
            this.currentPosition = this.videoview.getCurrentPosition() - 700;
            this.videoview.seekTo(this.currentPosition);
        }
    }

    private void changeBrightness(float percent) {
        if (this.mCurBrightness == -1.0f) {
            this.mCurBrightness = this.mContext.getWindow().getAttributes().screenBrightness;
            if (this.mCurBrightness <= 0.01f) {
                this.mCurBrightness = 0.01f;
            }
        }
        this.brightbar.setVisibility(View.VISIBLE);
        this.textbrightness.setVisibility(View.VISIBLE);
        WindowManager.LayoutParams attributes = this.mContext.getWindow().getAttributes();
        attributes.screenBrightness = this.mCurBrightness + percent;
        if (attributes.screenBrightness >= 1.0) {
            attributes.screenBrightness = (float) 1.0;
        } else if (attributes.screenBrightness <= 0.01f) {
            attributes.screenBrightness = 0.01f;
        }
        this.mContext.getWindow().setAttributes(attributes);
        float p = attributes.screenBrightness * 100.0f;
        this.brightbar.setProgress((int) p);
        this.textbrightness.setText(String.valueOf((int) p));
    }

    private void changeVolume(float percent) {
        this.volumebar.setVisibility(View.VISIBLE);
        this.textvolume.setVisibility(View.VISIBLE);
        if (this.mCurVolume == -1) {
            this.mCurVolume = this.mAudioManager.getStreamVolume(3);
            if (((float) this.mCurVolume) < 0.01f) {
                this.mCurVolume = 0;
            }
        }
        int volume = ((int) (((float) this.mMaxVolume) * percent)) + this.mCurVolume;
        if (volume > this.mMaxVolume) {
            volume = this.mMaxVolume;
        }
        if (((float) volume) < 0.01f) {
            volume = 0;
        }
        this.volumebar.setProgress(volume);
    }

    public void onBackPressed() {
//        super.onBackPressed();
        if (!SplashActivity.isPurchased) {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
                finish();

        }
        else {
            finish();
        }
        if (this.videoview != null && this.videoview.isPlaying()) {
            this.videoview.stopPlayback();
        }


    }


    public void onStart() {
        super.onStart();
  }

    public void onStop() {
        super.onStop();

    }


    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
    }
}

//package com.hd.video.player.music.player.allformat.player.glory;
//
//        import android.annotation.SuppressLint;
//        import android.annotation.TargetApi;
//        import android.app.Activity;
//        import android.app.Dialog;
//        import android.content.ContentResolver;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.pm.ActivityInfo;
//        import android.database.Cursor;
//        import android.media.AudioManager;
//        import android.media.MediaPlayer;
//        import android.net.Uri;
//        import android.os.Build;
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.provider.MediaStore;
//        import android.provider.Settings;
//        import android.util.DisplayMetrics;
//        import android.util.Log;
//        import android.view.GestureDetector;
//        import android.view.MotionEvent;
//        import android.view.View;
//        import android.view.WindowManager;
//        import android.widget.ImageButton;
//        import android.widget.ImageView;
//        import android.widget.LinearLayout;
//        import android.widget.RadioButton;
//        import android.widget.RadioGroup;
//        import android.widget.RelativeLayout;
//        import android.widget.SeekBar;
//        import android.widget.TextView;
//        import android.widget.Toast;
//        import android.widget.VideoView;
//
//        import com.google.android.gms.ads.AdListener;
//        import com.google.android.gms.ads.AdRequest;
//        import com.google.android.gms.ads.AdView;
//        import com.google.android.gms.ads.InterstitialAd;
//        import com.hd.video.player.music.player.allformat.player.glory.servicesGlory.FloatingViewService;
//
//        import java.util.ArrayList;
//        import java.util.List;
//        import java.util.Locale;
//
//        import androidx.core.app.NotificationManagerCompat;
//
//
//public class VideoViewActGlory extends Activity implements GestureDetectionGlory.SimpleGestureListener {
//    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
//    public static int oneTimeOnly = 0;
//    static int vari = 0;
//    AdView Banner;
//    public InterstitialAd interstitialAd;
//    boolean aa = true;
//    AdRequest adRequest;
//    List<Integer> audioTracksIndexes;
//    List<String> audioTracksList;
//    private SeekBar brightbar;
//    //    private GoogleApiClient client;
//    Context context;
//    int count;
//    int current;
//    int currentPosition;
//    TextView current_position;
//    int duration;
//    private Handler durationHandler = new Handler();
//    String durations;
//    TextView durationss;
//    Bundle extras;
//    private String filePath;
//    private double finalTime = 0.0d;
//    ImageView forward_btn;
//
//    private Runnable hideScreenControllsRunnable = new C13721();
//    private Runnable horizontalScrollRunnable = new C13732();
//    ImageView hundred_screensize;
//
//    boolean isPlaying = false;
//    ImageView ivg;
//    ImageButton land;
//    Long lastSeekUpdateTime = null;
//    Long lastVolumeUpdateTime = null;
//    LinearLayout laylock;
//    SeekBar left_press;
//    ImageView lock;
//    boolean lock_click = false;
//    ImageView locked;
//    private AudioManager mAudioManager;
//    private Activity mContext;
//    private float mCurBrightness = -1.0f;
//    private int mCurVolume = -1;
//    Handler mHandlerss;
//    private int mMaxVolume;
//    MediaPlayer mediaPlayer;
//    View music_controls;
//    ImageView open_pop_up_video;
//    ImageView pause_btn;
//    ImageView play_btn;
//    ImageButton playbutton;
//    ImageButton portrat;
//
//    View rel_bar;
//    ImageView rewind_btn;
//    SeekBar right_press;
//    TextView screen_sizes;
//    TextView scroll_position;
//    SeekBar seekbar_vplay;
//    int selectedAudioTrack;
//
//
//    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
//        @SuppressLint({"NewApi"})
//        @TargetApi(11)
//        public boolean onDoubleTap(MotionEvent e) {
//            if (VideoViewActGlory.this.videoview.isPlaying()) {
//                VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
//                VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
//                VideoViewActGlory.this.getWindow().clearFlags(2048);
//                VideoViewActGlory.this.getWindow().addFlags(1024);
//                VideoViewActGlory.vari = 1;
//            }
//            return true;
//        }
//
//        public boolean isFullScreen() {
//            return (VideoViewActGlory.this.getWindow().getAttributes().flags & 1024) == 1024;
//        }
//
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
//            if (!VideoViewActGlory.this.lock_click && VideoViewActGlory.this.videoview.isPlaying()) {
//                if (VideoViewActGlory.this.music_controls.getVisibility() == View.GONE) {
//                    VideoViewActGlory.this.music_controls.setVisibility(View.VISIBLE);
//                } else {
//                    VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
//                }
//                if (VideoViewActGlory.this.video_header_controls.getVisibility() == View.GONE) {
//                    VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
//                } else {
//                    VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
//                }
//                VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 5000);
//                if (isFullScreen()) {
//                    VideoViewActGlory.this.getWindow().clearFlags(1024);
//                    VideoViewActGlory.this.getWindow().addFlags(2048);
//                    VideoViewActGlory.vari = 0;
//                } else {
//                    VideoViewActGlory.this.getWindow().clearFlags(2048);
//                    VideoViewActGlory.this.getWindow().addFlags(1024);
//                    VideoViewActGlory.vari = 1;
//                }
//            }
//            return super.onSingleTapConfirmed(e);
//        }
//
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            float deltaX = e1.getRawX() - e2.getRawX();
//            float deltaY = e1.getRawY() - e2.getRawY();
//            long currentTime = System.currentTimeMillis();
//            VideoViewActGlory.this.setGestureListener();
//            if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                if (Math.abs(deltaX) > 20.0f && currentTime >= VideoViewActGlory.this.lastVolumeUpdateTime + 1000) {
//                    boolean z;
//                    VideoViewActGlory.this.lastSeekUpdateTime = currentTime;
//                    VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
//                    z = deltaX < 0.0f;
//                    videoViewActivity.onHorizontalScroll(z);
//                }
//            } else if (Math.abs(deltaY) > 60.0 && currentTime >= VideoViewActGlory.this.lastSeekUpdateTime + 1000) {
//                if (((double) e1.getX()) < ((double) VideoViewActGlory.getDeviceWidth(VideoViewActGlory.this.context)) * 0.5d) {
//                    VideoViewActGlory.this.lastVolumeUpdateTime = currentTime;
//                    VideoViewActGlory.this.onVerticalScroll(deltaY / ((float) VideoViewActGlory.getDeviceHeight(VideoViewActGlory.this.context)), 1);
//                } else if (((double) e1.getX()) > ((double) VideoViewActGlory.getDeviceWidth(VideoViewActGlory.this.context)) * 0.5d) {
//                    VideoViewActGlory.this.lastVolumeUpdateTime = currentTime;
//                    VideoViewActGlory.this.onVerticalScroll(deltaY / ((float) VideoViewActGlory.getDeviceHeight(VideoViewActGlory.this.context)), 2);
//                }
//            }
//            return true;
//        }
//    };
//    @SuppressWarnings("deprecation")
//    GestureDetector gestureDetector
//            = new GestureDetector(simpleOnGestureListener);
//
//    ImageView size_screen;
//    ImageView size_screenback;
//    int stopPosition = -1;
//    TextView textbrightness;
//    TextView textvolume;
//    public double timeElapsed = 0.0d;
//    private Runnable updateSeekBarTime = new C13743();
//    private String videoDisplayName;
//    View video_header_controls;
//    TextView video_title;
//    private Cursor videocursor;
//    VideoView videoview;
//    private SeekBar volumebar;
//
//    class C13721 implements Runnable {
//        C13721() {
//        }
//
//        public void run() {
//            if (VideoViewActGlory.this.music_controls.getVisibility() == View.VISIBLE) {
//                VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
//            }
//            if (VideoViewActGlory.this.video_header_controls.getVisibility() == View.VISIBLE) {
//                VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
//            }
//            VideoViewActGlory.this.getWindow().clearFlags(2048);
//            VideoViewActGlory.this.getWindow().addFlags(1024);
//            VideoViewActGlory.vari = 1;
//            VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
//        }
//    }
//
//    class C13732 implements Runnable {
//        C13732() {
//        }
//
//        public void run() {
//            if (Long.valueOf(System.currentTimeMillis()) >= VideoViewActGlory.this.lastSeekUpdateTime + 1000) {
//                Log.e("Scroll", "Stopped");
//                VideoViewActGlory.this.mAudioManager.setStreamMute(3, false);
//                VideoViewActGlory.this.scroll_position.setVisibility(View.GONE);
//                if (VideoViewActGlory.this.videoview.isPlaying()) {
//                    VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
//                }
//                VideoViewActGlory.this.videoview.removeCallbacks(VideoViewActGlory.this.horizontalScrollRunnable);
//                return;
//            }
//            VideoViewActGlory.this.videoview.postDelayed(VideoViewActGlory.this.horizontalScrollRunnable, 1000);
//        }
//    }
//
//    class C13743 implements Runnable {
//        C13743() {
//        }
//
//        @SuppressLint({"NewApi"})
//        public void run() {
//            String songTime;
//            VideoViewActGlory.this.durationHandler.removeCallbacks(VideoViewActGlory.this.updateSeekBarTime);
//            VideoViewActGlory.this.timeElapsed = VideoViewActGlory.this.videoview.getCurrentPosition();
//            if (VideoViewActGlory.this.videoview.getCurrentPosition() > 0) {
//                VideoViewActGlory.this.seekbar_vplay.setMax(VideoViewActGlory.this.videoview.getDuration());
//                VideoViewActGlory.this.seekbar_vplay.setProgress(VideoViewActGlory.this.videoview.getCurrentPosition());
//            }
//            double d = VideoViewActGlory.this.timeElapsed;
//            TextView localTextView = VideoViewActGlory.this.current_position;
//            int mns = (int) ((d % 3600000.0d) / 60000.0d);
//            int scs = (int) (((d % 3600000.0d) % 60000.0d) / 1000.0d);
//            if (((int) (d / 3600000.0d)) > 0) {
//                songTime = String.format("%02d:%02d:%02d", (int) (d / 3600000.0d), (mns), (scs));
//            } else {
//                songTime = String.format("%1$02d:%2$02d", (mns), (scs));
//            }
//            localTextView.setText(songTime);
//            VideoViewActGlory.this.scroll_position.setText(songTime);
//            VideoViewActGlory.this.durationHandler.postDelayed(this, 100);
//        }
//    }
//
//    class C13754 implements View.OnClickListener {
//        C13754() {
//        }
//
//        public void onClick(View v) {
//            if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(VideoViewActGlory.this)) {
//                VideoViewActGlory.this.durationHandler.removeCallbacks(VideoViewActGlory.this.updateSeekBarTime);
//                Intent intent = new Intent(VideoViewActGlory.this, FloatingViewService.class);
//                intent.putExtra("VIDEO_PATH", VideoViewActGlory.this.filePath);
//                intent.putExtra("START_FROM", VideoViewActGlory.this.videoview.getCurrentPosition());
//                intent.putExtra("durations", VideoViewActGlory.this.durations);
//                intent.putExtra("VideoDisplayName", VideoViewActGlory.this.videoDisplayName);
//                intent.addCategory("android.intent.category.HOME");
//                VideoViewActGlory.this.startService(intent);
//                Intent startMain = new Intent("android.intent.action.MAIN");
//                startMain.addCategory("android.intent.category.HOME");
//                startMain.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                VideoViewActGlory.this.startActivity(startMain);
//                VideoViewActGlory.this.finish();
//                return;
//            }
//            VideoViewActGlory.this.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + VideoViewActGlory.this.getPackageName())), VideoViewActGlory.CODE_DRAW_OVER_OTHER_APP_PERMISSION);
//        }
//    }
//
//    class C13765 implements View.OnClickListener {
//        C13765() {
//        }
//
//        public void onClick(View v) {
//            VideoViewActGlory.this.aa = false;
//            VideoViewActGlory.this.lock_click = true;
//            VideoViewActGlory.this.music_controls.setVisibility(View.GONE);
//            VideoViewActGlory.this.video_header_controls.setVisibility(View.GONE);
//            VideoViewActGlory.this.lock.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.laylock.setEnabled(false);
//            VideoViewActGlory.this.size_screenback.setEnabled(false);
//            VideoViewActGlory.this.hundred_screensize.setEnabled(false);
//            VideoViewActGlory.this.screen_sizes.setEnabled(false);
//            VideoViewActGlory.this.left_press.setEnabled(false);
//            VideoViewActGlory.this.right_press.setEnabled(false);
//            VideoViewActGlory.this.volumebar.setEnabled(false);
//            VideoViewActGlory.this.brightbar.setEnabled(false);
//            VideoViewActGlory.this.seekbar_vplay.setEnabled(false);
//            VideoViewActGlory.this.rewind_btn.setEnabled(false);
//            VideoViewActGlory.this.play_btn.setEnabled(false);
//            VideoViewActGlory.this.forward_btn.setEnabled(false);
//            VideoViewActGlory.this.locked.setTag("a");
//            VideoViewActGlory.this.music_controls.setEnabled(false);
//        }
//    }
//
//    class C13786 implements View.OnClickListener {
//
//        class C13771 implements Runnable {
//            C13771() {
//            }
//
//            public void run() {
//                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
//            }
//        }
//
//        C13786() {
//        }
//
//        public void onClick(View v) {
//            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.screen_sizes.setText(R.string.hundred_percent);
//            VideoViewActGlory.this.screen_sizes.postDelayed(new C13771(), 2000);
//            VideoViewActGlory.this.size_screenback.setVisibility(View.GONE);
//            VideoViewActGlory.this.hundred_screensize.setVisibility(View.GONE);
//            VideoViewActGlory.this.size_screen.setVisibility(View.VISIBLE);
//            DisplayMetrics metrics = new DisplayMetrics();
//            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
//            params.width = (int) (1000.0f * metrics.density);
//            params.leftMargin = 150;
//            params.rightMargin = 150;
//            params.topMargin = 0;
//            params.bottomMargin = 0;
//            VideoViewActGlory.this.videoview.setLayoutParams(params);
//        }
//    }
//
//    class C13807 implements View.OnClickListener {
//
//        class C13791 implements Runnable {
//            C13791() {
//            }
//
//            public void run() {
//                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
//            }
//        }
//
//        C13807() {
//        }
//
//        public void onClick(View v) {
//            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.screen_sizes.setText(R.string.fit_to_screen);
//            VideoViewActGlory.this.screen_sizes.postDelayed(new C13791(), 2000);
//            VideoViewActGlory.this.size_screenback.setVisibility(View.GONE);
//            VideoViewActGlory.this.size_screen.setVisibility(View.GONE);
//            VideoViewActGlory.this.hundred_screensize.setVisibility(View.VISIBLE);
//            DisplayMetrics metrics = new DisplayMetrics();
//            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
//            params.width = metrics.widthPixels;
//            params.height = metrics.heightPixels;
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            params.topMargin = 0;
//            params.bottomMargin = 0;
//            VideoViewActGlory.this.videoview.setLayoutParams(params);
//        }
//    }
//
//    class C13828 implements View.OnClickListener {
//
//        class C13811 implements Runnable {
//            C13811() {
//            }
//
//            public void run() {
//                VideoViewActGlory.this.screen_sizes.setVisibility(View.GONE);
//            }
//        }
//
//        C13828() {
//        }
//
//        public void onClick(View v) {
//            VideoViewActGlory.this.screen_sizes.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.screen_sizes.setText(R.string.crop);
//            VideoViewActGlory.this.screen_sizes.postDelayed(new C13811(), 2000);
//            VideoViewActGlory.this.size_screenback.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.size_screen.setVisibility(View.GONE);
//            VideoViewActGlory.this.hundred_screensize.setVisibility(View.GONE);
//            DisplayMetrics metrics = new DisplayMetrics();
//            VideoViewActGlory.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) VideoViewActGlory.this.videoview.getLayoutParams();
//            params.width = (int) (400.0f * metrics.density);
//            params.height = (int) (300.0 * metrics.density);
//            params.leftMargin = 150;
//            params.rightMargin = 150;
//            params.topMargin = 150;
//            params.bottomMargin = 150;
//            VideoViewActGlory.this.videoview.setLayoutParams(params);
//        }
//    }
//
//    class C13839 implements View.OnClickListener {
//        C13839() {
//        }
//
//        public void onClick(View v) {
//            VideoViewActGlory.this.aa = true;
//            VideoViewActGlory.this.laylock.setEnabled(true);
//            VideoViewActGlory.this.lock.setVisibility(View.GONE);
//            VideoViewActGlory.this.left_press.setEnabled(true);
//            VideoViewActGlory.this.right_press.setEnabled(true);
//            VideoViewActGlory.this.volumebar.setEnabled(true);
//            VideoViewActGlory.this.brightbar.setEnabled(true);
//            VideoViewActGlory.this.music_controls.setEnabled(true);
//            VideoViewActGlory.this.seekbar_vplay.setEnabled(true);
//            VideoViewActGlory.this.rewind_btn.setEnabled(true);
//            VideoViewActGlory.this.play_btn.setEnabled(true);
//            VideoViewActGlory.this.forward_btn.setEnabled(true);
//            VideoViewActGlory.this.lock_click = false;
//            VideoViewActGlory.this.lock.setTag("b");
//            VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.rewind_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.forward_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.music_controls.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
//            if (VideoViewActGlory.this.videoview.isPlaying()) {
//                VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
//                VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
//                return;
//            }
//            VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
//            VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @SuppressLint({"NewApi"})
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Long valueOf = System.currentTimeMillis();
//        this.lastVolumeUpdateTime = valueOf;
//        this.lastSeekUpdateTime = valueOf;
//        setContentView(R.layout.activity_video_view_glory);
//        this.Banner = findViewById(R.id.adView);
//        if (!SplashActivity.isPurchased) {
//            InterstitialAdmob_Load();
//            interstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    finish();
//                    super.onAdClosed();
//                }
//            });
//
//            this.adRequest = new AdRequest.Builder().build();
//        }
//
//        startService(new Intent(this, FloatingViewService.class));
//        this.mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        initComponents();
//        setUpComponents();
//        this.playbutton.performClick();
////        this.client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//    public void backArrowListener(View v) {
//        onBackPressed();
//    }
//
//    private void setUpComponents() {
//        getWindowManager().getDefaultDisplay();
//        init_phone_video_grid();
//        this.open_pop_up_video.setOnClickListener(new C13754());
//        this.music_controls.postDelayed(this.hideScreenControllsRunnable, 3000);
//        this.locked.setOnClickListener(new C13765());
//        this.hundred_screensize.setOnClickListener(new C13786());
//        this.size_screenback.setOnClickListener(new C13807());
//        this.size_screen.setOnClickListener(new C13828());
//        this.lock.setOnClickListener(new C13839());
//        this.pause_btn.setOnClickListener(v -> {
//            VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
//            VideoViewActGlory.this.music_controls.removeCallbacks(VideoViewActGlory.this.hideScreenControllsRunnable);
//            VideoViewActGlory.this.videoview.pause();
//        });
//        this.play_btn.setOnClickListener(v -> {
//            VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
//            VideoViewActGlory.this.Banner.setVisibility(View.GONE);
//            VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
//            VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 1000);
//            VideoViewActGlory.vari = 1;
//            VideoViewActGlory.this.videoview.start();
//        });
//        this.forward_btn.setOnClickListener(v -> {
//            VideoViewActGlory.this.aa = false;
//            if (VideoViewActGlory.this.current + 1 == VideoViewActGlory.this.count) {
//                VideoViewActGlory.this.current = 0;
//            } else if (VideoViewActGlory.this.current + 1 < VideoViewActGlory.this.count) {
//                VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
//                videoViewActivity.current++;
//            }
//            VideoViewActGlory.this.videoview.stopPlayback();
//            VideoViewActGlory.this.get_video(VideoViewActGlory.this.current);
//            if (VideoViewActGlory.oneTimeOnly == 0) {
//                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//                VideoViewActGlory.oneTimeOnly = 1;
//            }
//            VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//            VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
//            VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
//            VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.videoview.setZOrderOnTop(false);
//            VideoViewActGlory.this.Banner.setVisibility(View.GONE);
//            VideoViewActGlory.this.videoview.start();
//            VideoViewActGlory.this.timeElapsed = VideoViewActGlory.this.videoview.getCurrentPosition();
//            VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
//            VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 100);
//            VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 3000);
//        });
//        this.rewind_btn.setOnClickListener(v -> {
//            VideoViewActGlory.this.aa = false;
//            if (VideoViewActGlory.this.current - 1 == 0) {
//                VideoViewActGlory.this.current = VideoViewActGlory.this.count;
//            }
//            if (VideoViewActGlory.this.current - 1 < VideoViewActGlory.this.count && VideoViewActGlory.this.current - 1 > 0) {
//                VideoViewActGlory.this.videoview.stopPlayback();
//                VideoViewActGlory.this.get_video(VideoViewActGlory.this.current - 1);
//                VideoViewActGlory videoViewActivity = VideoViewActGlory.this;
//                videoViewActivity.current--;
//            }
//            if (VideoViewActGlory.oneTimeOnly == 0) {
//                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//                VideoViewActGlory.oneTimeOnly = 1;
//            }
//            VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//            VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
//            VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
//            VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.videoview.setZOrderOnTop(false);
//            VideoViewActGlory.this.Banner.setVisibility(View.GONE);
//            VideoViewActGlory.this.videoview.start();
//            VideoViewActGlory.this.timeElapsed = VideoViewActGlory.this.videoview.getCurrentPosition();
//            VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
//            VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 100);
//            VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.music_controls.postDelayed(VideoViewActGlory.this.hideScreenControllsRunnable, 3000);
//        });
//        this.seekbar_vplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            int progress = 0;
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {
//                if (VideoViewActGlory.this.videoview != null && fromUser) {
//                    this.progress = progressval;
//                    VideoViewActGlory.this.videoview.seekTo(this.progress);
//                }
//            }
//        });
//        this.videoview.setOnCompletionListener(arg0 -> {
//            if (VideoViewActGlory.this.current + 1 == VideoViewActGlory.this.count) {
//                VideoViewActGlory.this.play_btn.setVisibility(View.VISIBLE);
//                VideoViewActGlory.this.pause_btn.setVisibility(View.GONE);
//                VideoViewActGlory.this.playbutton.setVisibility(View.VISIBLE);
//                VideoViewActGlory.this.music_controls.post(VideoViewActGlory.this.hideScreenControllsRunnable);
//                return;
//            }
//            VideoViewActGlory.this.forward_btn.performClick();
//        });
//        this.mHandlerss = new Handler();
//        this.videoview.setKeepScreenOn(true);
//        this.portrat.setOnClickListener(v -> {
//            VideoViewActGlory.this.land.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.portrat.setVisibility(View.GONE);
//            VideoViewActGlory.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        });
//        this.land.setOnClickListener(v -> {
//            VideoViewActGlory.this.land.setVisibility(View.GONE);
//            VideoViewActGlory.this.portrat.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        });
//        this.land.performClick();
//        this.brightbar.setMax(100);
//        this.right_press.setMax(100);
//        this.volumebar.setMax(this.mAudioManager.getStreamMaxVolume(3));
//        this.volumebar.setProgress(this.mAudioManager.getStreamVolume(3));
//        this.volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                try {
//
//                    VideoViewActGlory.this.mAudioManager.setStreamVolume(3, progress, 0);
//                    if (progress > 0) {
//                        progress = (progress * 100) / VideoViewActGlory.this.mMaxVolume;
//                    }
//                    VideoViewActGlory.this.textvolume.setText(String.valueOf(progress));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
//        this.volumebar.setKeyProgressIncrement(1);
//        this.brightbar.setKeyProgressIncrement(1);
//
//        this.videoview.requestFocus();
//
//        this.playbutton.setOnClickListener(v -> {
//            if (VideoViewActGlory.this.videoview.isPlaying()) {
//                VideoViewActGlory.this.videoview.stopPlayback();
//                VideoViewActGlory.this.videoview.setZOrderOnTop(true);
//                return;
//            }
//            VideoViewActGlory.this.finalTime = VideoViewActGlory.this.videoview.getDuration();
//            VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//            VideoViewActGlory.this.playbutton.setVisibility(View.GONE);
//            VideoViewActGlory.this.Banner.setVisibility(View.GONE);
//            VideoViewActGlory.this.getWindow().clearFlags(2048);
//            VideoViewActGlory.this.getWindow().addFlags(1024);
//            VideoViewActGlory.vari = 1;
//            VideoViewActGlory.this.play_btn.setVisibility(View.INVISIBLE);
//            VideoViewActGlory.this.pause_btn.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.seekbar_vplay.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.videoview.setZOrderOnTop(false);
//            VideoViewActGlory.this.Banner.setVisibility(View.GONE);
//            VideoViewActGlory.this.videoview.start();
//            if (VideoViewActGlory.this.getIntent().hasExtra("START_FROM")) {
//                VideoViewActGlory.this.videoview.seekTo(VideoViewActGlory.this.extras.getInt("START_FROM"));
//                VideoViewActGlory.this.getIntent().removeExtra("START_FROM");
//            }
//            VideoViewActGlory.this.timeElapsed = VideoViewActGlory.this.videoview.getCurrentPosition();
//            VideoViewActGlory.this.seekbar_vplay.setProgress((int) VideoViewActGlory.this.timeElapsed);
//            VideoViewActGlory.this.durationHandler.postDelayed(VideoViewActGlory.this.updateSeekBarTime, 10);
//            VideoViewActGlory.this.rel_bar.setVisibility(View.VISIBLE);
//            VideoViewActGlory.this.video_header_controls.setVisibility(View.VISIBLE);
//            if (VideoViewActGlory.oneTimeOnly == 0) {
//                VideoViewActGlory.this.seekbar_vplay.setMax((int) VideoViewActGlory.this.finalTime);
//                VideoViewActGlory.oneTimeOnly = 1;
//            }
//        });
//        this.videoview.setOnPreparedListener(mp -> {
//            videoview.setBackgroundColor(View.VISIBLE);
//            audioTracksList = new ArrayList();
//            audioTracksIndexes = new ArrayList();
//            selectedAudioTrack = -1;
//            if (Build.VERSION.SDK_INT >= 16) {
//                findViewById(R.id.ic_audio_tracks).setVisibility(View.VISIBLE);
//                MediaPlayer.TrackInfo[] trackInfoArray = mp.getTrackInfo();
//                int j = 0;
//                for (int i = 0; i < trackInfoArray.length; i++) {
//                    if (trackInfoArray[i].getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO) {
//                        String language_name = trackInfoArray[i].getLanguage();
//                        if (language_name.equals("und") || language_name.isEmpty()) {
//                            j++;
//                            language_name = "Audio track #" + j;
//                        } else {
//                            Locale loc = new Locale(language_name);
//                            language_name = loc.getDisplayLanguage(loc);
//                        }
//                        VideoViewActGlory.this.audioTracksList.add(language_name);
//                        VideoViewActGlory.this.audioTracksIndexes.add(i);
//                        Log.d("AudioTrack", i + " : " + language_name);
//                    }
//                }
//                if (!VideoViewActGlory.this.audioTracksIndexes.isEmpty()) {
//                    VideoViewActGlory.this.selectedAudioTrack = VideoViewActGlory.this.audioTracksIndexes.get(View.VISIBLE);
//                }
//                VideoViewActGlory.this.mediaPlayer = mp;
//            }
//        });
//    }
//
//    public void enlistAudioTracks(View view) {
//        if (this.audioTracksList == null || this.audioTracksList.size() <= 0) {
//            Toast.makeText(this, "No Audio track found", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(1);
//        dialog.setContentView(R.layout.audiotracks_dialog_glory);
//        onPause();
//        final RadioGroup rg = dialog.findViewById(R.id.radio_group);
//        for (int i = 0; i < this.audioTracksList.size(); i++) {
//            RadioButton rb = new RadioButton(this);
//            rb.setText(this.audioTracksList.get(i));
//            rb.setTextColor(getResources().getColor(R.color.white));
//            if (this.audioTracksIndexes.get(i) == this.selectedAudioTrack) {
//                rb.setChecked(true);
//                rb.setButtonDrawable(R.drawable.ic_radio_button_checked);
//            } else {
//                rb.setButtonDrawable(R.drawable.ic_radio_button_unchecked);
//            }
//            rg.addView(rb);
//        }
//        rg.setOnCheckedChangeListener((group, checkedId) -> {
//            RadioButton radioButton = rg.findViewById(checkedId);
//            int index = rg.indexOfChild(radioButton);
//            VideoViewActGlory.this.selectedAudioTrack = VideoViewActGlory.this.audioTracksIndexes.get(index);
//            radioButton.setButtonDrawable(R.drawable.ic_radio_button_checked);
//            if (Build.VERSION.SDK_INT >= 16) {
//                VideoViewActGlory.this.mediaPlayer.selectTrack(VideoViewActGlory.this.selectedAudioTrack);
//            }
//            dialog.dismiss();
//        });
//        dialog.setOnDismissListener(dialog1 -> VideoViewActGlory.this.onResume());
//        dialog.show();
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
//            super.onActivityResult(requestCode, resultCode, data);
//        } else if (resultCode == -1) {
//            this.open_pop_up_video.performClick();
//        } else {
//            Toast.makeText(this, "Draw over other app permission not available. Closing the application", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void initComponents() {
//        this.ivg = findViewById(R.id.ivg);
//        this.video_header_controls = findViewById(R.id.video_header);
//        this.video_title = findViewById(R.id.video_title);
//        this.open_pop_up_video = findViewById(R.id.open_pop_up_video);
//        this.context = this;
//        this.mContext = this;
//        this.extras = getIntent().getExtras();
//        this.filePath = this.extras.getString("videofilename");
//        this.videoDisplayName = this.extras.getString("VideoDisplayName");
//
//        this.durations = this.extras.getString("durations");
//
//        this.scroll_position = findViewById(R.id.scroll_position);
//        this.current_position = findViewById(R.id.current_position);
//        this.durationss = findViewById(R.id.left_time);
//        this.durationss.setText(this.durations);
//        this.videoview = findViewById(R.id.videoView);
//        this.playbutton = findViewById(R.id.play_button);
//        this.left_press = findViewById(R.id.left_press);
//        this.right_press = findViewById(R.id.right_press);
//        this.portrat = findViewById(R.id.switch_to_portrait);
//        this.land = findViewById(R.id.switch_to_landscape);
//        this.textvolume = findViewById(R.id.textvolume);
//        this.textbrightness = findViewById(R.id.textbrightness);
//        this.pause_btn = findViewById(R.id.pause_btn);
//        this.play_btn = findViewById(R.id.play_btn);
//        this.play_btn.setVisibility(View.INVISIBLE);
//        this.pause_btn.setVisibility(View.VISIBLE);
//        this.rel_bar = findViewById(R.id.music_controls);
//        this.rel_bar.setVisibility(View.GONE);
//        this.forward_btn = findViewById(R.id.forward_btn);
//        this.rewind_btn = findViewById(R.id.rewind_btn);
//        this.seekbar_vplay = findViewById(R.id.video_seekbar);
//        this.lock = findViewById(R.id.lock);
//        this.locked = findViewById(R.id.locked);
//        this.laylock = findViewById(R.id.laylock);
//        this.videoview.setVideoPath(this.filePath);
//        try {
//            this.video_title.setText(this.videoDisplayName.substring(0, this.videoDisplayName.lastIndexOf(46)));
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//            this.video_title.setText("");
//        }
//        this.brightbar = findViewById(R.id.brightness_seekbar);
//        this.volumebar = findViewById(R.id.volume_seekbar);
//        ContentResolver cResolver = getContentResolver();
//        this.brightbar.setVisibility(View.GONE);
//        this.volumebar.setVisibility(View.GONE);
//        this.textvolume.setVisibility(View.GONE);
//        this.textbrightness.setVisibility(View.GONE);
//        this.land.setVisibility(View.VISIBLE);
//        this.portrat.setVisibility(View.GONE);
//        this.lock.setVisibility(View.GONE);
//        this.seekbar_vplay.setVisibility(View.GONE);
//        setVolumeControlStream(3);
//        this.size_screen = findViewById(R.id.size_screen);
//        this.size_screenback = findViewById(R.id.size_screenback);
//        this.screen_sizes = findViewById(R.id.screen_sizes);
//        this.size_screen.setVisibility(View.VISIBLE);
//        this.screen_sizes.setVisibility(View.GONE);
//        this.size_screenback.setVisibility(View.GONE);
//        this.hundred_screensize = findViewById(R.id.hundred_screensize);
//        this.hundred_screensize.setVisibility(View.GONE);
//    }
//
//    public void onPause() {
//        Log.d("VideoView", "onPause called");
//        super.onPause();
//        this.stopPosition = this.videoview.getCurrentPosition();
//        if (this.videoview.isPlaying()) {
//            this.videoview.pause();
//            this.isPlaying = true;
//            return;
//        }
//        this.isPlaying = false;
//    }
//
//    public void onResume() {
//        super.onResume();
//        Log.d("VideoView", "onResume called");
//        if (this.stopPosition > 0) {
//            this.videoview.seekTo(this.stopPosition);
//            if (this.isPlaying) {
//                this.videoview.start();
//            }
//        }
//    }
//
////    public boolean checkInternetConenction() {
////        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
////        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
////            this.Banner.setVisibility(View.GONE);
////            return false;
////        }
////        this.Banner.setVisibility(View.VISIBLE);
////        this.Banner.loadAd(this.adRequest);
////        return true;
////    }
//
//    private void get_video(int position) {
//        String songTime;
//        int video_column_index = this.videocursor.getColumnIndexOrThrow("_data");
//        this.videocursor.moveToPosition(position);
//        this.filePath = this.videocursor.getString(video_column_index);
//        this.videoDisplayName = this.videocursor.getString(this.videocursor.getColumnIndex("_display_name"));
//        this.videocursor.moveToPosition(position);
//        this.duration = this.videocursor.getColumnIndexOrThrow("duration");
//        this.durations = this.videocursor.getString(this.duration);
//        int dur = this.videocursor.getInt(this.duration);
//        this.finalTime = this.videocursor.getDouble(this.duration);
//        int mns = (dur % 3600000) / 60000;
//        int scs = ((dur % 3600000) % 60000) / 1000;
//        if (dur / 3600000 > 0) {
//            songTime = String.format("%02d:%02d:%02d", dur / 3600000, mns, scs);
//        } else {
//            songTime = String.format("%02d:%02d", mns, scs);
//        }
//        this.durations = songTime;
//        this.videocursor.moveToPosition(position);
//        this.videocursor.moveToPosition(position);
//        this.videocursor.moveToPosition(position);
//        this.volumebar.setEnabled(false);
//        this.brightbar.setEnabled(false);
//        this.aa = true;
//        this.videoview.stopPlayback();
//        this.videoview.setVideoPath(this.filePath);
//        this.video_title.setText(this.videoDisplayName.substring(0, this.videoDisplayName.lastIndexOf(46)));
//        this.videoview.seekTo(100);
//        this.current_position.setText(R.string.current_position_time);
//        this.durationss.setText(songTime);
//        this.current_position.setText(songTime);
//        this.scroll_position.setText(songTime);
//    }
//
//    private void init_phone_video_grid() {
//        this.videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data", "_display_name", "_size", "duration"}, null, null, null);
//        this.count = this.videocursor.getCount();
//        if (this.videocursor != null && this.count > 0) {
//            int i = 0;
//            while (this.videocursor.moveToNext()) {
//                if (this.videocursor.getString(this.videocursor.getColumnIndex("_data")).equals(this.filePath)) {
//                    this.current = i;
//                    return;
//                }
//                i++;
//            }
//        }
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == 1) {
//            this.mCurVolume = -1;
//            this.mCurBrightness = -1.0f;
//            this.volumebar.postDelayed(() -> VideoViewActGlory.this.volumebar.setVisibility(View.GONE), 3000);
//            this.textvolume.postDelayed(() -> VideoViewActGlory.this.textvolume.setVisibility(View.GONE), 3000);
//            this.brightbar.postDelayed(() -> VideoViewActGlory.this.brightbar.setVisibility(View.GONE), 3000);
//            this.textbrightness.postDelayed(() -> VideoViewActGlory.this.textbrightness.setVisibility(View.GONE), 3000);
//        }
//        this.gestureDetector.onTouchEvent(event);
//        return true;
//    }
//
//    public void onSwipe(int direction) {
//        switch (direction) {
//            case 1:
//                Log.d("111-SWIPE_UP-111", "111-SWIPE_UP-111");
//                return;
//            case 2:
//                Log.d("111-SWIPE_DOWN-111", "111-SWIPE_DOWN-111");
//                return;
//            case 3:
//                Log.d("111-SWIPE_LEFT-111", "111-SWIPE_LEFT-111");
//                this.currentPosition = this.videoview.getCurrentPosition();
//                this.currentPosition = this.videoview.getCurrentPosition() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
//                this.videoview.seekTo(this.currentPosition);
//                return;
//            case 4:
//                Log.d("111-SWIPE_RIGHT-111", "111-SWIPE_RIGHT-111");
//                this.currentPosition = this.videoview.getCurrentPosition();
//                this.currentPosition = this.videoview.getCurrentPosition() + 1000;
//                this.videoview.seekTo(this.currentPosition);
//                return;
//            default:
//        }
//    }
//
//    public void onStart(Boolean bol) {
//    }
//
//    public void setGestureListener() {
//        this.mMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
//    }
//
//    public static int getDeviceWidth(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
//        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
//        return mDisplayMetrics.widthPixels;
//    }
//
//    public static int getDeviceHeight(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
//        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
//        return mDisplayMetrics.heightPixels;
//    }
//
//    public void onVerticalScroll(float percent, int direction) {
//        if (direction == 1) {
//            changeBrightness(percent * 2.0f);
//        } else {
//            changeVolume(percent * 2.0f);
//        }
//    }
//
//    public void onHorizontalScroll(boolean seekForward) {
//        if (((seekForward && this.videoview.canSeekForward()) || (!seekForward && this.videoview.canSeekBackward())) && this.aa) {
//            if (this.music_controls.getVisibility() == View.GONE) {
//                this.music_controls.setVisibility(View.VISIBLE);
//            }
//            this.mAudioManager.setStreamMute(3, true);
//            this.videoview.removeCallbacks(this.horizontalScrollRunnable);
//            if (this.scroll_position.getVisibility() == View.GONE) {
//                this.scroll_position.setVisibility(View.VISIBLE);
//            }
//            this.videoview.postDelayed(this.horizontalScrollRunnable, 1000);
//            if (seekForward) {
//                Log.i("ViewGestureListener", "Forwarding");
//                this.currentPosition = this.videoview.getCurrentPosition();
//                this.currentPosition = this.videoview.getCurrentPosition() + 700;
//                this.videoview.seekTo(this.currentPosition);
//                return;
//            }
//            Log.i("ViewGestureListener", "Rewinding");
//            this.currentPosition = this.videoview.getCurrentPosition();
//            this.currentPosition = this.videoview.getCurrentPosition() - 700;
//            this.videoview.seekTo(this.currentPosition);
//        }
//    }
//
//    private void changeBrightness(float percent) {
//        if (this.mCurBrightness == -1.0f) {
//            this.mCurBrightness = this.mContext.getWindow().getAttributes().screenBrightness;
//            if (this.mCurBrightness <= 0.01f) {
//                this.mCurBrightness = 0.01f;
//            }
//        }
//        this.brightbar.setVisibility(View.VISIBLE);
//        this.textbrightness.setVisibility(View.VISIBLE);
//        WindowManager.LayoutParams attributes = this.mContext.getWindow().getAttributes();
//        attributes.screenBrightness = this.mCurBrightness + percent;
//        if (attributes.screenBrightness >= 1.0) {
//            attributes.screenBrightness = (float) 1.0;
//        } else if (attributes.screenBrightness <= 0.01f) {
//            attributes.screenBrightness = 0.01f;
//        }
//        this.mContext.getWindow().setAttributes(attributes);
//        float p = attributes.screenBrightness * 100.0f;
//        this.brightbar.setProgress((int) p);
//        this.textbrightness.setText(String.valueOf((int) p));
//    }
//
//    private void changeVolume(float percent) {
//        this.volumebar.setVisibility(View.VISIBLE);
//        this.textvolume.setVisibility(View.VISIBLE);
//        if (this.mCurVolume == -1) {
//            this.mCurVolume = this.mAudioManager.getStreamVolume(3);
//            if (((float) this.mCurVolume) < 0.01f) {
//                this.mCurVolume = 0;
//            }
//        }
//        int volume = ((int) (((float) this.mMaxVolume) * percent)) + this.mCurVolume;
//        if (volume > this.mMaxVolume) {
//            volume = this.mMaxVolume;
//        }
//        if (((float) volume) < 0.01f) {
//            volume = 0;
//        }
//        this.volumebar.setProgress(volume);
//    }
//
//    public void onBackPressed() {
////        super.onBackPressed();
//        if (!SplashActivity.isPurchased) {
//            if (interstitialAd.isLoaded()) {
//                interstitialAd.show();
//                finish();
//            }
//
//        } else {
//            finish();
//        }
//        if (this.videoview != null && this.videoview.isPlaying()) {
//            this.videoview.stopPlayback();
//        }
//
//
//    }
//
//
//    public void onStart() {
//        super.onStart();
//    }
//
//    public void onStop() {
//        super.onStop();
//
//    }
//
//
//    /*Assign and Load Intercialtial*/
//    public void InterstitialAdmob_Load() {
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
//    }
//}

