package com.customme.fullhdvideo.audioVisualizerGlory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

@SuppressLint({"DrawAllocation"})
public class VisualizerView extends View {
    private byte[] mBytes;
    private Paint mForePaint = new Paint();
    private float[] mPoints;
    private Rect mRect = new Rect();

    public VisualizerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mBytes = null;
        this.mForePaint.setStrokeWidth(10.0f);
        this.mForePaint.setAntiAlias(true);
        this.mForePaint.setColor(Color.rgb(255, 255, 255));
    }

    public void updateVisualizer(byte[] bytes) {
        this.mBytes = bytes;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBytes != null) {
            if (this.mPoints == null || this.mPoints.length < this.mBytes.length * 4) {
                this.mPoints = new float[(this.mBytes.length * 4)];
            }
            this.mRect.set(0, 0, getWidth(), getHeight());
            for (int i = 0; i < this.mBytes.length - 1; i++) {
                this.mPoints[i * 4] = (float) ((this.mRect.width() * i) / (this.mBytes.length - 1));
                this.mPoints[(i * 4) + 1] = (float) ((this.mRect.height() / 2) + ((((byte) (this.mBytes[i] + 128)) * (this.mRect.height() / 2)) / 128));
                this.mPoints[(i * 4) + 2] = (float) ((this.mRect.width() * (i + 1)) / (this.mBytes.length - 1));
                this.mPoints[(i * 4) + 3] = (float) ((this.mRect.height() / 2) + ((((byte) (this.mBytes[i + 1] + 128)) * (this.mRect.height() / 2)) / 128));
            }
            canvas.drawLines(this.mPoints, this.mForePaint);
        }
    }
}
