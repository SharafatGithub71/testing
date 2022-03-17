package com.customme.fullhdvideo.servicesGlory;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import com.customme.fullhdvideo.VideoViewActGlory;
import com.customme.fullhdvideo.R;

public class FloatingViewService extends Service {
    TextView current_position;
    String duration;
    String filename = null;

    private Runnable hideScreenControllsRunnable = new C13841();
    private View mFloatingView;
    private WindowManager mWindowManager;
    View music_controls;
    ImageView play_pause_btn;
    SeekBar seekbar_vplay;
    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {

        @TargetApi(11)
        public boolean onSingleTapConfirmed(MotionEvent e) {
            FloatingViewService.this.music_controls.removeCallbacks(FloatingViewService.this.hideScreenControllsRunnable);
            if (FloatingViewService.this.music_controls.getVisibility() == View.GONE) {
                FloatingViewService.this.music_controls.setVisibility(View.VISIBLE);
                FloatingViewService.this.play_pause_btn.setVisibility(View.VISIBLE);
            } else {
                FloatingViewService.this.music_controls.setVisibility(View.GONE);
                FloatingViewService.this.play_pause_btn.setVisibility(View.GONE);
            }
            FloatingViewService.this.music_controls.postDelayed(FloatingViewService.this.hideScreenControllsRunnable, 5000);
            return super.onSingleTapConfirmed(e);
        }

    };
    int startFrom;
    TextView total_duration;
    private Runnable updateSeekBarTime = new C13852();
    String videoDisplayName;
    VideoView videoView;

    class C13841 implements Runnable {
        C13841() {
        }

        public void run() {
            try {
                if (FloatingViewService.this.music_controls.getVisibility() == View.VISIBLE) {
                    FloatingViewService.this.play_pause_btn.setVisibility(View.GONE);
                    FloatingViewService.this.music_controls.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class C13852 implements Runnable {
        C13852() {
        }

        @SuppressLint({"NewApi"})
        public void run() {
            try {
                String songTime;
                FloatingViewService.this.videoView.removeCallbacks(FloatingViewService.this.updateSeekBarTime);
                double d = (double) FloatingViewService.this.videoView.getCurrentPosition();
                if (d > 0.0d) {
                    FloatingViewService.this.seekbar_vplay.setMax(FloatingViewService.this.videoView.getDuration());
                    FloatingViewService.this.seekbar_vplay.setProgress((int) d);
                }
                int mns = (int) ((d % 3600000.0d) / 60000.0d);
                int scs = (int) (((d % 3600000.0d) % 60000.0d) / 1000.0d);
                if (((int) (d / 3600000.0d)) > 0) {
                    songTime = String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (d / 3600000.0d)), Integer.valueOf(mns), Integer.valueOf(scs)});
                } else {
                    songTime = String.format("%02d:%02d", new Object[]{Integer.valueOf(mns), Integer.valueOf(scs)});
                }
                FloatingViewService.this.current_position.setText(songTime);
                FloatingViewService.this.videoView.postDelayed(this, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class C13874 implements OnClickListener {
        C13874() {
        }

        public void onClick(View v) {
            if (FloatingViewService.this.videoView.isPlaying()) {
                FloatingViewService.this.videoView.pause();
                FloatingViewService.this.play_pause_btn.setImageDrawable(FloatingViewService.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_action_play));
                return;
            }
            FloatingViewService.this.videoView.start();
            FloatingViewService.this.play_pause_btn.setImageDrawable(FloatingViewService.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_action_pause));
        }
    }

    class C13885 implements OnCompletionListener {
        C13885() {
        }

        public void onCompletion(MediaPlayer arg0) {
            FloatingViewService.this.stopSelf();
        }
    }

    class C13896 implements OnSeekBarChangeListener {
        C13896() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {
            if (FloatingViewService.this.videoView != null && fromUser) {
                FloatingViewService.this.videoView.seekTo(progressval);
            }
        }
    }

    class C13907 implements OnClickListener {
        C13907() {
        }

        public void onClick(View view) {
            FloatingViewService.this.stopSelf();
        }
    }

    class C13918 implements OnClickListener {
        C13918() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(FloatingViewService.this, VideoViewActGlory.class);
            intent.putExtra("videofilename", FloatingViewService.this.filename);
            intent.putExtra("durations", FloatingViewService.this.duration);
            intent.putExtra("START_FROM", FloatingViewService.this.videoView.getCurrentPosition());
            intent.putExtra("VideoDisplayName", videoDisplayName);
            intent.putExtra("pos2", "");
            FloatingViewService.this.stopSelf();
            startActivity(intent);

        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        try {
            Log.e("folderAct", "in onStartCommand");
            this.filename = intent.getExtras().getString("VIDEO_PATH");
            this.videoDisplayName = intent.getExtras().getString("VideoDisplayName");
            this.startFrom = intent.getExtras().getInt("START_FROM", -1);
            this.duration = intent.getExtras().getString("durations");
            setUpWindow();
        } catch (Exception e) {
            stopSelf();
        }
        return START_STICKY;
    }

    private void setUpWindow() {
        Log.e("folderAct", "in setUpWindow");
        this.mFloatingView = LayoutInflater.from(FloatingViewService.this).inflate(R.layout.layout_floating_widget_glory, null);
        Log.e("folderAct", "in setUpWindow 0");
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_TOAST;

        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Log.e("folderAct", "in setUpWindow 00");
//        params.gravity = 48;
//        params.x = 0;
//        params.y = 0;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Log.e("folderAct", "in setUpWindow 000");
        if (mWindowManager != null) {

            this.mWindowManager.addView(this.mFloatingView, params);
        }
        Log.e("folderAct", "in setUpWindow 1");
        final View expandedView = this.mFloatingView.findViewById(R.id.expanded_container);
        this.play_pause_btn = (ImageView) this.mFloatingView.findViewById(R.id.play_pause_btn);
        this.total_duration = (TextView) this.mFloatingView.findViewById(R.id.left_time);
        this.total_duration.setText(this.duration);
        Log.e("folderAct", "in setUpWindow2");
        this.current_position = (TextView) this.mFloatingView.findViewById(R.id.current_position);
        this.music_controls = this.mFloatingView.findViewById(R.id.music_controls);
        this.seekbar_vplay = (SeekBar) this.mFloatingView.findViewById(R.id.video_seekbar);
        this.videoView = (VideoView) this.mFloatingView.findViewById(R.id.pop_up_video);
        this.videoView.setVideoPath(this.filename);
        Log.e("folderAct", "in setUpWindow 3");
        this.videoView.start();
        Log.e("folderAct", "in setUpWindow4");
        this.videoView.seekTo(this.startFrom);
        this.videoView.postDelayed(this.updateSeekBarTime, 100);
        this.play_pause_btn.setOnClickListener(new C13874());
        this.videoView.setOnCompletionListener(new C13885());
        this.seekbar_vplay.setOnSeekBarChangeListener(new C13896());
        this.videoView.setKeepScreenOn(true);
        ((ImageView) this.mFloatingView.findViewById(R.id.close_btn)).setOnClickListener(new C13907());
        ((ImageView) this.mFloatingView.findViewById(R.id.open_full_video)).setOnClickListener(new C13918());
        this.mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new OnTouchListener() {
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;
            GestureDetector gestureDetector
                    = new GestureDetector(simpleOnGestureListener);

            public boolean onTouch(View v, MotionEvent event) {
                this.gestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case 0:
                        this.initialX = params.x;
                        this.initialY = params.y;
                        this.initialTouchX = event.getRawX();
                        this.initialTouchY = event.getRawY();
                        return true;
                    case 1:
                        int Ydiff = (int) (event.getRawY() - this.initialTouchY);
                        if (((int) (event.getRawX() - this.initialTouchX)) >= 10 || Ydiff >= 10 || !FloatingViewService.this.isViewCollapsed()) {
                            return true;
                        }
                        expandedView.setVisibility(View.VISIBLE);
                        return true;
                    case 2:
                        params.x = this.initialX + ((int) (event.getRawX() - this.initialTouchX));
                        params.y = this.initialY + ((int) (event.getRawY() - this.initialTouchY));
                        FloatingViewService.this.mWindowManager.updateViewLayout(FloatingViewService.this.mFloatingView, params);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void onCreate() {
        super.onCreate();
    }

    private boolean isViewCollapsed() {
        return this.mFloatingView == null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFloatingView != null) {
            this.mWindowManager.removeView(this.mFloatingView);
        }
    }
}
